export type BBox = [number, number, number, number]; // [minLng, minLat, maxLng, maxLat]

export type MapData = {
  id: string;
  lat: number;
  lng: number;
  category: string;
  subCategory: string;
  info: string;
};