FROM debian:bookworm-slim

# Install wget
# Install osmium
RUN apt-get update && apt-get install -y \
    --no-install-recommends \
    wget \
    osmium-tool \
    ca-certificates \
    && rm -rf /var/lib/apt/lists/*

# Create data directory
RUN mkdir -p /data /data/pbf /data/osm

# Copy the import script into the image
COPY osm_fetch_local.sh /usr/local/bin/osm_fetch_local.sh
RUN chmod +x /usr/local/bin/osm_fetch_local.sh

# Set working directory
WORKDIR /data

# Default command runs the import script
CMD ["/usr/local/bin/osm_fetch_local.sh"]