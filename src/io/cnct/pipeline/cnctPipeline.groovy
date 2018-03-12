// /src/io/cnct/pipeline/cnctPipeline.groovy
package io.cnct.pipeline;

import org.yaml.snakeyaml.Yaml
def execute() {

  node {
    stage('Initialize') {
      checkout scm
      echo 'Loading pipeline definition'
      Yaml parser = new Yaml()      
      try {
        Map pipelineDefinition = parser.load(new File(pwd() + '/pipeline.yaml').text)
        echo pipelineDefinition.pipelineType
      } catch(FileNotFoundException e) {
        error "${pwd()}/pipeline.yaml not found!"
      }
      echo pipelineDefinition.pipelineType
    }

    echo pipelineDefinition.pipelineType
    switch(pipelineDefinition.pipelineType) {
      case 'chart':
        // Instantiate and execute a chart builder
        new chartRepoBuilder(pipelineDefinition).executePipeline()
      default:
        error "Unsupported pipeline '${pipelineDefinition.pipelineType}'!"
    }    
  }
}