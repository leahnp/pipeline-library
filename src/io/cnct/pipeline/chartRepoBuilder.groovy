// /src/io/cnct/pipeline/chartRepoBuilder.groovy
package io.cnct.pipeline;

chartRepoBuilder(pipelineDefinition) {
  // Create a globally accessible variable that makes
  // the YAML pipeline definition available to all scripts
  pd = pipelineDefinition
}

def executePipeline() {
  node {
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
      stage('Build') {
        sh "docker run busybox sh -c 'echo hi'"
      }
    }
  }
}

return this