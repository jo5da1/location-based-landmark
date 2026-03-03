#!/bin/bash
set -e

# Config
OSM_URL="https://download.geofabrik.de/europe/sweden-latest.osm.pbf"
OSM_PBF="sweden-latest.osm.pbf"
EXTRACT_PBF="gothenburg.osm.pbf"
EXTRACT_OSM="map.osm"

POSTGRES_HOST="${POSTGRES_HOST}"
POSTGRES_DB="${POSTGRES_DB}"
POSTGRES_USER="${POSTGRES_USER}"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD}"

export PGPASSWORD="$POSTGRES_PASSWORD"

# Step 1: Download OSM
echo " "
echo "**** Step 1: ****"
echo "wget: $(which wget)"
echo "Downloading Sweden OSM PBF..."
wget -q --show-progress $OSM_URL -O $OSM_PBF

# Step 2: Extract Gothenburg bbox
echo " "
echo "**** Step 2: ****"
echo "osmium: $(which osmium)"
echo "Extracting Gothenburg..."
osmium extract --bbox=11.80,57.60,12.10,57.80 $OSM_PBF -o $EXTRACT_PBF

# Step 3: Convert to OSM XML
echo " "
echo "**** Step 3: ****"
echo "Converting to OSM XML..."
osmium cat $EXTRACT_PBF -o $EXTRACT_OSM

# Step 4: Wait for PostgreSQL
echo " "
echo "**** Step 4: ****"
echo "Waiting for PostgreSQL..."
until pg_isready -h $POSTGRES_HOST -p 5432 -U $POSTGRES_USER; do sleep 2; done
echo "PostgreSQL ready!"

# Step 5: osm2pgrouting
echo " "
echo "**** Step 5: ****"
echo "osm2pgrouting: $(which osm2pgrouting)"
echo "Running osm2pgrouting import..."
osm2pgrouting -f $EXTRACT_OSM -d $POSTGRES_DB -U $POSTGRES_USER -W "$POSTGRES_PASSWORD" -h $POSTGRES_HOST --clean

# Step 6: osm2pgsql
echo " "
echo "**** Step 6: ****"
echo "osm2pgsql: $(which osm2pgsql)"
echo "Running osm2pgsql import..."
osm2pgsql -H $POSTGRES_HOST -d $POSTGRES_DB -U $POSTGRES_USER --create --slim --hstore $EXTRACT_OSM

echo "All imports finished successfully!"