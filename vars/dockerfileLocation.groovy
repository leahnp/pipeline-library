def call(defaults, locationOverride, context) {
  if (locationOverride) {
    if (fileExists("${locationOverride}/${context}/Dockerfile")) {
      return "${locationOverride}/${context}/Dockerfile""
    } else {
      error "Could not find ${locationOverride}/${context}/Dockerfile"
    }
  }

  for (loc in defaults.packages ) {
    if (fileExists("${loc}/${context}/Dockerfile")) {
      return "${loc}/${context}/Dockerfile"
    }
  }   

  error "Could not find ${context}/Dockerfile in any of the ${defaults.packages.toString()} locations"
}