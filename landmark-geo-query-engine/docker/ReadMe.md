### Docker image <br/>
- PostgreSQL (PostgreSQL 16 + PostGIS 3.5 + pgRouting 4.0) <br/>
`pgrouting/pgrouting:16-3.5-4.0`
- RabbitMQ <br/>
`rabbitmq:4.2.3-management-alpine`

### Download OSM Map and Extract
From Geofabrik (Europe → Sweden), download Sweden and clip, or use a bounding box.<br/>

Example:<br/>
``https://download.geofabrik.de/europe/sweden.html``
```bash
wget https://download.geofabrik.de/europe/sweden-latest.osm.pbf
```

### Osmium-Tool (Command-line tool for OpenStreetMap)
Read More: ``https://osmcode.org/osmium-tool/`` <br/>

Install
```bash
brew install osmium-tool
```
Extract desired bounding box of a city map.<br/>
```bash
osmium extract \
  --bbox=11.80,57.60,12.10,57.80 \
  sweden-latest.osm.pbf \
  -o gothenburg.osm.pbf
```

```bash
osmium cat gothenburg_map.osm.pbf -o map.osm
```

Docker commands
````bash
docker compose down -v
docker compose build --no-cache
docker compose up
docker logs -f importer
````
