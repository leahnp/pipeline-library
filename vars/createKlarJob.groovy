// def call(certificateConf, issuerName) {
//   if (!certificateConf) {
//     return
//   }


// todo: correct image
// dynamic flags
// dynamic clair address?

def call(imageUrl) {
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
            "containers": [
              [
                "name": "klar",
                "image": "leahnp/klar-scratch",
                "args": [
                  imageUrl
                ],
                "env": [
                  [
                    "name": "CLAIR_ADDR",
                    "value": "clairsvc:6060"
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
  // spec.template.spec.containers.image = imageUrl
  echo("dundun")
  // echo klarJob
  return klarJob
}