cd "$(dirname "$0")"
echo "-------------------------------------"
echo "Running: [ landmark-osm-import-local ]"
echo "-------------------------------------"
docker compose -f docker-compose.yml up
