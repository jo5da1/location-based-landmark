cd "$(dirname "$0")"
echo "----------------------------------"
echo "Running: [ landmark-osm-importer ]"
echo "----------------------------------"
docker compose -f docker-compose-osm-importer.yml up
