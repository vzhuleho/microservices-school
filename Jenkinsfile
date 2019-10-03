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
        dir(path: 'scheduleservice') {
          sh './gradlew clean build -xTest'
        }

      }
    }
    stage('Test') {
      agent any
      steps {
        sh './gradlew check'
      }
    }
    stage('Post') {
      steps {
        junit 'build/reports/**/*.xml'
      }
    }
  }
}