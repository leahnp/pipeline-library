// /src/io/cnct/pipeline/cnctPipeline.groovy
package io.cnct.pipeline;

import org.yaml.snakeyaml.Yaml
import io.cnct.pipeline.*

def execute() {

  node {

    podTemplate(label: "${env.JOB_NAME}-${env.BUILD_ID}", containers: [
      containerTemplate(
        name: 'initializer',
        image: 'busybox',
        privileged: true,
        alwaysPullImage: true
      )
    ]) {

      stage('Initialize') {
        checkout scm
        echo 'Loading pipeline definition'
        
        Yaml parser = new Yaml()
        def pipelineDefinition = [:] 
        
        try {
          pipelineDefinition = parser.load(new File(pwd() + '/pipeline.yaml').text)
        } catch(FileNotFoundException e) {
          error "${pwd()}/pipeline.yaml not found!"
        }

        switch(pipelineDefinition.pipelineType) {
          case 'chart':
            // Instantiate and execute a chart builder
            new chartRepoBuilder(pipelineDefinition).executePipeline()
          default:
            error "Unsupported pipeline '${pipelineDefinition.pipelineType}'!"
        }
      }
    }
  }
}