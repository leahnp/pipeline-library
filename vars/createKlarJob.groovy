// def call(certificateConf, issuerName) {
//   if (!certificateConf) {
//     return
//   }


// todo: correct image
// dynamic flags
// dynamic clair address?

def call() {
  echo("beans")

  def klarJob = [
      "apiVersion": "batch/v1",
      "kind": "Job",
      "metadata": [
        "name": "klar"
      ],
      "spec": [
        "template": [
          "spec": [
            "containers": [
              [
                "name": "klar",
                "image": "leahnp/klar-scratch",
                "args": [
                  "quay.io/samsung_cnct/fluentd-central:latest"
                ],
                "env": [
                  [
                    "name": "CLAIR_ADDR",
                    "value": "10.41.99.111:6060"
                  ],
                  [
                    "name": "CLAIR_OUTPUT",
                    "value": "High"
                  ],
                  [
                    "name": "CLAIR_THRESHOLD",
                    "value": "10"
                  ]
                ]
              ]
            ],
            "restartPolicy": "Never"
          ]
        ],
        "backoffLimit": 4
      ]
    ]

  // certConfig.metadata.name = certificateConf.name
  echo("dundun")
  // echo klarJob
  return klarJob
}