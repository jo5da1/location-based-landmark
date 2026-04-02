cd "$(dirname "$0")"
echo "--------------------------------------"
echo "Running: [ landmark-osm-import-worker ]"
echo "--------------------------------------"
docker compose -f docker-compose.yml up
