// /src/io/cnct/pipeline/cnctPipeline.groovy
package io.cnct.pipeline;

import org.yaml.snakeyaml.Yaml
def execute() {
  podTemplate(label: "${env.JOB_NAME}-${env.BUILD_ID}", containers: [
      containerTemplate(
        name: 'initializer',
        image: 'busybox',
        privileged: true,
        alwaysPullImage: true
      )
    ]) {
    node("${env.JOB_NAME}-${env.BUILD_ID}") {
      stage('Initialize') {
        checkout scm
        echo 'Loading pipeline definition'
  
        pipelineDefinition = [:] 
        
        try {
          Yaml parser = new Yaml()
          pipelineDefinition = parser.load(new File(pwd() + '/pipeline.yaml').text)
        } catch(FileNotFoundException e) {
          error "${pwd()}/pipeline.yaml not found!"
        }
      }

      switch(pipelineDefinition.pipelineType) {
        case 'chart':
          // Instantiate and execute a chart builder
          echo pipelineDefinition.pipelineType
          new chartRepoBuilder().executePipeline(pipelineDefinition)
        default:
          error "Unsupported pipeline '${pipelineDefinition.pipelineType}'!"
      }        
    }
  }
}