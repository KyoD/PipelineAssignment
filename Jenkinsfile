/* Requires the Docker Pipeline plugin */
pipeline {
    agent any
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

