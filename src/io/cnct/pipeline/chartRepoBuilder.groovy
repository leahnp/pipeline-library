// /src/io/cnct/pipeline/chartRepoBuilder.groovy
package io.cnct.pipeline;

def executePipeline(pipelineDefinition) {
  
  pd = pipelineDefinition

  properties(
    [ disableConcurrentBuilds() ]
  )

  podTemplate(label: "${env.JOB_NAME}-${env.BUILD_ID}", containers: [
    containerTemplate(
      name: 'dind',
      image: 'docker:stable-dind',
      privileged: true,
      alwaysPullImage: true
    ),
    containerTemplate(
      name: 'docker', 
      image: 'docker', 
      command: 'cat', 
      ttyEnabled: true,
      envVars: [envVar(key: 'DOCKER_HOST', value: 'localhost:2375')]
    )
  ],
  volumes: [
    emptyDirVolume(mountPath: '/var/lib/docker', memory: false),
  ]) {
    node("${env.JOB_NAME}-${env.BUILD_ID}") {
      stage('Build') {
        sh "docker run busybox sh -c 'echo hi'"
      }
    }
  }
}

return this