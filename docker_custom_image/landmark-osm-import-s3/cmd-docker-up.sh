cd "$(dirname "$0")"
echo "-----------------------------------"
echo "Running: [ landmark-osm-import-s3 ]"
echo "-----------------------------------"
docker compose -f docker-compose.yml up
