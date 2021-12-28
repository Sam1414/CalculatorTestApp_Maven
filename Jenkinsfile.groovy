pipeline
{
    agent any
    
    tools
    {
        maven "maven-3.8.1"
        jdk "Java-11"
    }
    
    environment
    {
        ECR_URL = '876724398547.dkr.ecr.us-east-2.amazonaws.com'
        ECR_REPO_URL = '876724398547.dkr.ecr.us-east-2.amazonaws.com/sam-docker-testapp:latest'
        // SSH_KEY_CONNECTION = 'ssh -i sam-ec2-instance-1.pem ec2-user@ec2-52-15-55-15.us-east-2.compute.amazonaws.com'
    }
    stages
    {
        stage("Code Checkout")
        {
            steps
            {
                git credentialsId: '89ef5e1c-40db-416b-98c3-2563172672c6', url: 'https://git.nagarro.com/freshertraining2021/samarthagarwal.git'
            }
        }
        
        stage("Code Build")
        {
            steps
            {
                sh "mvn clean install"
            }
        }
        
        stage('Unit Test Cases Execution')
        {
            steps
            {
                sh "mvn test"
            }
        }
        
        stage("Sonar Analysis")
        {
            steps
            {
                withSonarQubeEnv('SonarQube')
                {
                    sh "mvn sonar:sonar -Dsonar.login=b1bcd8c0ff2fadf99154bddebcc7cf381f5ddbab"
                }
            }
        }
        
        stage('Publish Artifact to Artifactory'){
            steps{
                rtServer(
                    id:"jfrog-instance-1",
                    url:'http://192.168.56.102:8081/artifactory',
                    username:'admin',
                    password:'Gravity@123',
                    bypassProxy:true,
                    timeout:300
                )
                rtUpload(
                    serverId:"jfrog-instance-1",
                    spec: '''{
                        "files":[
                            {
                                "pattern":"*.war",
                                "target":"TestPipelineRepo"
                            }
                        ]
                    } ''' ,
                )
            }
        }
        
        stage('Create Docker Image')
        {
            steps
            {
                sh 'docker build -t test_image .'
                sh 'docker tag sam-docker-testapp:latest $ECR_REPO_URL'
            }
        }
        
        stage('Push to ECR')
        {
            steps
            {
                script
                {
                    // ECR Login
                    sh '/usr/local/bin/aws ecr get-login-password --region us-east-2 | docker login -u AWS --password-stdin $ECR_REPO_URL'
                    
                    // Push image to ECR
                    sh 'docker push $ECR_REPO_URL'
                }
            }
        }
        
        stage('Pre-container Check in Local System')
        {
            steps
            {
                sh 'docker ps -f name=sam_app -f publish=12000 -q | xargs -r docker kill | xargs -r docker container rm'
                sh 'docker container ls -a -f name=sam_app -q | xargs -r docker rm'
            }
        }
        
        stage('Docker Deploy to Local System')
        {
            steps
            {
                sh 'docker run -d --name sam_app -p 192.168.56.102:12000:8080 876724398547.dkr.ecr.us-east-2.amazonaws.com/sam-docker-testapp'
            }
        }
        
        stage('Tomcat Deploy Local System')
        {
            steps
            {
                sh 'cp target/CalculatorTestApp.war /opt/tomcat/webapps/'
            }
        }
        
        stage('Terraform Destroy & Init')
        {
            steps
            {
                sh 'terraform destroy --auto-approve'
                sh 'terraform init'
                // sh 'terraform plan'
            }
        }
        
        stage('Create new ec2 instance - Terrafrom Apply')
        {
            steps
            {
                sh 'terraform apply --auto-approve'
            }
        }
        
        stage('Get Connection details of EC2')
        {
            steps
            {
                script
                {
                    sh 'chmod 400 sam_tf_key.pem'
                    
                    // getting public dns name
                    env.PUBLIC_DNS_NAME = sh(script: '/usr/local/bin/aws ec2 describe-instances --filters "Name=tag:Name, Values=sam-tf-instance"  "Name=instance-state-name, Values=running, pending" --query "Reservations[].Instances[].PublicDnsName" --output text', returnStdout: true).trim()
                    
                    // Appending dns name to the ssh command to store in an env variable
                    env.SSH_CONNECTION = "ssh -o StrictHostKeyChecking=no -i sam_tf_key.pem ec2-user@" + sh(script: 'echo $PUBLIC_DNS_NAME', returnStdout: true).trim()
                    
                    // getting public ip of ec2 host
                    env.PUBLIC_IP = sh(script: '/usr/local/bin/aws ec2 describe-instances --filters "Name=tag:Name, Values=sam-tf-instance"  "Name=instance-state-name, Values=running" --query "Reservations[].Instances[].PublicIpAddress" --output text', returnStdout: true).trim()
                }
            }
        }
        
        stage('ansible build inventory file')
        {
            steps
            {
                // adding public ip of ec2 in hosts file of ansible
                sh ': > hosts'
                sh 'echo "[ec2]" >> hosts'
                sh 'echo "ec2-user@$PUBLIC_IP" >> hosts'
            }
        }
        
        stage('Ansible playbook - docker setup')
        {
            steps
            {
                // ansiblePlaybook become: true, credentialsId: 'sam-tf-creds', installation: 'ansible', inventory: 'hosts', playbook: 'ansible_docker_setup.yml'
                sh 'ansible-playbook docker_setup.yml -e @secret-vars.yml --vault-password-file vault-pass -i hosts --private-key sam_tf_key.pem -u ec2-user -b --become-user root'
            }
        }
        
        stage('Docker Deploy on ec2')
        {
            steps
            {
                script
                {
                    sh '$SSH_CONNECTION sudo docker run -d --name sam_app -p 12000:8080 876724398547.dkr.ecr.us-east-2.amazonaws.com/sam-docker-testapp'
                }
            }
        }
    }
}