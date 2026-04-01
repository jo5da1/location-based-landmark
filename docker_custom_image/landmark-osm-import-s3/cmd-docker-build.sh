cd "$(dirname "$0")"
echo "------------------------------------------"
echo "Building Image: [ landmark-osm-import-s3 ]"
echo "------------------------------------------"
docker build --no-cache -t landmark-osm-import-s3 -f Dockerfile .
