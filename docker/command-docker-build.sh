# docker_custom_image
sh ./../docker_custom_image/landmark-localstack-terraform/cmd-docker-build.sh
sh ./../docker_custom_image/landmark-osm-fetch-local/cmd-docker-build.sh
sh ./../docker_custom_image/landmark-osm-import-local/cmd-docker-build.sh
sh ./../docker_custom_image/landmark-osm-import-s3/cmd-docker-build.sh
sh ./../docker_custom_image/landmark-osm-import-worker/cmd-docker-build.sh
sh ./../docker_custom_image/landmark-osm-importer/cmd-docker-build.sh

# landmark
sh ./../landmark-geo-query-engine/cmd-docker-build.sh
sh ./../landmark-nearby/cmd-docker-build.sh
sh ./../landmark-nearify/cmd-docker-build.sh

