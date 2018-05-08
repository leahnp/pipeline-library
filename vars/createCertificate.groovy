def call(certificateConf, issuerName) {
  if (!certificateConf) {
    return
  }

  def certConfig = [
    "apiVersion": "certmanager.k8s.io/v1alpha1",
    "kind": "Certificate",
    "metadata": [
      "name": ""
    ],
    "spec": [
      "secretName": "",
      "dnsNames": [],
      "acme": [
        "config": [ 
          [
            "dns01": [
              "provider": "route53",
            ],
            "domains": []
          ]
        ]
      ],
      "issuerRef": [
        "name": issuerName,
        "kind": "ClusterIssuer"
      ]
    ]
  ]

  certConfig.metadata.name = certificateConf.name
  certConfig.spec.secretName = certificateConf.secretName
  certConfig.spec.dnsNames = certificateConf.dnsNames
  certConfig.spec.acme.config[0].domains = certificateConf.domains

  return certConfig
}