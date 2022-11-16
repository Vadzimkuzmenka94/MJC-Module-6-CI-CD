node {
  stage('SCM') {
    git 'https://github.com/Vadzimkuzmenka94/MJC-Module-6-CI-CD.git'  }
  stage('SonarQube analysis') {
    withSonarQubeEnv() { // Will pick the global server connection you have configured
      bat './gradlew sonarqube'
    }
  }
}
