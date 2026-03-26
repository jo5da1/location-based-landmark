# Use official pgrouting image as base
FROM pgrouting/pgrouting:16-3.5-4.0
#FROM debian:bookworm-slim


# Install osm2pgsql
# Install osm2pgrouting
# Install wget
# Install osmium
RUN apt-get update && apt-get install -y \
    osm2pgsql \
    osm2pgrouting \
    wget \
    osmium-tool \
    # ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Create data directory
RUN mkdir -p /data
RUN mkdir -p /data/pbf
RUN mkdir -p /data/osm


# Copy the import script into the image
COPY osm_import.sh /usr/local/bin/osm_import.sh
RUN chmod +x /usr/local/bin/osm_import.sh

# Set working directory
WORKDIR /data

# Default command runs the import script
CMD ["/usr/local/bin/osm_import.sh"]