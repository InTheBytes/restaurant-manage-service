pipeline {
    agent { dockerfile true }
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
                node {
                    docker.build('accountservice')
                }
            }
        }
        stage('Push ECR') {
            steps {
                node {
                    docker.withRegistry('https://241465518750.dkr.ecr.us-east-2.amazonaws.com', 'ecr:us-east-1:demo-ecr-credentials') {
                        docker.image('accountservice').push('latest')
                    }
                }
            }
        }
    }
    post {
        always {
            sh 'mvn clean'
        }
    }
}
