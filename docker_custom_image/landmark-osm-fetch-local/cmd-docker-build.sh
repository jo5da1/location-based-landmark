cd "$(dirname "$0")"
echo "--------------------------------------------"
echo "Building Image: [ landmark-osm-fetch-local ]"
echo "--------------------------------------------"
docker build -t landmark-osm-fetch-local -f Dockerfile .
