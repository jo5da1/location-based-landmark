## Step 1: Set Up the Project

### 1. Install Node.js

##### NodeJS Download
https://nodejs.org/en/download

#### Download and install nvm:
```
curl -o- https://raw.githubusercontent.com/nvm-sh/nvm/v0.40.4/install.sh | bash
```
#### in lieu of restarting the shell
```
\. "$HOME/.nvm/nvm.sh"
```
#### Download and install Node.js:
```
nvm install 24
```
#### Verify the Node.js version:
```
node -v 
Should print "v24.14.0".
```
#### Verify npm version:
```
npm -v 
Should print "11.9.0".
```

### 2. Create a React + TypeScript project:
```
npx create-react-app landmark-react-nearify --template typescript
cd 
```
---
## Step 2: Install Dependencies
- Leaflet + React-Leaflet for the map
- react-leaflet-draw for drawing bounding boxes
- axios for REST API calls

```
npm install react-leaflet leaflet react-leaflet-draw axios
```
types for TypeScript:
```
npm install --save-dev @types/leaflet
```
UI library for checkboxes: Material-UI
```
npm install @mui/material @emotion/react @emotion/styled
```
---
## Step 3: Configure Leaflet CSS
Leaflet needs its CSS imported. In src/index.tsx or src/App.tsx:
```
import 'leaflet/dist/leaflet.css';
import 'leaflet-draw/dist/leaflet.draw.css';
```
---
## Step 4: Create Folder Structure
```
src/
  components/
    MapComponent.tsx
    OptionsPanel.tsx
  types/
    index.ts
  api/
    api.ts
  App.tsx
```
- components/ → React components
- types/ → TypeScript interfaces/types
- api/ → API call logic
---
## Step 5: Define Types
Create src/types/index.ts:
```
export type BBox = [number, number, number, number]; // [minLng, minLat, maxLng, maxLat]

export type MapData = {
  id: string;
  lat: number;
  lng: number;
  info: string;
};
```
---
## Step 6: Setup Map Component
Create `MapComponent.tsx`:
- Show the map
- Draw bounding boxes
- Render API data
```
import React from 'react';
import { MapContainer, TileLayer, Rectangle, Marker, Popup } from 'react-leaflet';
import { BBox, MapData } from '../types';

type Props = {
  bbox: BBox | null;
  setBbox: (bbox: BBox) => void;
  mapData: MapData[];
};

const MapComponent: React.FC<Props> = ({ bbox, setBbox, mapData }) => {
  return (
    <MapContainer center={[0, 0]} zoom={2} style={{ height: '100vh', flex: 1 }}>
      <TileLayer url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png" />
      {bbox && <Rectangle bounds={[[bbox[1], bbox[0]], [bbox[3], bbox[2]]]} />}
      {mapData.map(d => (
        <Marker key={d.id} position={[d.lat, d.lng]}>
          <Popup>{d.info}</Popup>
        </Marker>
      ))}
    </MapContainer>
  );
};

export default MapComponent;
```
---
## Step 7: Setup Options Panel
Create `OptionsPanel.tsx` for checkboxes:
```
import React from 'react';

type Props = {
  options: string[];
  setOptions: (opts: string[]) => void;
};

const AVAILABLE_OPTIONS = ['Option 1', 'Option 2', 'Option 3'];

const OptionsPanel: React.FC<Props> = ({ options, setOptions }) => {
  const toggleOption = (opt: string) => {
    if (options.includes(opt)) {
      setOptions(options.filter(o => o !== opt));
    } else {
      setOptions([...options, opt]);
    }
  };

  return (
    <div style={{ padding: '1rem', width: '200px' }}>
      {AVAILABLE_OPTIONS.map(opt => (
        <label key={opt}>
          <input
            type="checkbox"
            checked={options.includes(opt)}
            onChange={() => toggleOption(opt)}
          />
          {opt}
        </label>
      ))}
    </div>
  );
};

export default OptionsPanel;
```
---
## Step 8: Setup App Component
App.tsx
```
import React, { useState, useEffect } from 'react';
import MapComponent from './components/MapComponent';
import OptionsPanel from './components/OptionsPanel';
import { BBox, MapData } from './types';
import axios from 'axios';

function App() {
  const [bbox, setBbox] = useState<BBox | null>(null);
  const [options, setOptions] = useState<string[]>([]);
  const [mapData, setMapData] = useState<MapData[]>([]);

  useEffect(() => {
    if (bbox && options.length > 0) {
      axios
        .post<MapData[]>('/api/data', { bbox, options })
        .then(res => setMapData(res.data))
        .catch(err => console.error(err));
    }
  }, [bbox, options]);

  return (
    <div style={{ display: 'flex' }}>
      <OptionsPanel options={options} setOptions={setOptions} />
      <MapComponent bbox={bbox} setBbox={setBbox} mapData={mapData} />
    </div>
  );
}

export default App;
```
---
## Step 9: Run the App
```
npm start
```
- Opens http://localhost:3000
- You’ll see the map + checkboxes
- Bounding box drawing and API integration are next (can add react-leaflet-draw for interactive drawing)

---
## Step 10: Next Enhancements

1. Bounding box drawing with react-leaflet-draw
2. Debounce API requests
3. Dynamic marker rendering
4. UI improvements (Material UI / Chakra UI)
5. Error/loading handling

---
### FontAwesome icons instead of images
A nicer approach is Leaflet + FontAwesome markers, no need to manage PNG files.
Using FontAwesome icons with Leaflet is a great idea. 
It avoids managing image files and allows easily change colors and icons for restaurants, parks, museums, etc.

#### Install dependencies
```
npm install leaflet-awesome-markers
npm install @fortawesome/fontawesome-free
```
