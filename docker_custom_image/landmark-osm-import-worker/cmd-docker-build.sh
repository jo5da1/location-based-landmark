cd "$(dirname "$0")"
echo "---------------------------------------------"
echo "Building Image: [ landmark-osm-import-worker ]"
echo "---------------------------------------------"
docker build -t landmark-osm-import-worker -f Dockerfile .
