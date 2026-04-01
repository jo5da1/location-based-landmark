hello:
	@echo "Hello MakeFile"

ok:
	@echo "Ok MakeFile"

bye:
	@echo "Bye MakeFile"

all:	\
	build-image-all	\
	create-network

build-image-all:	\
		docker_custom_image	\
		landmark_app_image
	@echo " "
	@echo "All Image Done!"

clean:	\
	clean-images

clean-images:
	sh command-docker-clean.sh
	@echo " "
	@echo "All Image Removed!!"

docker_custom_image:	\
		landmark-localstack-terraform	\
		landmark-osm-fetch-local	\
		landmark-osm-import-local	\
		landmark-osm-import-s3	\
		landmark-osm-import-worker	\
		landmark-osm-importer
	@echo " "
	@echo "All Custom Image Done!!"

landmark-localstack-terraform:
	sh ./../docker_custom_image/landmark-localstack-terraform/cmd-docker-build.sh

landmark-osm-fetch-local:
	sh ./../docker_custom_image/landmark-osm-fetch-local/cmd-docker-build.sh

landmark-osm-import-local:
	sh ./../docker_custom_image/landmark-osm-import-local/cmd-docker-build.sh

landmark-osm-import-s3:
	sh ./../docker_custom_image/landmark-osm-import-s3/cmd-docker-build.sh

landmark-osm-import-worker:
	sh ./../docker_custom_image/landmark-osm-import-worker/cmd-docker-build.sh

landmark-osm-importer:
	sh ./../docker_custom_image/landmark-osm-importer/cmd-docker-build.sh

landmark_app_image:	\
		landmark-geo-query-engine	\
		landmark-nearby	\
		landmark-nearify
	@echo " "
	@echo "All Landmark App Image Done!!"

landmark-geo-query-engine:
	sh ./../landmark-geo-query-engine/cmd-docker-build.sh

landmark-nearby:
	sh ./../landmark-nearby/cmd-docker-build.sh

landmark-nearify:
	sh ./../landmark-nearify/cmd-docker-build.sh

create-network:
	sh command-docker-network.sh
	@echo " "
	@echo "All Network Done!!"




