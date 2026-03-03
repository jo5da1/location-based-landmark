# Use official pgrouting image as base
FROM pgrouting/pgrouting:16-3.5-4.0

# Install osm2pgsql
RUN apt update && apt install -y osm2pgsql

# Install osm2pgrouting
RUN apt-get update && apt-get install -y osm2pgrouting

# Install wget
RUN apt-get update && apt-get install -y wget

# Install osmium
RUN apt-get update && apt-get install -y osmium-tool

 # Create data directory
RUN mkdir -p /data

# Copy the import script into the image
COPY osm_import.sh /usr/local/bin/osm_import.sh
RUN chmod +x /usr/local/bin/osm_import.sh

# Set working directory
WORKDIR /data

# Default command runs the import script
CMD ["/usr/local/bin/osm_import.sh"]