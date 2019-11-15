pipeline {
    agent any

    stage('Build') {
        checkout scm
        withEnv([
            'SERVICE=spring-boot-boilerplate',
        ]){
            stage('Build') {
                sh './build.sh'
            }
        }
    }

    stage('DEV') {
        sh 'ls -la'
        withEnv([
            'SERVICE=spring-boot-boilerplate',
            'PROFILES=dev'
        ]){
            sh './deploy.sh'
        }
    }
}
