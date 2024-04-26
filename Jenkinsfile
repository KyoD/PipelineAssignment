/* Requires the Docker Pipeline plugin */
/* Adding comment for triggering sonarqube again again sdsad das adsdd*/
pipeline {
    agent any
    tools {
        maven 'maven'
    }
    environment{
        SCANNER_HOME=tool 'SonarQube-Scanner'
		DOCKER_ACCESS_TOKEN = credentials('your-docker-credentials-id')
        DOCKER_IMAGE_NAME = 'movie_service'
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
		stage('Build and Push Docker Image') {
            steps {
				script {
                    // Your Docker commands here, such as docker login and docker push
                    sh "docker login -u dazzk -p ${DOCKER_ACCESS_TOKEN}"
                    sh "docker build -t ${DOCKER_IMAGE_NAME} ."
                    sh "docker push ${DOCKER_IMAGE_NAME}"
                }
            }
        }
    }
    //post {
        //always {
             //Stop the application after tests
            //sh 'pkill -f "java -jar"'
       // }
    //}
}

