cd "$(dirname "$0")"
echo "-----------------"
echo "Running: [ react ]"
echo "-----------------"
docker compose --profile landmark-react up
