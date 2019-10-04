pipeline {
  agent any
  stages {
    stage('Check out') {     
      steps {
        git(url: 'https://github.com/vzhuleho/microservices-school', changelog: true, poll: true)
      }
    }
    stage('Build') {
      steps {
        dir(path: 'scheduleservice') {
          sh './gradlew clean build -xTest'
        }
      }
    }
    stage('Test') {
      steps {
        dir(path: 'scheduleservice') {
          sh './gradlew check'
        }
      }
    }    
  }
  post {
    always {
      archiveArtifacts artifacts: 'build/libs/**/*.jar', fingerprint: true
      junit 'build/reports/**/*.xml'
    }
  }
}
