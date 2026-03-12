CREATE TABLE planet_osm_point (
    osm_id BIGINT PRIMARY KEY,
    amenity TEXT,
    brand TEXT,
    name TEXT,
    way geometry(Point,3857)
);

