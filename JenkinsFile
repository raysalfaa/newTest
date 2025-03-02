pipeline {
    agent any
    environment {
        REPO_URL = 'https://github.com/raysalfaa/assesment.git'  // Replace with your repository URL
    }
    stages {
        stage('Checkout') {
            steps {
                script {
                    // Clone the repository
                    echo "Cloning repository from ${REPO_URL}"
                    git url: "${REPO_URL}", branch: "${env.CHANGE_TARGET}" // Change target from the PR event
                }
            }
        }

        stage('Check Branch and Log Information') {
            steps {
                script {
                    // Get the base (target) branch and source (PR) branch
                    def baseBranch = env.CHANGE_TARGET
                    def sourceBranch = env.CHANGE_BRANCH

                    echo "Base branch: ${baseBranch}"
                    echo "Source branch: ${sourceBranch}"
                    echo "Repository URL: ${REPO_URL}"

                    // Check if target branch is 'dev', and proceed or skip based on condition
                    if (baseBranch == 'dev') {
                        echo "Pull request is targeting 'dev' branch. Proceeding with the build..."
                    } else if (baseBranch == 'prod' || baseBranch == 'staging') {
                        echo "Pull request is targeting 'prod' or 'staging' branch. Skipping the build..."
                        currentBuild.result = 'SUCCESS'  // Skip the build
                        return  // Exit the pipeline
                    }
                }
            }
        }

        stage('Build') {
            steps {
                echo "Running build process..."
                // Add your build commands here, for example, to build a project or run tests
            }
        }
    }

    // Optional: Post actions (if needed)
    post {
        success {
            echo 'Build successful!'
        }
        failure {
            echo 'Build failed!'
        }
    }
}
