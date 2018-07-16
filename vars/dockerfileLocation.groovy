def call(defaults, container) {
  if (container.locationOverride) {
    if (fileExists("${container.locationOverride}/${container.context}/Dockerfile")) {
      return "${container.locationOverride}/${container.context}/Dockerfile""
    } else {
      error "Could not find ${container.locationOverride}/${container.context}/Dockerfile"
    }
  }

  for (loc in defaults.packages ) {
    if (fileExists("${loc}/${container.context}/Dockerfile")) {
      return "${loc}/${container.context}/Dockerfile"
    }
  }   

  error "Could not find ${container.context}/Dockerfile in any of the ${defaults.packages.toString()} locations"
}