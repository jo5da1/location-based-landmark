import axios from "axios";

import { MAP_CENTER } from '../constants/map';
import { BBox, MapData } from '../types';
import {
        MapPoint, Landmark, Coordinates, LandmarkCategory,
        LandmarkCategoryResponse,
        LandmarksRequest, LandmarksResponse
    } from '../types/landmarkTypes';


export const fetchLandmarkOptionsMock = async (): Promise<LandmarkCategory[]> => {
  console.log('MOCK!! Fetch Landmark Options');

  const mockOptions: LandmarkCategory[] = [
    { id: 1, category: 'Museum', subCategories: ['OK','new']},
    { id: 2, category: 'Park', subCategories: ['OK','new'] },
    { id: 3, category: 'Restaurant', subCategories: ['OK','new'] },
    { id: 4, category: 'Historic Site', subCategories: ['OK','new'] },
    { id: 5, category: 'Monument', subCategories: ['OK','new'] },
  ];

  return new Promise(resolve => setTimeout(() => resolve(mockOptions), 500));
};

export const fetchLandmarkOptions = async (): Promise<LandmarkCategory[]> => {

  try {
    console.log('Fetching Category from API...');

    const apiBaseUrl = process.env.REACT_APP_LANDMARK_NEARBY_API_URL;
    const apiEndpoint = process.env.REACT_APP_ENDPOINT_CATEGORY;
    if (!apiBaseUrl || !apiEndpoint) {
      throw new Error("Missing API URL or endpoint environment variables");
    }
    const apiUrl = `${apiBaseUrl}${apiEndpoint}`;
    console.log("API URL:", apiUrl);

    const response = await axios.get<LandmarkCategoryResponse>(
      apiUrl,
      { headers: { 'Content-Type': 'application/json'} }
    );

    console.log('API Response: ', response.data);
    console.log('API Response: Options: ', response.data.categories);

    return response.data.categories;

  } catch (error) {
    console.error('Error fetching landmark options:', error);
    // fallback to empty array or you can use mockOptions if you want
    return fetchLandmarkOptionsMock();
  }
};

export const fetchLandmarksMock = async (
  bbox: BBox,
  options: string[],
  mapPoint: MapPoint,
  zoom: number
  ): Promise<Landmark[]> => {

  console.log('MOCK!! Fetch Landmarks');

  const mockLandmarks: Landmark[] = [
    {
      id: "123",
      name: 'Liseberg',
      category: 'Park' ,
      subCategory: 'Park' ,
      coordinates: { latitude: 57.695, longitude: 11.993 },
      distance: 2.2
    },
    {
      id: "1234",
      name: 'Universeum',
      category:  'Museum' ,
      subCategory:  'Museum' ,
      coordinates: { latitude: 57.6956, longitude: 11.987 },
      distance: 1.8
    },
  ];

  return new Promise(resolve => setTimeout(() => resolve(mockLandmarks), 500));
};

export const fetchLandmarks = async (
  bbox: BBox,
  options: string[],
  subCategories: string[],
  mapPoint: MapPoint,
  zoom: number
  ): Promise<Landmark[]> => {

  console.log('Fetch Landmarks:');
  console.log('BBox: ', bbox, 
    ', Categories:', options,
    ', Sub Categories:', subCategories,
    ', Point: ', mapPoint);

  const request: LandmarksRequest = {
    requestId: crypto.randomUUID(),
    coordinates: {
      latitude: mapPoint.latitude,
      longitude: mapPoint.longitude
    },
    radius: 500,
    categories: [],
    subCategories: subCategories,
    page: 0,
    pageSize: 50
  };
  console.log("LandmarksRequest: ", request);

  try {
    console.log('Fetching Landmarks from API...');

    const apiBaseUrl = process.env.REACT_APP_LANDMARK_NEARBY_API_URL;
    const apiEndpoint = process.env.REACT_APP_ENDPOINT_NEARBY;
    if (!apiBaseUrl || !apiEndpoint) {
      throw new Error("Missing API URL or endpoint environment variables");
    }
    const apiUrl = `${apiBaseUrl}${apiEndpoint}`;
    console.log("API URL:", apiUrl);

    const response = await axios.post<LandmarksResponse>(
      apiUrl,
      request,
      { headers: { 'Content-Type': 'application/json'} }
    );

    console.log('API Response: ', response.data);
    return response.data.landmarks;
  } catch (error) {
    console.error('Error fetching landmarks:', error);
    // fallback to empty array or you can use mock if you want
    return fetchLandmarksMock(bbox, options, mapPoint, zoom);
  }
};

