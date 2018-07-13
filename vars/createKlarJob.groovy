def call(imageUrl, maxCVE, maxLevel) {
  echo(imageUrl)

  def klarJobYaml = [
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
                    "value": maxCVE
                  ],
                  [
                    "name": "CLAIR_THRESHOLD",
                    "value": maxLevel
                  ]
                ]
              ]
            ],
          ]
        ],
        "backoffLimit": 0
      ]
    ]

  return klarJobYaml
}