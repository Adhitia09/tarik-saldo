node() {
    def repoUrl = "gitlab.com/Gumelar09/be_java.git"
    def branch = "main"
    def app = "bejava"

    //stage ('Clone Repository') {
    //    withCredentials([usernamePassword(credentialsId: 'gitlab-new', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
    //        bat "git clone https://${USERNAME}:${PASSWORD}@${repoUrl} source"
    //        
    //    }
    //}
    
    stage('Checkout Branch') {
        dir("source") {
            bat "git fetch"
            bat "git switch ${branch}"
            // bat "mvn clean package -Dmaven.test.skip=true"
        }
    }

    stage('Build Image With Docker') {
        dir("source") {
            //bat "mkdir -p build-folder/target"
            //bat "cp Dockerfile build-folder/Dockerfile"
            // bat "cp target/*.jar build-folder/target/"

            def tag = bat(script: 'git rev-parse --short=8 HEAD', returnStdout: true).trim()
            echo "Git tag: ${tag}" // tampilkan hash buat konfirmasi
            
            bat "docker build -t ${app} ."
            bat "docker images"
            bat "docker tag ${app}:latest docker.io/adhitia09/${app}:${tag}"
        }
    }

    stage('Pubat to Dockerhub') {
        dir("source") {
            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                bat "echo \$PASSWORD | docker login -u \$USERNAME --password-stdin"
                bat "docker pubat docker.io/adhitia09/${app}:${tag}"
            }

            bat "docker tag docker.io/adhitia09/${app}:${tag} docker.io/adhitia09/${app}:latest"

            withCredentials([usernamePassword(credentialsId: 'docker-creds', usernameVariable: 'USERNAME', passwordVariable: 'PASSWORD')]) {
                bat "echo \$PASSWORD | docker login -u \$USERNAME --password-stdin"
                bat "docker pubat docker.io/adhitia09/${app}:latest"
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
