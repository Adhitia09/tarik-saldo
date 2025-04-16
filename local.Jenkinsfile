node('jdk9') {
    def repoUrl = "gitlab.com/Gumelar09/be_java.git"
    def branch = "main"
    def app = "bejava"

    stage ('Checkout Branch') {
        dir("source") {
            bat "git fetch"
            bat "git switch ${branch}"
            //bat "mvn clean package -Dmaven.test.skip=true"
        }
    }

    stage ('Build Image With Docker') {
        dir("source") {
            bat "mkdir -p build-folder/target"
            bat "cp Dockerfile build-folder/Dockerfile"
            //bat "cp target/*.jar build-folder/target/"

            def tag = bat(returnStdout: true, script: "git rev-parse --batort=8 HEAD").trim();
            //bat "sleep 60"
            bat "docker build -t ${app} . "
            bat "docker images"
            bat "docker tag ${app}:latest docker.io/adhitia09/${app}:${tag}"
        }
    }

    stage ('Pubat to Dockerhub') {
        dir("source") {
            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                bat "docker pubat docker.io/adhitia09/${app}:${tag}  https://\${USERNAME}:\${PASSWORD}@index.docker.io/v1/ source "
            }

            bat "docker tag docker.io/adhitia09/${app}:${tag} docker.io/adhitia09/${app}:latest"

            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                bat "docker pubat docker.io/adhitia09/${app}:latest  https://\${USERNAME}:\${PASSWORD}@index.docker.io/v1/ source "
            }
        }
    }

    stage ('Run Aplikasi with Container') {
        dir("source") {
            //bat "docker pull docker.io/adhitia09/${app}:latest"
            //bat "docker run -d -p 8383:8383 --name be_java docker.io/adhitia09/${app}:latest "
            bat "docker-compose up -d"
        }
    }
}