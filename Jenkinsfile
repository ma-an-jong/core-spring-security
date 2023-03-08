def component = [
		LoginService: true,
		Nginx: true
]
pipeline {
	agent any
	stages {
		stage("Checkout") {
			steps {
				checkout scm
			}
		}
		stage("Build") {
			steps {
                script {
					component.each{ entry ->
						stage ("${entry.key} Build"){
							if(entry.value){
								var = entry.key
								sh "docker compose build ${var.toLowerCase()}"
							}
						}
					}
				}
			}
		}
		stage("Tag and Push") {
			steps {
                script {
					component.each{ entry ->
						stage ("${entry.key} Push"){
							if(entry.value){
								var = entry.key
								withCredentials([[$class: 'UsernamePasswordMultiBinding',
								credentialsId: 'docker-hub',
								usernameVariable: 'DOCKER_USER_ID',
								passwordVariable: 'DOCKER_USER_PASSWORD'
								]]){
								sh "docker tag docker-pipeline-${var.toLowerCase()}:latest ${DOCKER_USER_ID}/login-service:${var.toLowerCase()}"
								sh "docker login -u ${DOCKER_USER_ID} -p ${DOCKER_USER_PASSWORD}"
								sh "docker push ${DOCKER_USER_ID}/login-service:${var.toLowerCase()}"
								}
							}
						}
					}
				}
			}
		}
	}
}