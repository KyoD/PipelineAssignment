/* Requires the Docker Pipeline plugin */
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
        stage('SonarQube Analysis') {
            steps{
                withSonarQubeEnv('sonar') {
                  sh "mvn clean verify sonar:sonar -Dsonar.projectKey=Test-Project -Dsonar.projectName='Test Project'"
                }
            }
        }
    }
}

