pipeline {
    agent any

    environment {
        IMAGE_NAME = "amazon7737/hello-world-image"
        IMAGE_TAG = "build-${env.BUILD_NUMBER}"
        REMOTE_HOST = "146.56.98.224"
        REMOTE_DIR = "/home/ubuntu"
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: 'main', url: 'https://github.com/amazon7737/hello-world.git'
            }
        }
        stage('Build') {
            steps {
                echo 'Building from GitHub!'
                sh './gradlew clean build'
            }
        }
        stage('Build Docker Image') {
            steps {
                sh "docker build --platform linux/amd64 -t ${IMAGE_NAME}:${IMAGE_TAG} ."
            }
        }
        stage('Push Docker Image') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'amazon77372', usernameVariable: 'DOCKER_USER', passwordVariable: 'DOCKER_PASS')]) {
                    sh """
                    echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin
                    docker push ${IMAGE_NAME}:${IMAGE_TAG}
                    """
                }
            }
        }
        stage('Deploy to Remote Server') {
            steps {
                withCredentials([file(credentialsId: 'remote-server-key', variable: 'PRIVATE_KEY')]) {
                    sh """
                        ssh -o StrictHostKeyChecking=no -i ${PRIVATE_KEY} ubuntu@${REMOTE_HOST} '
                        cd ${REMOTE_DIR} &&
                        docker pull ${IMAGE_NAME}:${IMAGE_TAG} &&
                        docker-compose down &&
                        IMAGE_TAG=${IMAGE_TAG} docker-compose up -d
                        '
                        """
                }
            }
        }
    }

    post {
        success {
            echo "✅ Successfully deployed: ${IMAGE_NAME}:${IMAGE_TAG}"
        }
        failure {
            echo "❌ Deployment failed."
        }
    }
}
