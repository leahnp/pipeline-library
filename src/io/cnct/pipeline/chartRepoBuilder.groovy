// /src/io/cnct/pipeline/chartRepoBuilder.groovy
package io.cnct.pipeline;

def executePipeline(pipelineDefinition) {
  properties(
    [ disableConcurrentBuilds() ]
  )

  podTemplate(label: name.node(), containers: [
    containerTemplate(
      name: 'dind',
      image: 'docker:stable-dind',
      privileged: true,
      alwaysPullImage: true
    ),
    containerTemplate(
      name: 'docker', 
      image: 'docker:stable', 
      command: 'cat', 
      ttyEnabled: true,
      envVars: [envVar(key: 'DOCKER_HOST', value: 'localhost:2375')]
    )
  ],
  volumes: [
    emptyDirVolume(mountPath: '/var/lib/docker', memory: false),
  ]) {
    node(name.node()) {
      container('docker') {
        stage('Build') {
          sh "docker run busybox sh -c 'echo hi'"
        }
      }
    }
  }
}

return this