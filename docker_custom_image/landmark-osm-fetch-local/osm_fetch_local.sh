#!/bin/bash
set -e

# Config
OSM_URL="${OSM_URL}"
OSM_PBF="${OSM_PBF}"
EXTRACT_BBOX="${EXTRACT_BBOX}"
EXTRACT_PBF="${EXTRACT_PBF}"
EXTRACT_OSM="${EXTRACT_OSM}"
RELEASE_OSM="${RELEASE_OSM}"
RELEASE_PBF="${RELEASE_PBF}"

# Step Echo:
echo " "
echo " **** Step Echo: ****"
echo "OSM_URL      : " $OSM_URL
echo "OSM_PBF      : " $OSM_PBF
echo "EXTRACT_BBOX : " $EXTRACT_BBOX
echo "EXTRACT_PBF  : " $EXTRACT_PBF
echo "EXTRACT_OSM  : " $EXTRACT_OSM
echo "RELEASE_OSM  : " $RELEASE_OSM
echo "RELEASE_PBF  : " $RELEASE_PBF


# Step 1: Download OSM
echo " "
echo "**** Step 1: ****"
echo "wget: $(which wget)"
echo "Downloading OSM PBF..."
#wget -q --show-progress $OSM_URL -O $OSM_PBF
wget -q $OSM_URL -O $OSM_PBF

# Step 2: Extracting bbox
echo " "
echo "**** Step 2: ****"
echo "osmium: $(which osmium)"
echo "Extracting bbox..."
osmium extract --bbox=$EXTRACT_BBOX $OSM_PBF -o $EXTRACT_PBF

# Step 3: Convert to OSM XML
echo " "
echo "**** Step 3: ****"
echo "Converting to OSM XML..."
osmium cat $EXTRACT_PBF -o $EXTRACT_OSM

# Step 4: Organize files. Moving pbf, osm file to respective dir..
echo " "
echo "**** Step 4: ****"
echo "Organize files. Moving pbf, osm file to respective dir.."
mkdir -p pbf_full pbf_extract osm

mv "$OSM_PBF" pbf_full/
mv "$EXTRACT_PBF" pbf_extract/
mv "$EXTRACT_OSM" osm/

# Step 5: Creating Release Directory..
echo " "
echo "**** Step 5: ****"
echo "Creating Release Directory.."
if [ -d release ]; then
  mkdir -p release_archive
  mv release "release_archive/release_$(date +%Y_%m_%d_%H%M%S)"
fi
mkdir -p release

# Step 6: Merging osm file to a final map.osm
echo " "
echo "**** Step 6: Merge ****"
echo "Merging osm file to a final map.osm.."
osmium merge osm/*.osm -o $RELEASE_OSM
osmium merge pbf_extract/*.pbf -o $RELEASE_PBF

echo " "
echo "Download finished successfully!"
