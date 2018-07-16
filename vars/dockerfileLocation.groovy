def call(defaults, packageName) {
  for (loc in defaults.packages ) {
    if (fileExists("${loc}/${packageName}/Dockerfile")) {
      return "${loc}/${packageName}/Dockerfile"
    }
  }   

  error "Could not find ${packageName}/Dockerfile in any of the ${defaults.packages.toString()} locations"
}