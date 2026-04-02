cd "$(dirname "$0")"
echo "-------------------------------------"
echo "Running: [ landmark-osm-fetch-local ]"
echo "-------------------------------------"
docker compose -f docker-compose.yml up
