def call(String name) {
  return "${name.toLowerCase().replaceAll('[^A-Za-z0-9]', '-')}-jenkins"
}