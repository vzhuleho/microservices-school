pipeline {
  agent any
  stages {
    stage('Check out') {
      agent any
      steps {
        git(url: 'https://github.com/vzhuleho/microservices-school/scheduleservice', changelog: true, poll: true)
      }
    }
    stage('Build') {
      agent any
      steps {
        sh 'ls -a'
      }
    }
  }
}