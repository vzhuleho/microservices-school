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
    stage('Build and check') {
      parallel {
        stage('Check schedule-service') {
          steps {
            dir(path: 'scheduleservice') {
              sh './gradlew clean build -xTest -xasciidoctor'
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
              sh './gradlew clean build -xTest -xasciidoctor -Pdatasource_url="jdbc:postgresql://localhost:5432/postgres" -Pdocker_username="msschooltraining" -Pdocker_password="Ms.school$" -Pdocker_email="ms.school.training@gmail.com" -Pdatasource_username="test" -Pdatasource_password="test" -Pdatasource_driver="org.postgresql.Driver"'
              catchError() {
                sh './gradlew check'
              }

              junit(testResults: '**/test-results/**/*.xml', allowEmptyResults: true)
              createSummary '${currentBuild.result}'
            }

          }
        }
        stage('Check user-service') {
          steps {
            dir(path: 'userservice') {
              sh './mvnw clean compile'
              catchError() {
                sh './mvnw test'
              }

              junit(testResults: '**/surefire-reports/*.xml', allowEmptyResults: true)
              createSummary '${currentBuild.result}'
            }

          }
        }
        stage('Check school-class-service') {
          steps {
            dir(path: 'school-class-service') {
              sh './mvnw clean compile'
              catchError() {
                sh './mvnw test'
              }

              junit(testResults: '**/surefire-reports/*.xml', allowEmptyResults: true)
              createSummary '${currentBuild.result}'
            }

          }
        }
      }
    }
    stage('Analyze code') {
      steps {
        dir(path: 'scheduleservice') {
          sh './gradlew sonarqube -Dsonar.projectKey=vzhuleho_microservices-school -Dsonar.organization=vzhuleho -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=c7f49e7e1366972e96ef0a2c39cc76f35db3d497'
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