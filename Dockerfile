# Getting Tomcat Alpine Image
FROM tomcat:alpine

RUN apk add wget \
&& cd webapps \
&& wget --user admin --password Gravity123 http://192.168.56.102:8082/artifactory/TestPipelineRepo/CalculatorTestApp.war

# Copying war file from current directory to tomcat dir
# ADD CalculatorTestApp.war /usr/local/tomcat/webapps/

EXPOSE 8080

CMD ["catalina.sh", "run"]