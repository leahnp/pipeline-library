import com.cloudbees.groovy.cps.NonCPS

def call(Map defaults, String locationOverride, String packageName) {
  return getDockerfileLocation(defaults, locationOverride, packageName)
}

@NonCPS
def getDockerfileLocation(defaults, locationOverride, packageName) {
  if (locationOverride?.trim()) {
    if (fileExists("${locationOverride}/${packageName}/Dockerfile")) {
      return "${locationOverride}/${packageName}/Dockerfile""
    } else {
      error "Could not find ${locationOverride}/${packageName}/Dockerfile"
    }
  }

  for (loc in defaults.packages) {
    if (fileExists("${loc}/${packageName}/Dockerfile")) {
      return "${loc}/${packageName}/Dockerfile"
    }
  }   

  error "Could not find ${packageName}/Dockerfile in any of the ${defaults.packages.toString()} locations"
}