pipeline {

    agent any



    stages {

        stage('Build') {

            steps {

               build 'GIT HUB JOB'

            }

        }

        stage('Test') {

            steps {

                build 'tests'

            }

        }

        stage('Sonar check') {

                    steps {

                        build 'Sonar Scanner'

                    }

                }

        stage('Final build') {

            steps {

                build 'build'

           }
        }
    }
    post {

                    failure {

                       mail bcc: '', body: 'Project build failed. See logs', cc: '', from: '', replyTo: '', subject: 'Project build failed', to: 'vadzimkuzmenka@mail.ru'

                    }
                }

}
