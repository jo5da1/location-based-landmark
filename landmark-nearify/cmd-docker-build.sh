cd "$(dirname "$0")"
echo "------------------------------------"
echo "Building Image: [ landmark-nearify ]"
echo "------------------------------------"
docker build -t landmark-nearify .
