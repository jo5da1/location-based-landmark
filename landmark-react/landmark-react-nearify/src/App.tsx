import 'leaflet/dist/leaflet.css';
import 'leaflet-draw/dist/leaflet.draw.css';

import React, { useState, useEffect } from 'react';
import MapComponent from './components/MapComponent';
import OptionsPanel from './components/OptionsPanel';
import { BBox, MapData } from './types';
import { fetchLandmarks } from './api/landmarkApi';
import { MAP_CENTER} from './constants/map';
import { MapPoint, Landmark, Category, Coordinates } from './types/landmarkTypes';

import axios from 'axios';

function App() {
  const [bbox, setBbox] = useState<BBox | null>(null);
  const [clickPos, setClickPos] = useState<MapPoint | null>(null);
  const [radius, setRadius] = useState<number | 0>(0);
  const [options, setOptions] = useState<string[]>([]);
  const [mapData, setMapData] = useState<MapData[]>([]);
  const [landmarks, setLandmarks] = useState<Landmark[]>([]);

  useEffect(() => {
    if (bbox && options.length > 0 && clickPos) {

        fetchLandmarks(
          bbox,
          options,
          clickPos,
          MAP_CENTER.zoom
        )
        .then(data => {
            setLandmarks(data);
            const converted: MapData[] = data.map(
                l => ({
                    id: l.id,
                    lat: l.coordinates.latitude,
                    lng: l.coordinates.longitude,
                    info: l.name
                }));
            setMapData(converted);
        })
        .catch(err => console.error(err));
        console.log("Position: ", clickPos);


    }
  }, [bbox, options, clickPos]);

  return (
    <div style={{ display: 'flex' }}>
      <OptionsPanel options={options} setOptions={setOptions} />
      <MapComponent 
        bbox={bbox} 
        setBbox={setBbox}
        clickPos={clickPos}
        setClickPos={setClickPos}
        radius={radius}
        setRadius={setRadius}
        mapData={mapData} 
      />
    </div>
  );
}

export default App;
