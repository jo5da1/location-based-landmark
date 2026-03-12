INSERT INTO planet_osm_point (osm_id, amenity, brand, name, way) VALUES
(2931104210, 'cafe', 'Local Vendor', 'Café Vi',
 ST_Transform(ST_SetSRID(ST_Point(11.952140999999997,57.72202500063145),4326),3857)),

(2165234233, 'cafe', NULL, 'Cofee Corner',
 ST_Transform(ST_SetSRID(ST_Point(11.952931599999998,57.72264960063176),4326),3857)),

(3647167404, 'cafe', 'Local Vendor', 'Kafé Alkemisten',
 ST_Transform(ST_SetSRID(ST_Point(11.944914,57.72231100063156),4326),3857)),

(2793201063, 'cafe', NULL, 'Tooty Coffee',
 ST_Transform(ST_SetSRID(ST_Point(11.945614499999998,57.7229829006319),4326),3857)),

(1184106552, 'cafe', NULL, 'Café Rosa-Mi',
 ST_Transform(ST_SetSRID(ST_Point(11.9450394,57.72758860063432),4326),3857)),

(2793200982, 'cafe', NULL, 'Café Kvillebäcken',
 ST_Transform(ST_SetSRID(ST_Point(11.947652499999998,57.723170500632044),4326),3857));