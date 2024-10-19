pipeline {
    agent any
    tools {
        nodejs "node 22"
        maven "maven-3.9.7"
    }
    environment {
        DOCKERHUB_CREDENTIALS_ID = '56'
        FRONTEND_IMAGE_NAME = 'artistehmz/labo-frontend'
        BACKEND_IMAGE_NAME = 'artistehmz/labo-backend'
        KUBE_CONFIG = credentials('kubeconfig') // Configuration Kubernetes
    }
    stages {
        stage('Build Frontend') {
            steps {
                script {
                    dir('frontend/test-app') {
                        bat 'npm install'
                        bat "docker build -t ${FRONTEND_IMAGE_NAME}:${BUILD_NUMBER} ."
                    }
                }
            }
        }
        stage('Build Backend') {
            steps {
                script {
                    dir('backend/demo') {
                        bat 'mvn clean install'
                        bat "docker build -t ${BACKEND_IMAGE_NAME}:${BUILD_NUMBER} ."
                    }
                }
            }
        }
        stage('Run Tests') {
            steps {
                script {
                    dir('backend/demo') {
                        bat 'mvn test'
                    }
                    dir('frontend/test-app') {
                        bat 'npm test'
                    }
                }
            }
        }
        stage('Push Docker Images to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                        bat "docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%"
                        
                        // Pousser les images avec le BUILD_NUMBER
                        bat "docker push ${FRONTEND_IMAGE_NAME}:${BUILD_NUMBER}"
                        bat "docker push ${BACKEND_IMAGE_NAME}:${BUILD_NUMBER}"
                        
                        // Tag et pousser l'image latest
                        bat "docker tag ${FRONTEND_IMAGE_NAME}:${BUILD_NUMBER} ${FRONTEND_IMAGE_NAME}:latest"
                        bat "docker push ${FRONTEND_IMAGE_NAME}:latest"
                        bat "docker tag ${BACKEND_IMAGE_NAME}:${BUILD_NUMBER} ${BACKEND_IMAGE_NAME}:latest"
                        bat "docker push ${BACKEND_IMAGE_NAME}:latest"
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    writeFile file: 'kubeconfig', text: KUBE_CONFIG
                    bat 'set KUBECONFIG=%WORKSPACE%\\kubeconfig'
                    bat 'kubectl apply -f k8s/frontend-deployment.yaml'
                    bat 'kubectl apply -f k8s/backend-deployment.yaml'
                }
            }
        }
    }
    post {
        always {
            script {
                bat "docker logout"
            }
        }
    }
}
