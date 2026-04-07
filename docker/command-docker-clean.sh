docker stop \
  landmark-nearify \
  landmark-nearby \
  landmark-geo-query-engine \

docker stop \
  pgadmin \
  grafana \
  postgres_pgrouting \
  prometheus \
  postgres-exporter \
  rabbitmq \

docker rm \
  landmark-nearify \
  landmark-nearby \
  landmark-geo-query-engine \

docker rm \
  pgadmin \
  grafana \
  postgres_pgrouting \
  prometheus \
  postgres-exporter \
  rabbitmq \

docker rmi \
  dpage/pgadmin4 \
  grafana/grafana:12.3 \
  pgrouting/pgrouting:16-3.5-4.0 \
  prom/prometheus:v3.5.1 \
  prometheuscommunity/postgres-exporter:v0.19.0  \
  rabbitmq:4.2.3-management-alpine \
  --force

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
  landmark-react-nearify \
  --force

docker network rm \
  landmark-net \
  docker_landmark-net \
  landmark-shared-net \
  landmark-localstack-net \
  landmark-react-nearify_landmark-net \
  landmark-react_landmark-net \

docker volume rm \
  docker_grafana_data \
  docker_prometheus_data \
  docker_rabbitmq_data \
