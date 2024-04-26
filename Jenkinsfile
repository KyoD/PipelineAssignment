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
        EC2_INSTANCE_IP = 'ec2-52-90-148-156.compute-1.amazonaws.com'
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn clean compile"
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
                // Copy the JAR file to the EC2 instance using SSH
                sshPublisher(
                    publishers: [
                        sshPublisherDesc(
                            configName: '4670da32-576a-4b05-b283-526495333891',
                            transfers: [
                                sshTransfer(execCommand: "mkdir -p /opt/movie-service", sourceFiles: "target/*.jar"),
                                sshTransfer(execCommand: "cp target/*.jar*.jar /opt/movie-service")
                            ]
                        )
                    ]
                )
            }
        }
    }
}

