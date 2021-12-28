pipeline
{
    agent any
    
    environment
    {
        KMASTER_NODE = 'root@192.168.56.105'
        WORKER01_NODE = 'root@192.168.56.103'
        WORKER02_NODE = 'root@192.168.56.104'
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
    
        stage ('checking ssh-connection to vm nodes')
        {
            steps 
            {
                sh 'ssh $KMASTER_NODE ls'
                sh 'ssh $WORKER01_NODE ls'
                // sh 'ssh $WORKER02_NODE ls'
            }
        }
        
        stage ('kubeadm init - Initialising Cluster - Master Node')
        {
            steps
            {
                sh 'ssh $KMASTER_NODE kubeadm init --pod-network-cidr=10.244.0.0/16'
                
                sh 'ssh $KMASTER_NODE mkdir -p $HOME/.kube'
                sh 'ssh $KMASTER_NODE sudo \\cp -i /etc/kubernetes/admin.conf $HOME/.kube/config'
                sh 'ssh $KMASTER_NODE sudo chown $(id -u):$(id -g) $HOME/.kube/config'
            }
        }
        
        stage ('Flannel - Implement Kubernetes Network')
        {
            steps
            {
                sh 'ssh $KMASTER_NODE kubectl apply -f kube-flannel.yml'   
            }
        }
        
        stage ('Wait for all pods to be UP')
        {
            steps
            {
                sh 'ssh $KMASTER_NODE kubectl wait --for=condition=Ready pods --all -A'
            }
        }
        
        stage ('Get Token for worker nodes to join')
        {
            steps
            {
                script
                {
                    sh 'ssh $KMASTER_NODE sudo kubeadm token generate'
                    env.JOIN_COMMAND = sh 'ssh $KMASTER_NODE sudo kubeadm token create <TOKEN-FROM-GENERATE-STEP> --ttl 1h --print-join-command'
                }
            }
        }
        
        stage ('Join Worker Nodes in Cluster using kubeadm join')
        {
            steps
            {
                script
                {
                    sh 'ssh $WORKER01_NODE $JOIN_COMMAND'
                    // sh 'ssh $WORKER02_NODE $JOIN_COMMAND'
                }
            }
        }
        
        stage ('Create Deployment & Service - To Deploy our docker image from ECR')
        {
            steps
            {
                sh 'ssh $KMASTER_NODE kubectl apply -f myapp-deployment-and-service.yaml'
            }
        }
        
        stage ('Show all resources when deployment is complete')
        {
            steps
            {
                sh 'ssh $KMASTER_NODE kubectl wait --for=condition=Ready pods --all -A'
                sh 'ssh $KMASTER_NODE kubectl wait --for=condition=Ready pods --all -A'
                sh 'ssh $KMASTER_NODE kubectl get all -o wide'
            }
        }
    }
}