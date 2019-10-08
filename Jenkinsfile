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
        stage('Check schedule-service') {
          steps {
            dir(path: 'scheduleservice') {
              sh './gradlew clean build -xTest'
            }

            catchError() {
              dir(path: 'scheduleservice') {
                sh './gradlew check'
              }

            }

            dir(path: 'scheduleservice') {
              junit(testResults: '**/test-results/**/*.xml', allowEmptyResults: true)
            }

            createSummary '${currentBuild.result}'
          }
        }
        stage('Check curriculum-service') {
          environment {
            datasource_username = 'test'
            datasource_password = 'test'
            datasource_url = 'jdbc:postgresql://localhost:5432/postgres'
          }
          steps {
            dir(path: 'curriculum') {
              sh './gradlew clean build -xTest'
            }

          }
        }
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