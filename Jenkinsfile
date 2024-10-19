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
        KUBE_CONFIG = credentials('kubeconfig') // Fetch Kubernetes config from Jenkins credentials
    }

    stages {
        stage('Build Frontend') {
            steps {
                dir('frontend/test-app') {
                    bat 'npm install'
                    buildDockerImage(FRONTEND_IMAGE_NAME)
                }
            }
        }

        stage('Build Backend') {
            steps {
                dir('backend/demo') {
                    bat 'mvn clean install'
                    buildDockerImage(BACKEND_IMAGE_NAME)
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
                        dockerLogin()
                        pushDockerImage(FRONTEND_IMAGE_NAME)
                        pushDockerImage(BACKEND_IMAGE_NAME)
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                script {
                    writeKubeConfig(KUBE_CONFIG)
                    deployK8s()
                }
            }
        }
    }

    post {
        always {
            script {
                dockerLogout()
            }
        }
    }
}

def buildDockerImage(String imageName) {
    def imageExists = bat(script: "docker images -q ${imageName}:${BUILD_NUMBER}", returnStatus: true) == 0
    if (!imageExists) {
        bat "docker build -t ${imageName}:${BUILD_NUMBER} ."
    } else {
        echo "${imageName}:${BUILD_NUMBER} already exists. Skipping build."
    }
}

def dockerLogin() {
    bat "docker login -u %DOCKERHUB_USERNAME% -p %DOCKERHUB_PASSWORD%"
}

def pushDockerImage(String imageName) {
    if (bat(script: "docker images -q ${imageName}:${BUILD_NUMBER}", returnStatus: true) == 0) {
        dir("frontend/test-app") {
            bat "docker push ${imageName}:${BUILD_NUMBER}"
            bat "docker tag ${imageName}:${BUILD_NUMBER} ${imageName}:latest"
            bat "docker push ${imageName}:latest"
        }
    }
}

def writeKubeConfig(String kubeConfig) {
    writeFile file: 'kubeconfig', text: kubeConfig
    bat 'set KUBECONFIG=%WORKSPACE%\\kubeconfig'
}

def deployK8s() {
    bat 'kubectl apply -f k8s/frontend-deployment.yaml'
    bat 'kubectl apply -f k8s/backend-deployment.yaml'
}

def dockerLogout() {
    bat "docker logout"
}
