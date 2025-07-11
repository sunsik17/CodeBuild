# CodeBuild

- Jenkins를 이용한 CI/CD pipeline을 구축해보기 위한 프로젝트입니다.
- **Modules**저장소를 그대로 사용하였습니다.
- Modules중 Module-user를 배포했습니다.

*test api*

http://ec2-3-34-151-37.ap-northeast-2.compute.amazonaws.com:8082/ping (만료 테스트 불가능)


### 아키텍처
![제목 없는 다이어그램 drawio (3)](https://github.com/sunsik17/CodeBuild/assets/117346927/383f821b-2f33-466c-ad3b-16c2a3060c76)


### Pipeline script
``` groovy
pipeline {
    agent any

    stages {
        stage('clone github repository') {
            steps {
                git branch: 'main', url: 'https://github.com/sunsik17/CodeBuild.git' // Git 저장소 클론
            }
        }
        
        stage('codebuild build') {
            steps {
                dir ('module-user') {
                    sh '../gradlew bootJar'
                }
            }
        }
        
        stage('docker login') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'docker-login', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
                    sh "docker login -u ${DOCKER_USERNAME} -p ${DOCKER_PASSWORD}"
                }
            }
        }
        
        stage('docker container job') {
            steps {
                script {
                    def tag = sh(returnStdout: true, script: 'docker images sunsik/codebuild --format "{{.Tag}}"').trim()
                    if (tag && tag != null) {
                        def container_id = sh(returnStdout: true, script: 'docker container ls -lq').trim()
                        sh "docker stop ${container_id}"
                        sh "docker rm ${container_id}"
                        sh "docker rmi sunsik/codebuild:${tag}"
                        tag = (tag.toFloat() + 0.1).toString
                    } else {
                        tag = "0.0"
                    }
                    // Docker 이미지 빌드 및 태그
                    dir('module-user') {
                        sh "docker build -t sunsik/codebuild:${tag} ."
                        sh "docker push sunsik/codebuild:${tag}"
                        sh "docker pull sunsik/codebuild:${tag}"
                        withCredentials([usernamePassword(credentialsId: 'mailgun-secret', usernameVariable: 'MAILGUN_OWNER_EMAIL', passwordVariable: 'MAILGUN_KEY')]) {
                            sh "docker run -d -p 8082:8082 -e \"MAILGUN_OWNER_EMAIL=${MAILGUN_OWNER_EMAIL}\" -e \"MAILGUN_KEY=${MAILGUN_KEY}\" sunsik/codebuild:${tag}"
                        }
                    }
                }
            }
        }
    }
}
```

### 개선해야할 점
- docker job 관련 조금더 상세한 조건이 필요할 것 같습니다.
- 현재는 main branch만을 배포하고 있습니다. 따라서 module-user, module-admin중 하나만 수정해도 모든 서버가 재시작되는 문제가 발생합니다. 배포되는 branch를 설정해 문제를 해결하고 싶습니다.
