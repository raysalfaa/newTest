pipeline {
    agent any
    environment {
        REPO_URL = 'https://github.com/raysalfaa/newTest.git'  // Global env variable
        def branchName = env.GIT_BRANCH
        def sourceBranch = env.CHANGE_BRANCH  // Source branch
        def baseBranch = env.CHANGE_TARGET   // Base branch

    }
    stages {
        stage('Pull Request') {
            steps {
                script {
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
    }
}
