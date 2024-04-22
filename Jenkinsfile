/* Requires the Docker Pipeline plugin */
pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                bat "mvn clean compile"
            }
        }
        stage('Package'){
            steps {
                bar "mvn package"
            }
        }
    }
}

