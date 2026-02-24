# Use official pgrouting image as base
FROM pgrouting/pgrouting:16-3.5-4.0

# Install osm2pgsql
RUN apt update && apt install -y osm2pgsql

# Install osm2pgrouting
RUN apt-get update && apt-get install -y osm2pgrouting

WORKDIR /data