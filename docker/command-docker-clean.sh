docker rmi \
  landmark-localstack-terraform \
  landmark-osm-fetch-local \
  landmark-osm-import-local \
  landmark-osm-import-s3 \
  landmark-osm-import-worker \
  landmark-osm-importer \
  landmark-geo-query-engine \
  landmark-nearby \
  landmark-nearify \
  --force
docker network rm \
  landmark-shared-net \
  landmark-localstack-net