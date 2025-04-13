node {
    def repoUrl = "gitlab.com/Gumelar09/be_java.git"
    def branch = "main"
    def app = "bejava"
    def fullUrl = "https://${USERNAME}:${PASSWORD}@${repoUrl}"

    stage ('Clone Repository') {
        withCredentials([usernamePassword(credentialsId: 'gitlab-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            echo "Repo URL: ${fullUrl}" // Debug
            sh "git clone ${fullUrl} source"
        }
    }

    stage ('Checkout Branch') {
        sh "git fetch"
        sh "git switch branch ${branch}"
        sh "mvn test"
        sh "mvn clean package -Dmaven.test.skip=true"
    }

    stage ('Build Image With Docker') {
        sh "mkdir -p build-folder/target"
        sh "cp Dockerfile build-folder/Dockerfile"
        sh "cp target/*.jar build-folder/target/"

        def tag = sh(returnStdout: true, script: "git rev-parse --short=8 HEAD").trim();

        sh "docker build -t ${app} . "
        sh "docker tag ${app}:latest docker.io/adhitia09/${app}:${tag}"
    }

    stage ('Push to Dockerhub') {
        withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh "docker push docker.io/adhitia09/${app}:${tag}  https://\${USERNAME}:\${PASSWORD}@index.docker.io/v1/ source "
        }

        sh "docker tag docker.io/adhitia09/${app}:${tag} docker.io/adhitia09/${app}:latest"

        withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
            sh "docker push docker.io/adhitia09/${app}:latest  https://\${USERNAME}:\${PASSWORD}@index.docker.io/v1/ source "
        }
    }

    stage ('Run Aplikasi with Container') {
        sh "docker-compose -d up"
    }
}