cd "$(dirname "$0")"
echo "------------------------------------------"
echo "Building Image: [ landmark-react-nearify ]"
echo "------------------------------------------"
docker build --no-cache -t landmark-react-nearify .
