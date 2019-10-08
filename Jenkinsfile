pipeline {
  agent {
    label 'centos7 || centos7onDemand'
  }
  options {
    timestamps()
  }
  tools {
    jdk 'OpenJDK11.0.2'
  }
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
        catchError {
          dir(path: 'scheduleservice') {
            sh './gradlew check'
          }
        }
        echo currentBuild.result
      }
    }    
  }
  post {
    always {
      archiveArtifacts artifacts: '**/*.jar', fingerprint: true
      junit '**/test-results/**/*.xml'
      publishHTML([allowMissing: false, alwaysLinkToLastBuild: false, keepAll: false, reportDir: 'reports/tests/test', reportFiles: 'index.html', reportName: 'HTML Report', reportTitles: ''])
    }
  }
}
