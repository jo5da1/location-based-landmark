# Use official pgrouting image as base
FROM pgrouting/pgrouting:16-3.5-4.0
#FROM debian:bookworm-slim

# Install osm2pgsql
# Install osm2pgrouting
RUN apt-get update && apt-get install -y \
    --no-install-recommends \
    osm2pgsql \
    osm2pgrouting \
    # ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Create data directory
#RUN mkdir -p /data /data/pbf /data/osm


# Copy the import script into the image
COPY osm_import_local.sh /usr/local/bin/osm_import_local.sh
RUN chmod +x /usr/local/bin/osm_import_local.sh

# Set working directory
WORKDIR /data

# Default command runs the import script
CMD ["/usr/local/bin/osm_import_local.sh"]