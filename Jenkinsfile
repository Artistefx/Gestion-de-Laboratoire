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
        KUBE_CONFIG = credentials('kubeconfig') // Fetching the Kubernetes config stored in Jenkins credentials
    }
    stages {
        stage('Build Frontend') {
            steps {
                script {
                        dir('frontend/test-app') {
                            bat 'npm install'
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
        stage('Build Docker Images') {
            parallel {
                stage('Build Docker Image for Frontend') {
                    steps {
                        script {
                                dir('frontend/test-app') {
                                    bat "docker build -t ${FRONTEND_IMAGE_NAME}:${BUILD_NUMBER} ."
                                }
                        }
                    }
                }
                stage('Build Docker Image for Backend') {
                    steps {
                        script {
                                dir('backend/demo') {
                                    bat "docker build -t ${BACKEND_IMAGE_NAME}:${BUILD_NUMBER} ."
                                }
                        }
                    }
                }
            }
        }
        stage('Push Docker Images to DockerHub') {
            steps {
                script {
                    withCredentials([usernamePassword(credentialsId: DOCKERHUB_CREDENTIALS_ID, usernameVariable: 'DOCKERHUB_USERNAME', passwordVariable: 'DOCKERHUB_PASSWORD')]) {
                            bat "docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%"
                            // Push images
                            dir('frontend/test-app') {
                                bat "docker push ${FRONTEND_IMAGE_NAME}:${BUILD_NUMBER}"
                            }
                            dir('backend/demo') {
                                bat "docker push ${BACKEND_IMAGE_NAME}:${BUILD_NUMBER}"
                            }
                    }
                }
            }
        }
        stage('Deploy to Kubernetes') {
            steps {
                script {
                        // Load Kubernetes config
                        writeFile file: 'kubeconfig', text: KUBE_CONFIG
                        // Set the KUBECONFIG environment variable for kubectl to use
                        bat 'set KUBECONFIG=%WORKSPACE%\\kubeconfig'
                        // Apply Kubernetes deployment configurations
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
