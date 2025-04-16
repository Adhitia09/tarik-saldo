node('jdk9') {
    def repoUrl = "gitlab.com/Gumelar09/be_java.git"
    def branch = "main"
    def app = "bejava"
    def tag = ""

    stage('Checkout Branch') {
        dir("source") {
            bat "git fetch"
            bat "git switch ${branch}"
            // bat "mvn clean package -Dmaven.test.skip=true"
        }
    }

    stage('Build Image With Docker') {
        dir("source") {
            bat "mkdir build-folder && mkdir build-folder/target"
            bat "copy Dockerfile build-folder\\Dockerfile"
            // bat "copy target\\*.jar build-folder\\target\\"

            tag = bat(script: "git rev-parse --short=8 HEAD", returnStdout: true).trim()
            bat "docker build -t ${app} ."
            bat "docker images"
            bat "docker tag ${app}:latest docker.io/adhitia09/${app}:${tag}"
        }
    }

    stage('Push to Dockerhub') {
        dir("source") {
            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                bat "docker login -u %USERNAME% -p %PASSWORD%"
                bat "docker push docker.io/adhitia09/${app}:${tag}"
            }

            bat "docker tag docker.io/adhitia09/${app}:${tag} docker.io/adhitia09/${app}:latest"

            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                bat "docker push docker.io/adhitia09/${app}:latest"
            }
        }
    }

    stage('Run Aplikasi with Container') {
        dir("source") {
            // bat "docker pull docker.io/adhitia09/${app}:latest"
            // bat "docker run -d -p 8383:8383 --name be_java docker.io/adhitia09/${app}:latest"
            bat "docker-compose up -d"
        }
    }
}