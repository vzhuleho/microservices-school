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
      publishHTML([reportDir: 'build/reports/tests/test', reportFiles: 'index.html', reportName: 'HTML Report'])
    }
  }
}
