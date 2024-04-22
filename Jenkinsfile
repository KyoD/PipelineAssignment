/* Requires the Docker Pipeline plugin */
pipeline {
    agent any
    tools {
        maven 'Maven 3.8.6'
    }
    stages {
        stage('Build') {
            steps {
                sh "mvn clean compile"
            }
        }
        stage('Package'){
            steps {
                sh "mvn package"
            }
        }
    }
}

