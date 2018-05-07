def call(certificateConf, issuerName, ingressName) {
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
            "ingressClass": ingressName,
            "domains": []
          ]
        ]
      ],
      "issuerRef": [
        "name": issuerName,
        "kind": "Issuer"
      ]
    ]
  ]

  certConfig.metadata.name = certificateConf.name
  certConfig.spec.secretName = certificateConf.secretName
  certConfig.spec.dnsNames = certificateConf.dnsNames
  certConfig.spec.acme.config[0].domains = certificateConf.domains

  return certConfig
}