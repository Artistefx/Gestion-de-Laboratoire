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
        KUBE_CONFIG = credentials('kubeconfig') // Assuming you have the Kubernetes config stored in Jenkins credentials
    }
    stages {
        stage('Build Frontend') {
            steps {
                dir('Frontend/angular-app') {
                    bat 'npm install'
                }
            }
        }
        stage('Build Backend') {
            steps {
                dir('Backend/demo') {
                    bat 'mvn clean install'
                }
            }
        }
        stage('Run Tests') {
            steps {
                dir('Backend/demo') {
                    bat 'mvn test'
                }
                dir('Frontend/angular-app') {
                    bat 'npm test'
                }
            }
        }
        stage('Build Docker Images') {
            parallel {
                stage('Build Docker Image for Frontend') {
                    steps {
                        dir('Frontend/angular-app') {
                            bat "docker build -t ${FRONTEND_IMAGE_NAME}:${BUILD_NUMBER} ."
                        }
                    }
                }
                stage('Build Docker Image for Backend') {
                    steps {
                        dir('Backend/demo') {
                            bat "docker build -t ${BACKEND_IMAGE_NAME}:${BUILD_NUMBER} ."
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
                        dir('Frontend/angular-app') {
                            bat "docker push ${FRONTEND_IMAGE_NAME}:${BUILD_NUMBER}"
                        }
                        dir('Backend/demo') {
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
                    bat 'kubectl apply -f k8s/frontend-deployment.yaml'
                    bat 'kubectl apply -f k8s/backend-deployment.yaml'
                }
            }
        }
    }
    post {
        always {
            bat "docker logout"
        }
    }
}
