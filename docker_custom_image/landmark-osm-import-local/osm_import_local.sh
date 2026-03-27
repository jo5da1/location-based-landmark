#!/bin/bash
set -e

# Config
RELEASE_OSM="${RELEASE_OSM}"

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
echo "RELEASE_OSM       : " $RELEASE_OSM

# Step 1: Wait for PostgreSQL
echo " "
echo "**** Step 1: ****"
echo "Waiting for PostgreSQL..."
until pg_isready -h $POSTGRES_HOST -p 5432 -U $POSTGRES_USER; do sleep 2; done
echo "PostgreSQL ready!"

# Step 2: osm2pgsql
echo " "
echo "**** Step 2: ****"
echo "osm2pgsql: $(which osm2pgsql)"
echo "Running osm2pgsql import..."
osm2pgsql -H $POSTGRES_HOST \
          -d $POSTGRES_DB \
          -U $POSTGRES_USER \
          --create \
          --slim \
          --hstore \
          $RELEASE_OSM

# Step 3: osm2pgrouting
echo " "
echo "**** Step 3: ****"
echo "osm2pgrouting: $(which osm2pgrouting)"
echo "Running osm2pgrouting import..."
osm2pgrouting -f $RELEASE_OSM \
              -d $POSTGRES_DB \
              -U $POSTGRES_USER \
              -W "$POSTGRES_PASSWORD" \
              -h $POSTGRES_HOST \
              --clean

echo "Import finished successfully!"

