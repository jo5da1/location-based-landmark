import React, { useRef } from 'react';
import { MapContainer, TileLayer, Circle, Rectangle, Marker, Popup, useMapEvents } from 'react-leaflet';
import { BBox, MapData } from '../types';
import { MapPoint } from '../types/landmarkTypes';

import 'leaflet-draw';
import { MAP_CENTER } from '../constants/map';
import { getIcon } from '../types/leaflet.awesome-marker.d';



type Props = {
  bbox: BBox | null;
  setBbox: (bbox: BBox) => void;
  clickPos: MapPoint | null;
  setClickPos: (pos: MapPoint) => void;
  radius: number;
  setRadius: (r: number) => void;
  mapData: MapData[];
};


const DrawRectangle: React.FC<{ 
  setBbox: (bbox: BBox) => void;
  setClickPos: (pos: MapPoint) => void;
}> = ({ setBbox, setClickPos }) => {

  const map = useMapEvents({

    click(e) {

      //const zoom = e.target.getZoom() > 15 ? 15 : e.target.getZoom();
      const zoom = Math.min(e.target.getZoom(), 15);
      console.log("Zoom : ", e.target.getZoom(), zoom);

      // size decreases when zoom increases
      const size = 1 / Math.pow(2, zoom - 5);

      // store clicked position
      setClickPos( { latitude: e.latlng.lat, longitude: e.latlng.lng });

      // For simplicity, create a small bbox around clicked point
      // create square bbox centered on click
      setBbox([
        e.latlng.lng - size,
        e.latlng.lat - size, 
        e.latlng.lng + size, 
        e.latlng.lat + size
      ]);
    },
  });
  return null;
};

const DrawCircle: React.FC<{
  setClickPos: (pos: MapPoint) => void;
  setRadius: (r: number) => void;
}> = ({ setClickPos, setRadius }) => {

  const map = useMapEvents({

    click(e) {

      const zoom = Math.min(e.target.getZoom(), 15);
      console.log("Zoom : ", e.target.getZoom(), zoom);

      const baseRadius = 50000; // meters
      const radius = baseRadius / Math.pow(2, zoom - 5);

      // store clicked position
      setClickPos( { latitude: e.latlng.lat, longitude: e.latlng.lng });

       setRadius(radius);
    },
  });
  return null;
};

const MapComponent: React.FC<Props> = ({
    bbox, setBbox,
    clickPos, setClickPos,
    radius, setRadius,
    mapData }) => {

  return (
    <MapContainer
        center={[MAP_CENTER.lat, MAP_CENTER.lng]}
        zoom={MAP_CENTER.zoom}
        style={{ height: '100vh', flex: 1 }}>

      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />

      <DrawRectangle setBbox={setBbox} setClickPos={setClickPos} />
      {bbox &&
        <Rectangle
          bounds={[[bbox[1], bbox[0]], [bbox[3], bbox[2]]]}
          pathOptions={{ color: 'blue' }}
        />
      }

      <DrawCircle setClickPos={setClickPos} setRadius={setRadius} />
      {clickPos && (
        <Circle
          center={[clickPos.latitude, clickPos.longitude]}
          radius={radius}
          pathOptions={{ color: 'blue' }}
        />
      )}

     {/*  {mapData.map(d => (
        <Marker key={d.id} position={[d.lat, d.lng]}>
          <Popup>Info: {d.info}</Popup>
        </Marker>
      ))} */}

      {mapData.map(d => (
        <Marker
          key={d.id}
          position={[d.lat, d.lng]}
          icon={getIcon(d.category, d.subCategory)}>
          <Popup>{d.info}: {d.category}: {d.subCategory}</Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};

export default MapComponent;