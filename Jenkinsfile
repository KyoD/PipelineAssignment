/* Requires the Docker Pipeline plugin */
pipeline {
    agent any
    tools {
        maven 'maven'
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

