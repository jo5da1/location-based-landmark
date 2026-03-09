export type MapPoint = {
  latitude: number;
  longitude: number;
};

export type Coordinates = {
  latitude: number;
  longitude: number;
};

export type LandmarkOption = {
  id: number;
  name: string;
};

export type Category = {
  name: string;
};

export enum CategoryENUM {
  RESTAURANT = "RESTAURANT",
  CAFE = "CAFE",
  HOTEL = "HOTEL",
  HOSPITAL = "HOSPITAL",
  PARK = "PARK",
  MUSEUM = "MUSEUM",
  SCHOOL = "SCHOOL",
  SHOPPING_MALL = "SHOPPING_MALL",
}

export function validateCoordinates(coords: Coordinates) {
  if (coords.latitude < -90 || coords.latitude > 90) {
    throw new Error("Latitude must be between -90 and 90");
  }
  if (coords.longitude < -180 || coords.longitude > 180) {
    throw new Error("Longitude must be between -180 and 180");
  }
}

export type Landmark = {
  id: string;
  name: string;
  category: Category;
  coordinates: Coordinates;
  distance: number;
}

export type LandmarksResponse = {
  requestId: string;
  totalCount: number;
  landmarks: Landmark[];
}

export type LandmarksRequest = {
  requestId: string;
  coordinates: Coordinates;
  radius: number;
  categories: string[];
  page?: number;
  pageSize?: number;
}

export type LandmarkOptionsResponse = {
  options: LandmarkOption[];
}