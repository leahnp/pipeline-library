// def call(certificateConf, issuerName) {
//   if (!certificateConf) {
//     return
//   }


// todo: correct image
// dynamic flags
// dynamic clair address?

def call() {

  def klarJob = [
    "apiVersion": "batch/v1",
    "kind": "Job",
    "metadata": [
      "name": "klar"
    ],

    "spec": [
      "backoffLimit": "4",
      "template": [
        "spec": [
          "containers": [
            "- name": "klar",
            "image": "leahnp/klar-scratch",
            "args": "- quay.io/samsung_cnct/cluster-controller:prod",
          ]
        ]
      ],
    ]
  ]

  // certConfig.metadata.name = certificateConf.name

  return klarJob
}