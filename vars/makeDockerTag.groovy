def call(Map defaultVals, String sha) {
  def versionFileContents = readFile(defaultVals.versionfile)
  return "${versionFileContents.trim()}-${sha}"
}