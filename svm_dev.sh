SVM_PATH=$(readlink -f './build/libs/tech.richardson.svm.mainkt')

svm() {
  temporaryFile=$(mktemp /tmp/svm-eval.XXXXXX)
  TEMPFILE=$temporaryFile $SVM_PATH "$@"
  local exit_code=$?
  eval "$(cat "$temporaryFile")"
  rm -f "$temporaryFile"
  return ${exit_code}
}

svm setup 1>/dev/null