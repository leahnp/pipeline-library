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
                "image": "samsung_cnct/klar:0.0.2-a0ca0f21c6d44e437c76355913e2937e8d242030",
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