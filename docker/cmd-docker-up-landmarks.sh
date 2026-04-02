cd "$(dirname "$0")"
echo "-----------------"
echo "Running: [ apps ]"
echo "-----------------"
docker compose --profile landmark up
#docker compose --profile landmark up --scale landmark-geo-query-engine=0