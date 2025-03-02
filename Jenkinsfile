pipeline {
    agent any
    stages {
        stage('Checkout') {
            steps {
                script {
                    def url="https://github.com/raysalfaa/newTest.git"
                    def branchName = env.GIT_BRANCH
                    def sourceBranch = env.CHANGE_BRANCH  // Source branch
                    def baseBranch = env.CHANGE_TARGET   // Base branch

                    if (env.CHANGE_ID) {
                        echo "Pull Request Detected!"
                        echo "Source Branch: ${sourceBranch}"
                        echo "Base Branch: ${baseBranch}"
                    } else {
                        echo"Repostary is  ${url}"
                        echo "Regular Build - Branch: ${branchName}"

                        
                    }
                }
            }
        }
    }
}
