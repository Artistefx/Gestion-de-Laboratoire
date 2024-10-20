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
    }
    stages {
        stage('Build Frontend') {
            steps {
                script {
                    dir('frontend/test-app') {
                        bat 'npm install'
                        // Build the frontend
                        bat 'npm run build'
                    }
                }
            }
        }
        stage('Build Backend') {
            steps {
                script {
                    dir('backend/demo') {
                        bat 'mvn clean install'
                    }
                }
            }
        }
        stage('Run Frontend Tests') {
            steps {
                script {
                    dir('frontend/test-app') {
                        bat 'npm test'
                    }
                }
            }
        }
        stage('Run Backend Tests') {
            steps {
                script {
                    dir('backend/demo') {
                        bat 'mvn test'
                    }
                }
            }
        }
        stage('Build and Push Docker Images') {
            steps {
                script {
                    // Build and push frontend image
                    dir('frontend/test-app') {
                        // Remove existing image if it exists
                        bat "docker rmi -f ${FRONTEND_IMAGE_NAME}:latest || echo 'No existing frontend image to remove'"
                        // Build and tag the latest image
                        bat "docker build -t ${FRONTEND_IMAGE_NAME}:latest ."
                        // Push the latest image to DockerHub
                        withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                            bat "docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%"
                            bat "docker push ${FRONTEND_IMAGE_NAME}:latest"
                        }
                    }
                    
                    // Build and push backend image
                    dir('backend/demo') {
                        // Remove existing image if it exists
                        bat "docker rmi -f ${BACKEND_IMAGE_NAME}:latest || echo 'No existing backend image to remove'"
                        // Build and tag the latest image
                        bat "docker build -t ${BACKEND_IMAGE_NAME}:latest ."
                        // Push the latest image to DockerHub
                        withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                            bat "docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%"
                            bat "docker push ${BACKEND_IMAGE_NAME}:latest"
                        }
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                script {
                    // Copy the kubeconfig file from Jenkins secret file to workspace
                    def kubeconfigFile = 'kubeconfig'
                    writeFile file: kubeconfigFile, text: ''
                    // Load the kubeconfig from the Jenkins secret file
                    withCredentials([file(credentialsId: 'Kubeconf', variable: 'KUBE_CONFIG_FILE')]) {
                        bat "copy /Y %KUBE_CONFIG_FILE% %WORKSPACE%\\${kubeconfigFile}"
                    }
                    // Set KUBECONFIG environment variable for kubectl
                    bat "set KUBECONFIG=%WORKSPACE%\\${kubeconfigFile}"
                    // Verify the configuration
                    bat 'kubectl config view'
                    // Deploy the applications
                    bat 'kubectl apply -f k8s/frontend-deployment.yaml --validate=false'
                    bat 'kubectl apply -f k8s/backend-deployment.yaml --validate=false'
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
