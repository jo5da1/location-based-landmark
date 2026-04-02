cd "$(dirname "$0")"
echo "------------------------------"
echo "Running: [ aws-s3-upload-osm ]"
echo "------------------------------"
awslocal s3 cp _postgres/data/release/map.osm s3://s3-osm-bucket/
