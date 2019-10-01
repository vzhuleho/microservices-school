pipeline {
  agent any
  stages {
    stage('Check out') {
      agent any
      steps {
        git(url: 'https://github.com/vzhuleho/microservices-school/tree/master', branch: 'master', changelog: true, poll: true)
      }
    }
    stage('Build') {
      agent any
      steps {
        sh 'cd schedule-service'
        sh 'gradlew clean build'
      }
    }
  }
}