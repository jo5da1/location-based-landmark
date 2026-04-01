cd "$(dirname "$0")"
echo "-----------------------------------"
echo "Building Image: [ landmark-nearby ]"
echo "-----------------------------------"
docker build -t landmark-nearby .
