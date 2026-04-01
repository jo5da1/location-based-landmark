cd "$(dirname "$0")"
echo "---------------------------------------------"
echo "Building Image: [ landmark-geo-query-engine ]"
echo "---------------------------------------------"
docker build -t landmark-geo-query-engine .
