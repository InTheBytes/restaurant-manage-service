pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'Java JDK'
        dockerTool 'Docker'
    }
    stages {
        stage('Clean and Test target') {
            steps {
                sh 'mvn clean test'
            }
        }
        stage('Test and Package') {
            steps {
                sh 'mvn package'
            }
        }
        stage('Code Analysis: Sonarqube') {
            steps {
                withSonarQubeEnv('SonarQube') {
                    sh 'mvn sonar:sonar'
                }
            }
        }
        stage('Await Quality Gateway') {
            steps {
                waitForQualityGate abortPipeline: true
            }
        }
        stage('Dockerize') {
            steps {
                script {
                    docker.build('restaurantservice')
                }
            }
        }
        stage('Push ECR') {
            steps {
                script {
                    docker.withRegistry('https://241465518750.dkr.ecr.us-east-2.amazonaws.com', 'ecr:us-east-2:aws-ecr-creds') {
                        docker.image('restaurantservice').push("${env.BUILD_NUMBER}")
                        docker.image('restaurantservice').push('latest')
                    }
                }
            }
        }
        stage('Deploy') {
            steps {
                echo 'Updating k8s image..'
                sh '~/kubectl set image deployment/restaurant-manage-service restaurant-manage-service=241465518750.dkr.ecr.us-east-2.amazonaws.com/restaurantservice:latest'
            }
        }
    }
    post {
        always {
            sh 'mvn clean'
        }
    }
}