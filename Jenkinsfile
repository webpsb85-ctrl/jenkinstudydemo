pipeline {
    agent any

    tools {
        maven 'Maven_3.9'   // must match your Jenkins Tools config
        jdk 'Java_21'       // must match your Jenkins Tools config
    }

    stages {
        stage('Checkout') {
            steps {
                git branch: 'master',   // change to 'master' if your repo uses master
                    url: 'https://github.com/webpsb85-ctrl/jenkinstudydemo.git',
                    credentialsId: 'github-credss'
            }
        }

        stage('Build') {
            steps {
                bat 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Deploy') {
            steps {
                bat 'copy target\\jenkinstudydemo.war C:\\apache-tomcat-10.1.0\\webapps\\jenkinstudydemo.war'
            }
        }
    }
}
