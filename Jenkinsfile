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
                    setlocal enabledelayedexpansion
                    
                    REM Set Tomcat paths
                    set TOMCAT_HOME=C:\\Program Files\\Apache Software Foundation\\Tomcat 11.0
                    set CATALINA_HOME=%TOMCAT_HOME%
                    set CATALINA_BASE=%TOMCAT_HOME%
                    
                    echo ========================================
                    echo Stopping Tomcat...
                    echo ========================================
                    
                    REM Stop Tomcat using shutdown.bat (instead of killing all java processes)
                    call "%TOMCAT_HOME%\\bin\\shutdown.bat"
                    ping -n 5 127.0.0.1 >nul
                    
                    echo ========================================
                    echo Deploying WAR file...
                    echo ========================================
                    
                    if exist "%TOMCAT_HOME%\\webapps\\studydevops" (
                        echo Removing old application folder...
                        rmdir /s /q "%TOMCAT_HOME%\\webapps\\studydevops" 2>nul
                    )
                    
                    if exist "%TOMCAT_HOME%\\webapps\\studydevops.war" (
                        echo Removing old WAR file...
                        del /F /Q "%TOMCAT_HOME%\\webapps\\studydevops.war"
                    )
                    
                    echo Copying new WAR file...
                    copy "target\\studydevops-0.0.1-SNAPSHOT.war" "%TOMCAT_HOME%\\webapps\\studydevops.war"
                    if errorlevel 1 (
                        echo ERROR: Failed to copy WAR file!
                        exit /b 1
                    )
                    
                    echo ========================================
                    echo Starting Tomcat using startup.bat...
                    echo ========================================
                    
                    call "%TOMCAT_HOME%\\bin\\startup.bat"
                    ping -n 10 127.0.0.1 >nul
                    
                    echo ========================================
                    echo Verifying Tomcat is running...
                    echo ========================================
                    
                    netstat -ano | find ":8088" >nul
                    if errorlevel 1 (
                        echo WARNING: Tomcat may still be starting, check logs manually
                    ) else (
                        echo SUCCESS: Tomcat is listening on port 8088!
                    )
                    
                    echo ========================================
                    echo Deployment completed!
                    echo Application URL: http://localhost:8088/studydevops/
                    echo ========================================
                '''
            }
            post {
                always {
                    bat 'echo Deployment stage completed'
                }
                failure {
                    bat 'echo ERROR: Check Tomcat logs at "%TOMCAT_HOME%\\logs\\"'
                }
            }
        }
    }
}
