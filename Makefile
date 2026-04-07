.PHONY: \
		docker_custom_image \
		landmark-geo-query-engine \
		landmark-nearby \
		landmark-nearify

help:
	@echo "Usage:"
	@echo "------------------"
	@echo "  make show-system         - Show system"

	@echo " "
	@echo "  make clean-build-cache   - Remove Docker build cache"
	@echo "  make clean-system        - Remove unused containers, networks, images"
	@echo "  make clean-dangling      - Remove dangling images"
	@echo "  make clean-volumes       - Remove unused volumes"

	@echo " "
	@echo "  make clean          - Run custom clean script"
	@echo "  make clean-images   - Run custom clean script"

	@echo " "
	@echo "  make all             - Create network & Build all images"
	@echo "  make create-network  - Create network"
	@echo "  make build-image-all - Build all images"

	@echo " "
	@echo "  make hello | ok | bye - Test commands"
	@echo "------------------"

	@echo "Run:"
	@echo " "
	@echo "  make run-infra         -"
	@echo "  make run-app           -"
	@echo "  make run-monitoring    -"
	@echo "  make run-localstack    -"
	@echo "------------------"

	@echo "Import:"
	@echo " "
	@echo "  make run-landmark-osm-importer       -"
	@echo "  make run-landmark-osm-fetch-local    -"
	@echo "  make run-landmark-osm-import-local   -"
	@echo "------------------"

	@echo "Localstack:"
	@echo " "
	@echo "  make show-aws       -"

	@echo "------------------"
	@echo "Import via Localstack:(make run-localstack)"
	@echo " "
	@echo "  make run-landmark-osm-import-s3      -"
	@echo "------------------"

	@echo "Import via Lambda:(make run-localstack)"
	@echo " "
	@echo "  make run-landmark-osm-import-worker  -"
	@echo "  make upload-osm-s3                   -"
	@echo "  make trigger-import                  -"
	@echo "------------------"

show-system:
	@echo " "
	@echo "[Images]"
	@echo "------------------"
	docker images

	@echo " "
	@echo "[Networks]"
	@echo "------------------"
	docker network ls

	@echo " "
	@echo "[Volume]"
	@echo "------------------"
	docker volume ls

	@echo " "
	@echo "[Container]"
	@echo "------------------"
	docker ps -a
	@echo " "

	@echo " "
	@echo "[Images][Landmark]"
	@echo "------------------"
	docker images | grep landmark
	@echo " "



	@echo " "
	@echo "[Container]"
	@echo "------------------"
	docker ps -a --format "table {{.ID}}\t{{.State}}\t{{.Names}}\t{{.Status}}" | sort -k3
	@echo " "

clean-build-cache:
	docker builder prune -f

clean-system:
	docker system prune -f

clean-dangling:
	docker image prune -f

clean-volumes:
	docker volume prune -f

hello:
	@echo "Hello MakeFile"

ok:
	@echo "Ok MakeFile"

bye:
	@echo "Bye MakeFile"

clean: \
	clean-images

clean-images:
	sh docker/command-docker-clean.sh
	@echo " "
	@echo "All Image Removed!!"

all: \
	create-network \
	build-image-all

create-network:
	sh docker/command-docker-network.sh
	@echo " "
	@echo "All Network Done!!"

build-image-all: \
		build-docker_custom_image \
		build-landmark_app_image
	@echo " "
	@echo "All Image Done!"

build-docker_custom_image: \
		build-landmark-localstack-terraform \
		build-landmark-osm-fetch-local \
		build-landmark-osm-import-local \
		build-landmark-osm-import-s3 \
		build-landmark-osm-import-worker \
		build-landmark-osm-importer
	@echo " "
	@echo "All Custom Image Done!!"

build-landmark-localstack-terraform:
	sh docker_custom_image/landmark-localstack-terraform/cmd-docker-build.sh

build-landmark-osm-fetch-local:
	sh docker_custom_image/landmark-osm-fetch-local/cmd-docker-build.sh

run-landmark-osm-fetch-local:
	sh docker_custom_image/landmark-osm-fetch-local/cmd-docker-up.sh

build-landmark-osm-import-local:
	sh docker_custom_image/landmark-osm-import-local/cmd-docker-build.sh

run-landmark-osm-import-local:
	sh docker_custom_image/landmark-osm-import-local/cmd-docker-up.sh

build-landmark-osm-import-s3:
	sh docker_custom_image/landmark-osm-import-s3/cmd-docker-build.sh

run-landmark-osm-import-s3:
	sh docker_custom_image/command-aws-s3-upload-osm.sh
	sh docker_custom_image/landmark-osm-import-s3/cmd-docker-up.sh

build-landmark-osm-import-worker:
	sh docker_custom_image/landmark-osm-import-worker/cmd-docker-build.sh

run-landmark-osm-import-worker:
	sh docker_custom_image/landmark-osm-import-worker/cmd-docker-up.sh

build-landmark-osm-importer:
	sh docker_custom_image/landmark-osm-importer/cmd-docker-build.sh

run-landmark-osm-importer:
	sh docker_custom_image/landmark-osm-importer/cmd-docker-up.sh

build-landmark_app_image: \
		build-landmark-geo-query-engine \
		build-landmark-nearby \
		build-landmark-nearify \
		build-landmark-react-nearify
	@echo " "
	@echo "All Landmark App Image Done!!"

build-landmark-geo-query-engine:
	@echo "landmark-geo-query-engine"
	sh landmark-geo-query-engine/cmd-docker-build.sh

build-landmark-nearby:
	sh landmark-nearby/cmd-docker-build.sh

build-landmark-nearify:
	sh landmark-nearify/cmd-docker-build.sh

build-landmark-react-nearify:
	sh landmark-react/landmark-react-nearify/cmd-docker-build.sh

run-infra:
	sh docker/cmd-docker-up.sh

run-app:
	sh docker/cmd-docker-up-landmarks.sh
	sh landmark-react/cmd-docker-up.sh

run-monitoring:
	@echo " Not Available Yet"

run-localstack:
	sh docker_localstack_terraform/cmd-docker-up.sh

show-aws:
	sh docker_localstack_terraform/request/list-lambda.sh
	sh docker_localstack_terraform/request/list-queues.sh
	sh docker_localstack_terraform/request/list-s3.sh

trigger-import:
	sh docker_localstack_terraform/request/apigateway-import-info.sh
	sh docker_localstack_terraform/request/trigger-import.sh

upload-osm-s3:
	sh docker_custom_image/command-aws-s3-upload-osm.sh






