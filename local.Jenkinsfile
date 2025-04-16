node() {
    def repoUrl = "gitlab.com/Gumelar09/be_java.git"
    def branch = "main"
    def app = "bejava"
    def tag = ""

    stage('Clone Repository') {
        bat 'rmdir /s /q source || exit 0' // biar gak error kalau folder belum ada
        withCredentials([usernamePassword(credentialsId: 'gitlab-new', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            bat "git clone https://${USERNAME}:${PASSWORD}@${repoUrl} source"
        }
    }

    stage('Checkout Branch') {
        dir("source") {
            bat "git fetch"
            bat "git switch ${branch}"
        }
    }

    stage('Get Git Tag') {
        dir("source") {
            tag = bat(script: 'git rev-parse --short=8 HEAD', returnStdout: true).trim()
            echo "Commit tag is: ${tag}"
        }
    }

    stage('Build Image With Docker') {
        dir("source") {
            bat "docker build -t ${app} ."
            bat "docker images"
            bat "docker tag ${app}:latest docker.io/adhitia09/${app}:${tag}"
        }
    }

    stage('Push to Dockerhub') {
        dir("source") {
            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                // login docker
                bat "echo %PASSWORD% | docker login -u %USERNAME% --password-stdin"

                // push image dengan tag commit
                bat "docker push docker.io/adhitia09/${app}:${tag}"

                // tag sebagai 'latest'
                bat "docker tag docker.io/adhitia09/${app}:${tag} docker.io/adhitia09/${app}:latest"

                // push latest
                bat "docker push docker.io/adhitia09/${app}:latest"
            }
        }
    }

    stage('Run Aplikasi with Container') {
        dir("source") {
            bat "docker-compose up -d"
        }
    }
}