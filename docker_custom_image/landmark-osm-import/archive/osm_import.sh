#!/bin/bash
set -e

# Config
EXT_OSM_PBF=".osm.pbf"
OSM_URL="https://download.geofabrik.de/europe/sweden-latest.osm.pbf"
OSM_PBF="sweden-latest.osm.pbf"
#EXTRACT_BBOX=11.80,57.60,12.10,57.80
EXTRACT_BBOX=11.80,57.60,11.85,57.65
EXTRACT_PBF="gothenburg.osm.pbf"
EXTRACT_OSM="gothenburg.osm"
RELEASE_OSM="release/map.osm"

POSTGRES_HOST="${POSTGRES_HOST}"
POSTGRES_DB="${POSTGRES_DB}"
POSTGRES_USER="${POSTGRES_USER}"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD}"

export PGPASSWORD="$POSTGRES_PASSWORD"


# Step Echo:
echo " "
echo " **** Step Echo: ****"
echo "POSTGRES_HOST     : " $POSTGRES_HOST
echo "POSTGRES_DB       : " $POSTGRES_DB
echo "POSTGRES_USER     : " $POSTGRES_USER
echo "POSTGRES_PASSWORD : " $POSTGRES_PASSWORD
echo "PGPASSWORD        : " $PGPASSWORD
echo "OSM_URL      : " $OSM_URL
echo "EXT_OSM_PBF  : " $EXT_OSM_PBF
echo "OSM_PBF      : " $OSM_PBF
echo "EXTRACT_PBF  : " $EXTRACT_PBF
echo "EXTRACT_OSM  : " $EXTRACT_OSM
echo "EXTRACT_BBOX : " $EXTRACT_BBOX
echo "RELEASE_OSM  : " $RELEASE_OSM


# Step 1: Download OSM
echo " "
echo "**** Step 1: ****"
echo "wget: $(which wget)"
echo "Downloading Sweden OSM PBF..."
#wget -q --show-progress $OSM_URL -O $OSM_PBF
wget -q $OSM_URL -O $OSM_PBF

# Step 2: Extract Gothenburg bbox
echo " "
echo "**** Step 2: ****"
echo "osmium: $(which osmium)"
echo "Extracting Gothenburg..."
osmium extract --bbox=$EXTRACT_BBOX $OSM_PBF -o $EXTRACT_PBF

# Step 3: Convert to OSM XML
echo " "
echo "**** Step 3: ****"
echo "Converting to OSM XML..."
osmium cat $EXTRACT_PBF -o $EXTRACT_OSM

# Step 3.1: Move pbf file to pbf dir
echo " "
echo "**** Step 3.1: ****"
echo "Moving pbf and osm files to dir"
mv *.pbf pbf/
mv *.osm osm/

echo "Creating Release Directory"
if [ -d release ]; then
  mkdir -p release_archive
  mv release "release_archive/release_$(date +%Y_%m_%d_%H%M%S)"
fi
mkdir -p release

# Step 3.2: Merge
echo " "
echo "**** Step 3.2: Merge ****"
echo "Merging osm files"
osmium merge osm/*.osm -o $RELEASE_OSM

# Step 4: Wait for PostgreSQL
echo " "
echo "**** Step 4: ****"
echo "Waiting for PostgreSQL..."
until pg_isready -h $POSTGRES_HOST -p 5432 -U $POSTGRES_USER; do sleep 2; done
echo "PostgreSQL ready!"

# Step 5: osm2pgsql
echo " "
echo "**** Step 6: ****"
echo "osm2pgsql: $(which osm2pgsql)"
echo "Running osm2pgsql import..."
osm2pgsql -H $POSTGRES_HOST -d $POSTGRES_DB -U $POSTGRES_USER --create --slim --hstore $RELEASE_OSM

# Step 6: osm2pgrouting
echo " "
echo "**** Step 5: ****"
echo "osm2pgrouting: $(which osm2pgrouting)"
echo "Running osm2pgrouting import..."
osm2pgrouting -f $RELEASE_OSM -d $POSTGRES_DB -U $POSTGRES_USER -W "$POSTGRES_PASSWORD" -h $POSTGRES_HOST --clean

echo "All imports finished successfully!"

