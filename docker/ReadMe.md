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
#### Profiles (Best Way to Enable/Disable Services)
Docker Compose supports profiles, which act like feature toggles.
```
docker compose --profile landmark up
```
#### Scale to Zero (Soft Disable)
scale a service to 0:
```
#docker compose --profile landmark up --scale landmark-geo-query-engine=0
```

# Monitoring

---
### Step 1 — Login to Grafana
1. Open → http://localhost:3000
2. Default login:
```
username: admin
password: admin
```
3. Set a new password if prompted.
---
### Step 2 — Add Prometheus as Data Source
1. Go to Connections → Data sources
2. Click Add data source
3. Choose Prometheus
4. Set URL: <br/>
If Grafana runs in Docker:
````
http://prometheus:9090
````
NOT localhost (because inside Docker, localhost = container itself).
5. Click Save & Test<br/>
You should see: ✅ Data source is working

### Import Ready-Made Dashboards
Grafana has prebuilt dashboards.
- For Spring Boot (Micrometer)
  - Dashboard ID: 4701
- For RabbitMQ
  - Dashboard ID: 10991
- For PostgreSQL 
  - Dashboard ID: 9628

### To import:
1. Dashboards → New → Import
2. Enter ID
3. Select Prometheus
4. Import


---
Links
- App Actuator:
  - `http://localhost:8087/actuator/prometheus`
- Prometheus:
  - `http://localhost:9090/targets`
- Grafana:
  - `http://localhost:3000`
- Rabbit MQ metrics
  - `http://localhost:15692/metrics`
- Postgres metrics
  - `http://localhost:9187/metrics`

---

#### RabbitMQ dashboard 10991 expects metrics like:
```
rabbitmq_queue_messages
rabbitmq_queue_messages_ready
rabbitmq_connections
```

### Make sure the plugin enabled is:
Go to container and verify
```
docker exec -it rabbitmq rabbitmq-plugins list
```
Look for:
```
[E*] rabbitmq_prometheus
```

---
### What postgres-exporter Does
`postgres-exporter` (official image from Prometheus Community) is a metrics bridge between:
- PostgreSQL database
- Prometheus monitoring system
`https://hub.docker.com/r/prometheuscommunity/postgres-exporter`

PostgreSQL does NOT expose Prometheus-format metrics natively.

So this container:
1. Connects to your PostgreSQL database
2. Runs internal SQL queries
3.Converts results into Prometheus metrics format
3. Exposes them at:
```
http://postgres-exporter:9187/metrics
```
Prometheus then scrapes that endpoint.<br/>

With exporter:
`Prometheus → postgres-exporter → PostgreSQL`
It acts as a translator.

---
In GeoQueryEngine (Spring Boot + RabbitMQ + Postgres + pgrouting)<br/>
Database performance is critical because:
- Spatial queries are expensive
- Routing queries are heavy
- Connection pool exhaustion can happen

Without postgres-exporter:<br/>
Zero visibility into DB bottlenecks.<br/>

Create a read-only monitoring user<br/>
Not use your main application DB user<br/>
```
CREATE USER monitoring WITH PASSWORD 'monitorpass';
GRANT pg_monitor TO monitoring;
```
```
postgresql://monitoring:monitorpass@postgres:5432/landmark_db
```
