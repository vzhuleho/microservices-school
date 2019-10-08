pipeline {
  agent {
    label 'centos7 || centos7onDemand'
  }
  stages {
    stage('Check out') {
      steps {
        git(url: 'https://github.com/vzhuleho/microservices-school', changelog: true, poll: true)
      }
    }
    stage('Build') {
      parallel {
        stage('Build') {
          steps {
            dir(path: 'scheduleservice') {
              sh './gradlew clean build -xTest'
            }

          }
        }
        stage('') {
          steps {
            dir(path: 'curriculum') {
              sh './gradlew clean build -xTest'
            }

          }
        }
      }
    }
    stage('Test') {
      steps {
        catchError() {
          dir(path: 'scheduleservice') {
            sh './gradlew check'
          }

        }

        catchError() {
          dir(path: 'scheduleservice') {
            junit(testResults: '**/test-results/**/*.xml', allowEmptyResults: true)
          }

        }

        echo currentBuild.result
      }
    }
  }
  tools {
    jdk 'OpenJDK11.0.2'
  }
  options {
    timestamps()
  }
}