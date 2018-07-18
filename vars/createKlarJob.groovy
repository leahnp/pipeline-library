def call(imageUrl, maxCve, maxLevel) {
  echo("Creating job template to scan ${imageUrl} for vulnerabilities")
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
                "image": "quay.io/samsung_cnct/klar:prod",
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
                    "value": maxLevel
                  ],
                  [
                    "name": "CLAIR_THRESHOLD",
                    "value": maxCve
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
