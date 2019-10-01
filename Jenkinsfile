pipeline {
  agent any
  stages {
    stage('Check out') {
      agent any
      steps {
        git(url: 'https://github.com/vzhuleho/microservices-school', changelog: true, poll: true)
      }
    }
    stage('Build') {
      agent any
      steps {
        sh 'cd scheduleservice'
        sh './gradlew clean build'
      }
    }
  }
}