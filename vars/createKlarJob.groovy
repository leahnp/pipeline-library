// def call(certificateConf, issuerName) {
//   if (!certificateConf) {
//     return
//   }


// todo: correct image
// dynamic flags
// dynamic clair address?

def call(imageUrl, maxCritical) {
  echo("beans")
  echo(imageUrl)

  def klarJob = [
      "apiVersion": "batch/v1",
      "kind": "Job",
      "metadata": [
        "name": "klar"
      ],
      "spec": [
        "template": [
          "spec": [
            "restartPolicy": "Never",
            "containers": [
              [
                "name": "klar",
                "image": "leahnp/klar-scratch",
                "args": [
                  'quay.io/samsung_cnct/fluentd_daemonset:latest'
                ],
                "env": [
                  [
                    "name": "CLAIR_ADDR",
                    "value": "clairsvc:6060"
                  ],
                  [
                    "name": "CLAIR_OUTPUT",
                    "value": "Medium"
                  ],
                  [
                    "name": "CLAIR_THRESHOLD",
                    "value": '1'
                  ]
                ]
              ]
            ]
          ]
        ],
        "backoffLimit": 4
      ]
    ]

  // certConfig.metadata.name = certificateConf.name
  // spec.template.spec.containers.image = imageUrl
  echo("dundun")
  // echo klarJob
  return klarJob
}