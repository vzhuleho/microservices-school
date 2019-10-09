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
              catchError() {
                sh './gradlew check'
              }
              junit(testResults: '**/test-results/**/*.xml', allowEmptyResults: true)
              createSummary '${currentBuild.result}'
            }
          }
        }
        stage('Check curriculum-service') {
          steps {
            dir(path: 'curriculum') {
              sh './gradlew clean build -xTest -Pdatasource_url="jdbc:postgresql://localhost:5432/postgres" -Pdocker_username="msschooltraining" -Pdocker_password="Ms.school\$" -Pdocker_email="ms.school.training@gmail.com" -Pdatasource_username="test" -Pdatasource_password="test" -Pdatasource_driver="org.postgresql.Driver"'
            }
            catchError() {
              sh './gradlew check'
            }
            junit(testResults: '**/test-results/**/*.xml', allowEmptyResults: true)
            createSummary '${currentBuild.result}'
          }
        }
        stage('Check user-service') {
          steps {
            dir(path: 'userservice') {
              catchError() {
                sh './mvnw install'
              }
              junit(testResults: '**/test-results/**/*.xml', allowEmptyResults: true)
              createSummary '${currentBuild.result}'
            }
          }
        }
        stage('Check school-class-service') {
          steps {
            dir(path: 'school-class-service') {
              sh './gradlew clean build -xTest'
            }
            catchError() {
              sh './gradlew check'
            }
            junit(testResults: '**/test-results/**/*.xml', allowEmptyResults: true)
            createSummary '${currentBuild.result}'
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
