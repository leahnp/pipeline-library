// /src/io/cnct/pipeline/cnctPipeline.groovy
package io.cnct.pipeline;

def execute() {

  node {

    podTemplate(label: "CI-${application}", containers: [
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
        Map pipelineDefinition = null 
        
        try {
          pipelineDefinition = parser.load(new File(pwd() + '/pipeline.yml').text)
        } catch(FileNotFoundException e) {
          error "${pwd()}/pipeline.yml not found!"
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