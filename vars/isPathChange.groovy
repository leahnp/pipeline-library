#!/usr/bin/groovy
def call(String path, String changeId = "") { 
  def scriptVal = "git diff --name-only HEAD HEAD^"
  if (changeId != "") {
    scriptVal = """
git fetch origin +refs/pull/${changeId}/merge
git --no-pager diff --name-only FETCH_HEAD \$(git merge-base FETCH_HEAD master) | grep '\${path}'"""
  }

  def pathChanged = sh(
    returnStatus: true,
    script: scriptVal
  )

  return pathChanged
}