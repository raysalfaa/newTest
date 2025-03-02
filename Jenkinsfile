pipeline {
    agent any
    environment {
        REPO_URL = 'https://github.com/raysalfaa/newTest.git'  // Global env variable
        SONARQUBE_URL = 'http://localhost:9000'
        sonarHome=tool 'sonarQubeScanner'
        RECIPIENTS = 'redeyesinbg@gmail.com'
        
    }
    stages {
        stage('Pull Request') {
            steps {
                script {
                    def branchName = env.GIT_BRANCH
                    def sourceBranch = env.CHANGE_BRANCH  // Source branch
                    def baseBranch = env.CHANGE_TARGET   // Base branch

                    if (env.CHANGE_ID) {
                        echo "Pull Request Detected!"
                        echo "Source Branch: ${sourceBranch}"
                        echo "Base Branch: ${baseBranch}"
                        echo "Reposatory name: ${REPO_URL}"
                    } else {
                        echo "Reposatory name: ${REPO_URL}"
                        echo "Regular Build - Branch: ${branchName}"
                    }
                }
            }
        }
        stage('Validate PR') {
            steps {
                script {
                    if (env.CHANGE_ID) {  // If it's a PR
                        if (env.CHANGE_TARGET == "prod" || env.CHANGE_TARGET == "stag") {
                            echo "Skipping build. PR is targeting '${env.CHANGE_TARGET}'"
                            currentBuild.result = 'ABORTED'
                            error("Build skipped: PR is not targeting 'dev'.")
                        } else if (env.CHANGE_TARGET == "dev") {
                            echo "PR targeting 'dev' - Proceeding with the build."
                        } else {
                            echo "Skipping build: Unknown target branch '${env.CHANGE_TARGET}'."
                            currentBuild.result = 'ABORTED'
                            error("Build skipped: Target branch is not recognized.")
                        }
                    } else {
                        echo "Regular push to ${env.CHANGE_BRANCH} - Proceeding."
                    }
                }
            }
        }
        stage('SonarQube Analysis') {
            steps {
                
                withSonarQubeEnv('sonarQube') {
                    sh '''
                    ${sonarHome}/bin/sonar-scanner \
                        -Dsonar.projectKey=jenkinscanner \
                        -Dsonar.sources=. \
                        -Dsonar.language=py \
                        -Dsonar.python.version=3 \
                        -Dsonar.host.url=$SONARQUBE_URL
                    '''
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "Building code from ${env.CHANGE_BRANCH} -> ${env.CHANGE_TARGET}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Deploying application from ${env.CHANGE_BRANCH} -> ${env.CHANGE_TARGET}"
                }
            }
        }
    }

    post {
        failure {
            script {
                emailext (
                    subject: "Jenkins Build Failed: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
                    Hi Team,

                    The Jenkins build '${env.JOB_NAME} #${env.BUILD_NUMBER}' has failed.

                    **Possible Reasons:**
                    - Code Quality Issues
                    - Test Failures
                    - Build Errors

                    **Logs:** ${env.BUILD_URL}/console

                    Regards,  
                    Jenkins CI
                    """,
                    to: RECIPIENTS
                )
                echo "Pipelining done"
            }
        }
        aborted{
            cript {
                emailext (
                    subject: "Jenkins Build aborted: ${env.JOB_NAME} #${env.BUILD_NUMBER}",
                    body: """
                    Hi Team,

                    The Jenkins build '${env.JOB_NAME} #${env.BUILD_NUMBER}' has been aborted.

                    **Possible Reasons:**
                    - Code Quality Issues
                    - Test Failures
                    - Build Errors
                    -Pr is not targeting 'dev' branch .

                    **Logs:** ${env.BUILD_URL}/console

                    Regards,  
                    Jenkins CI
                    """,
                    to: RECIPIENTS
                )
                echo "Pipelining done"
            }
        }
    }
}
