// /src/io/cnct/pipeline/cnctPipeline.groovy
package io.cnct.pipeline;

import org.yaml.snakeyaml.Yaml
def execute() {
  podTemplate(label: name.node()) {
    node(name.node()) {
      stage('Checkout') {
        echo 'Checking out changes'
        checkout scm
      }

      stage('Initialize') {
        echo 'Loading pipeline definition'
        pipelineDefinition = [:] 
        
        try {
          pipelineDefinition = parseYaml {
            yaml = readFile("${pwd()}/pipeline.yaml")
          }
        } catch(FileNotFoundException e) {
          error "${pwd()}/pipeline.yaml not found!"
        }
      }     
    }        
  }

  switch(pipelineDefinition.pipelineType) {
    case 'chart':
      // Instantiate and execute a chart builder
      new chartRepoBuilder().executePipeline(pipelineDefinition)
    default:
      error "Unsupported pipeline '${pipelineDefinition.pipelineType}'!"
  }  
}