cd "$(dirname "$0")"
echo "------------------------------------------------"
echo "Building Image: [ landmark-localstack-terraform ]"
echo "------------------------------------------------"
docker build -t landmark-localstack-terraform .
