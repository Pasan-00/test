pipeline {
    agent any

    environment {
        // Set environment variables if needed
        BUILD_DIR = 'build'
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out the source code...'
                git url: 'https://github.com/your-repo.git', branch: 'main'
            }
        }

        stage('Install Dependencies') {
            steps {
                echo 'Installing dependencies...'
                sh 'npm install' // Change to your package manager or tool (e.g., Maven, Gradle, pip)
            }
        }

        stage('Build') {
            steps {
                echo 'Building the project...'
                sh './gradlew build' // Replace with your specific build command
            }
        }

        stage('Test') {
            steps {
                echo 'Running tests...'
                sh './gradlew test' // Replace with your specific test command
            }
        }

        stage('Package') {
            steps {
                echo 'Packaging the project...'
                sh 'tar -czvf app.tar.gz ${BUILD_DIR}' // Example of packaging; customize as needed
            }
        }

        stage('Deploy') {
            steps {
                echo 'Deploying the application...'
                sh 'scp app.tar.gz user@your-server:/path/to/deploy/' // Replace with your deploy command
            }
        }
    }

    post {
        always {
            echo 'Cleaning up temporary files...'
            cleanWs() // Clean the workspace
        }
        success {
            echo 'Pipeline completed successfully!'
        }
        failure {
            echo 'Pipeline failed. Please check the logs.'
        }
    }
}
