pipeline {
    agent any
    environment {
        REPO_URL = 'https://github.com/raysalfaa/newTest.git'  // Global env variable
        
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
            echo "Build failed!"
        }
        aborted {
            echo "Build was skipped because target branch is not 'dev'."
        }
    }
}
