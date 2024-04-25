/* Requires the Docker Pipeline plugin */
/* Adding comment for triggering sonarqube again again sdsad das adsdd*/
pipeline {
    agent any
    tools {
        maven 'maven'
    }
    environment{
        SCANNER_HOME=tool 'SonarQube-Scanner'
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
                sleep(time: 5, unit: 'SECONDS') // Wait X seconds for the application to start
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
                  sh "mvn clean verify sonar:sonar -Dsonar.projectKey=Test-Project -Dsonar.projectName='Test Project'"
                }
            }
        }
    }
    //post {
        //always {
            // Stop the application after tests
            //sh 'pkill -f "java -jar"'
       // }
    //}
}

