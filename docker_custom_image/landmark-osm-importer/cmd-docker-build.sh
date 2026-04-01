cd "$(dirname "$0")"
echo "----------------------------------------"
echo "Building Image: [ landmark-osm-importer ]"
echo "----------------------------------------"
docker build -t landmark-osm-importer .
