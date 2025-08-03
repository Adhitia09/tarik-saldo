node() {
    def repoUrl = "gitlab.com/Gumelar09/be_java.git"
    def branch = "main"
    def app = "tarik-saldo"
    def tag = ""

    stage('Clone Repository') {
        if (fileExists('source')) {
            echo 'Directory source already exists, cleaning it up...'
            sh "rm -rf source"  // Hapus semua file di dalamnya
        }
        //withCredentials([usernamePassword(credentialsId: 'gitlab-new', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh "git clone https:{repoUrl} source"
        //}
    }

    stage('Checkout Branch') {
        dir("source") {
            sh "git fetch"
            sh "git switch ${branch}"
        }
    }

    stage('Get Git Tag') {
        dir("source") {
            tag = sh(script: 'git rev-parse --short=8 HEAD', returnStdout: true).readLines().last().trim()
            echo "Commit tag is: ${tag}"
        }
    }

    stage('Build Image With Docker') {
        dir("source") {
            sh "docker build -t ${app} ."
            sh "docker images"
            sh "docker tag ${app}:latest docker.io/adhitia09/${app}:${tag}"
        }
    }

    stage('Push to Dockerhub') {
        dir("source") {
            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                // login docker
                sh "echo %PASSWORD% | docker login -u %USERNAME% --password-stdin"

                // push image dengan tag commit
                sh "docker push docker.io/adhitia09/${app}:${tag}"

                // tag sebagai 'latest'
                sh "docker tag docker.io/adhitia09/${app}:${tag} docker.io/adhitia09/${app}:latest"

                // push latest
                sh "docker push docker.io/adhitia09/${app}:latest"
            }

            sh "docker images"
            sh "docker rmi adhitia09/${app}:${tag}"
        }
    }

    stage('Run Aplikasi with Container') {
        dir("source") {
            sh "docker-compose up -d"
        }
    }
}