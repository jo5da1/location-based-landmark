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
    jq \
    && rm -rf /var/lib/apt/lists/*


# Copy the import script into the image
COPY osm_import_s3.sh  /usr/local/bin/osm_import_s3.sh
RUN chmod +x /usr/local/bin/osm_import_s3.sh

COPY osm_entry.sh  /usr/local/bin/osm_entry.sh
RUN chmod +x /usr/local/bin/osm_entry.sh

# Set working directory
WORKDIR /data

# Default command runs the import script
CMD ["/usr/local/bin/osm_entry.sh"]