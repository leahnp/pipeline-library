#!/usr/bin/groovy
def call(String path) {  
  def pathChanged = sh(
    returnStatus: true,
    script: """
#!/usr/bin/env bash
set -eo pipefail
merge_commit=\"\$(git rev-parse --short HEAD)\"
child_commit_range=\"\$(git show \"\${merge_commit}\" | grep 'Merge:' | cut -c8- | sed 's/ /../g' || true)\"
if [ \"\${child_commit_range}\" == \"\" ]; then
  child_commit_range=\"\${merge_commit}\"
fi
echo \"\${child_commit_range}\"
git diff-tree --no-commit-id --name-only -r \"\${child_commit_range}\" | grep '${path}'
exit \$?"""
  )

  return pathChanged
}