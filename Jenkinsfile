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
		SSH_CREDENTIALS_ID  = 'ec2-ssh-creds'
		SSH_PRIVATE_KEY = credentials('ec2-ssh-creds')
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
                script {
                    // Inline playbook
                    def playbook = """
                    - name: Deploy application to EC2 instance
                      hosts: ec2
                      gather_facts: no
                      tasks:
                        - name: Copy JAR file to EC2 instance
                          ansible.builtin.copy:
                            src: /var/jenkins_home/workspace/Movie_Service_CI/target/*.jar
                            dest: /opt/movie-service/movie-app.jar
                            remote_src: yes
                            owner: ec2-user
                            group: ec2-user
                            mode: '0644'
                        - name: Start application
                          ansible.builtin.shell: 'java -jar /opt/movie-service/movie-app.jar > /dev/null 2>&1 &'
                    """
                    
                    // Write playbook to a temporary file
                    def playbookFile = writeFile file: 'playbook.yaml', text: playbook
                    
                    // Run Ansible playbook with inline playbook
                    sh "ansible-playbook -i ${EC2_INSTANCE_IP}, --private-key=${SSH_PRIVATE_KEY} ${playbookFile}"
                }
            }
        }
		//stage('Deploy To EC2 Instance') {
          //  steps {
				//sh "mvn clean package -DskipTests"
			
                // Use SSH key credentials for authentication
               // withCredentials([sshUserPrivateKey(credentialsId: 'ec2-ssh-creds', keyFileVariable: 'SSH_PRIVATE_KEY')]) {
               //     // Copy the JAR file to the EC2 instance using SCP
                 //   sh "scp -v -o StrictHostKeyChecking=no -i \$SSH_PRIVATE_KEY /var/jenkins_home/workspace/Movie_Service_CI/target/*.jar ec2-user@${EC2_INSTANCE_IP}:/opt/movie-service/"
               //// }
           // }
        //}
    }
}

