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
        stage('SonarQube Analysis'){
            steps {
                withSonarQubeEnv('sonar') {
                    sh ''' $SCANNER_HOME/bin/SonarQube-Scanner z
                    -Dsonar.projectName=Test Project \
                    -Dsonar.projectKey=Test Project \
                    -Dsonar.analysis.report.format=json'''
                }
            }
        }
    }
}

