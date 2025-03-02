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
                        if (env.TARGET_BRANCH == "prod" || env.TARGET_BRANCH == "stag") {
                            echo "Skipping build. PR is targeting '${env.TARGET_BRANCH}'"
                            currentBuild.result = 'ABORTED'
                            error("Build skipped: PR is not targeting 'dev'.")
                        } else if (env.TARGET_BRANCH == "dev") {
                            echo "PR targeting 'dev' - Proceeding with the build."
                        } else {
                            echo "Skipping build: Unknown target branch '${env.TARGET_BRANCH}'."
                            currentBuild.result = 'ABORTED'
                            error("Build skipped: Target branch is not recognized.")
                        }
                    } else {
                        echo "Regular push to ${env.CURRENT_BRANCH} - Proceeding."
                    }
                }
            }
        }

        stage('Build') {
            steps {
                script {
                    echo "Building code from ${env.SOURCE_BRANCH} -> ${env.TARGET_BRANCH}"
                }
            }
        }

        stage('Deploy') {
            steps {
                script {
                    echo "Deploying application from ${env.SOURCE_BRANCH} -> ${env.TARGET_BRANCH}"
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
