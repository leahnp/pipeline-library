#!/usr/bin/groovy
def call(String path, String changeId = "null") { 
  def scriptVal = """
git --no-pager diff --name-only origin/master origin/master^ | grep '${path}'
exit \$?"""

  if (changeId != "null") {
    scriptVal = """
#!/usr/bin/env bash
set -eo pipefail
git fetch origin +refs/pull/${changeId}/merge
git --no-pager diff --name-only FETCH_HEAD \$(git merge-base FETCH_HEAD origin/master) | grep '${path}'
exit \$?"""
  }

  def pathChanged = sh(
    returnStatus: true,
    script: scriptVal
  )

  return pathChanged
}