export type MapPoint = {
  latitude: number;
  longitude: number;
};

export type Coordinates = {
  latitude: number;
  longitude: number;
};

export type LandmarkCategory = {
  id: number;
  category: string;
  subCategories: string[];
};


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
  category: string;
  subCategory: string;
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
  subCategories: string[];
  page?: number;
  pageSize?: number;
}

export type LandmarkCategoryResponse = {
  categories: LandmarkCategory[];
}