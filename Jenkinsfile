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
                bat '''
                    REM Stop Tomcat service
                    echo Stopping Tomcat...
                    net stop Tomcat11 || echo Tomcat was not running
                    
                    REM Wait for service to stop
                    timeout /t 5 /nobreak
                    
                    REM Deploy WAR file
                    echo Deploying application...
                    copy "target\\studydevops-0.0.1-SNAPSHOT.war" "C:\\Program Files\\Apache Software Foundation\\Tomcat 11.0\\webapps\\studydevops.war"
                    
                    REM Wait a moment for file to be copied
                    timeout /t 3 /nobreak
                    
                    REM Start Tomcat service
                    echo Starting Tomcat...
                    net start Tomcat11
                    
                    REM Wait for Tomcat to start
                    timeout /t 10 /nobreak
                    
                    REM Check if Tomcat is running
                    sc query Tomcat11 | find "RUNNING" >nul
                    if errorlevel 1 (
                        echo ERROR: Tomcat failed to start!
                        exit /b 1
                    ) else (
                        echo SUCCESS: Tomcat is running!
                    )
                '''
            }
            post {
                always {
                    bat 'echo Deployment completed'
                }
                failure {
                    bat 'echo ERROR: Deployment failed - check Tomcat logs at C:\\Program Files\\Apache Software Foundation\\Tomcat 11.0\\logs\\'
                }
            }
        }
    }
}
