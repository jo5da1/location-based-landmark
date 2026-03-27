# Use official pgrouting image as base
#FROM pgrouting/pgrouting:16-3.5-4.0
FROM debian:bookworm-slim

# Install osm2pgsql
# Install osm2pgrouting
# Install awscli
RUN apt-get update && apt-get install -y \
    --no-install-recommends \
    osm2pgsql \
    osm2pgrouting \
    postgresql-client \
    ca-certificates \
    awscli \
    && rm -rf /var/lib/apt/lists/*

# Create data directory
#RUN mkdir -p /data /data/pbf /data/osm

ARG FILE_NAME=osm_import_s3.sh

# Copy the import script into the image
COPY ${FILE_NAME}  /usr/local/bin/${FILE_NAME}
RUN chmod +x /usr/local/bin/${FILE_NAME}

# Set working directory
WORKDIR /data

# Default command runs the import script
CMD ["/usr/local/bin/osm_import_s3.sh"]