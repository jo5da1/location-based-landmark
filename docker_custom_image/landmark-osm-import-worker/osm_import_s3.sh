#!/bin/bash
set -e

# Config
POSTGRES_HOST="${POSTGRES_HOST}"
POSTGRES_DB="${POSTGRES_DB}"
POSTGRES_USER="${POSTGRES_USER}"
POSTGRES_PASSWORD="${POSTGRES_PASSWORD}"

export PGPASSWORD="$POSTGRES_PASSWORD"

AWS_ACCESS_KEY_ID="${AWS_ACCESS_KEY_ID}"
AWS_SECRET_ACCESS_KEY="${AWS_SECRET_ACCESS_KEY}"
AWS_DEFAULT_REGION="${AWS_DEFAULT_REGION}"
S3_ENDPOINT="${S3_ENDPOINT}"
S3_BUCKET="${S3_BUCKET}"
OSM_FILE="${OSM_FILE}"
OSM_FILE_1="${OSM_FILE_1}"

UPLOAD_LOCAL_OSM=fetched_from_s3/map.osm


# Step Echo:
echo " "
echo " **** Step Echo: ****"
echo "POSTGRES_HOST     : " $POSTGRES_HOST
echo "POSTGRES_DB       : " $POSTGRES_DB
echo "POSTGRES_USER     : " $POSTGRES_USER
echo "POSTGRES_PASSWORD : " $POSTGRES_PASSWORD
echo "PGPASSWORD        : " $PGPASSWORD
echo "AWS_ACCESS_KEY_ID     : " $AWS_ACCESS_KEY_ID
echo "AWS_SECRET_ACCESS_KEY : " $AWS_SECRET_ACCESS_KEY
echo "AWS_DEFAULT_REGION    : " $AWS_DEFAULT_REGION
echo "S3_ENDPOINT           : " $S3_ENDPOINT
echo "S3_BUCKET             : " $S3_BUCKET
echo "OSM_FILE              : " $OSM_FILE
echo "OSM_FILE_1            : " $OSM_FILE_1


echo "**** Step 0: Download from S3 ****"
aws --endpoint-url=$S3_ENDPOINT \
   s3 cp s3://$S3_BUCKET/$OSM_FILE $UPLOAD_LOCAL_OSM

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
          $UPLOAD_LOCAL_OSM

# Step 3: osm2pgrouting
echo " "
echo "**** Step 3: ****"
echo "osm2pgrouting: $(which osm2pgrouting)"
echo "Running osm2pgrouting import..."
osm2pgrouting -f $UPLOAD_LOCAL_OSM \
              -d $POSTGRES_DB \
              -U $POSTGRES_USER \
              -W "$POSTGRES_PASSWORD" \
              -h $POSTGRES_HOST \
              --clean

echo "Import finished successfully!"

