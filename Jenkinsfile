pipeline {
    agent any
    tools {
        maven 'Maven'
        jdk 'Java JDK'
        dockerTool 'Docker'
    }
    stages {
        stage('Test and Verify target') {
            steps {
                sh 'mvn verify'
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
        stage('Clean and Package') {
            steps {
                sh 'mvn clean package'
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
                echo 'Deploying cloudformation..'
                sh "aws cloudformation deploy --stack-name StackLunchRestaurantService --template-file ./ecs.yaml --parameter-overrides ApplicationName=RestaurantService ApplicationEnvironment=dev ECRRepositoryUri=241465518750.dkr.ecr.us-east-2.amazonaws.com/restaurantservice:latest --capabilities CAPABILITY_IAM CAPABILITY_NAMED_IAM --region us-east-2"
        	}
        }
    }
    post {
        always {
            sh 'mvn clean'
        }
    }
}