import groovy.json.*

def call(Map defaultVals, String token, String path, String value) {
  
  def curl = """curl --connect-timeout 1 -s --location --header 'X-Vault-Token: ${token}' \
--cert \${VAULT_CLIENT_CERT} --cert-type PEM --key \${VAULT_CLIENT_KEY} \
--key-type PEM --cacert \${VAULT_CACERT} \
'${defaultVals.vault.server}/${defaultVals.vault.api}/${path}'""" 
  def response = sh(returnStdout: true, script: curl)
  
  return parseJSON(response, path, value)
}

def parseJSON(String response, String path, String secretVal) {

  def result = new JsonSlurperClassic().parseText(response)
  if (result.errors) {
    error "Vault: " + result.errors[0].toString()
  } else if (result.data) {
      return result.data[secretVal]
  } else {
    error "Can't retrieve secret ${path}/${secretVal}: ${response} : ${result}"
  }
}