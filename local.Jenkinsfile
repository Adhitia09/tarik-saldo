node() {
    def repoUrl = "gitlab.com/Gumelar09/be_java.git"
    def branch = "main"
    def app = "bejava"
    

    stage ('Clone Repository') {
        sh "rm -rf source"
        withCredentials([usernamePassword(credentialsId: 'gitlab-new', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh "git clone https://${USERNAME}:${PASSWORD}@${repoUrl} source"
            
        }
    }

    stage ('Checkout Branch') {
        dir("source") {
            sh "git fetch"
            sh "git switch ${branch}"
            //sh "mvn clean package -Dmaven.test.skip=true"
        }
    }

    stage ('Build Image With Docker') {
        dir("source") {
            sh "mkdir -p build-folder/target"
            sh "cp Dockerfile build-folder/Dockerfile"
            //sh "cp target/*.jar build-folder/target/"

            def tag = sh(returnStdout: true, script: "git rev-parse --short=8 HEAD").trim();
            //sh "sleep 60"
            sh "docker build -t ${app} . "
            sh "docker images"
            sh "docker tag ${app}:latest docker.io/adhitia09/${app}:${tag}"
        }
    }

    stage ('Push to Dockerhub') {
        dir("source") {
            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                sh "docker push docker.io/adhitia09/${app}:${tag}  https://\${USERNAME}:\${PASSWORD}@index.docker.io/v1/ source "
            }

            sh "docker tag docker.io/adhitia09/${app}:${tag} docker.io/adhitia09/${app}:latest"

            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                sh "docker push docker.io/adhitia09/${app}:latest  https://\${USERNAME}:\${PASSWORD}@index.docker.io/v1/ source "
            }
        }
    }

    stage ('Run Aplikasi with Container') {
        dir("source") {
            //sh "docker pull docker.io/adhitia09/${app}:latest"
            //sh "docker run -d -p 8383:8383 --name be_java docker.io/adhitia09/${app}:latest "
            sh "docker-compose up -d"
        }
    }
}