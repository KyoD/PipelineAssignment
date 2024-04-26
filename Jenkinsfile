/* Requires the Docker Pipeline plugin */
/* Adding comment for triggering sonarqube again again sdsad das adsdd*/
pipeline {
    agent any
    tools {
        maven 'maven'
    }
    environment{
        SCANNER_HOME=tool 'SonarQube-Scanner'
        DOCKER_IMAGE_NAME = 'movie-service'
        EC2_INSTANCE_IP = '52.90.148.156'
		SSH_CREDENTIALS_ID  = 'ec2-ssh-creds'
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn clean package"
            }
        }
        stage('Start Movie Service for Karate') {
            steps {
                sh 'mvn spring-boot:run &'
                sleep(time: 10, unit: 'SECONDS') // Wait X seconds for the application to start
            }
        }
        stage('Run Unit and Karate Tests') {
            steps {
                sh "mvn test"
                junit '**/target/surefire-reports/*.xml'
            }
        }
        stage('SonarQube Analysis') {
            steps{
                withSonarQubeEnv('sonar') {
                  sh "mvn sonar:sonar -Dsonar.projectKey=Test-Project -Dsonar.projectName='Test Project'"
                }
            }
        }
		stage('Deploy To EC2 Instance') {
            steps {
                // Use SSH key credentials for authentication
                withCredentials([sshUserPrivateKey(credentialsId: 'ec2-ssh-creds', keyFileVariable: 'SSH_PRIVATE_KEY')]) {
                    // Copy the JAR file to the EC2 instance using SCP
                    sh "scp -o StrictHostKeyChecking=no -i ${SSH_PRIVATE_KEY} /var/lib/jenkins/workspace/Movie_Service_CI/target/*.jar ec2-user@${EC2_INSTANCE_IP}:/opt/movie-service/"
                }
            }
        }
    }
}

