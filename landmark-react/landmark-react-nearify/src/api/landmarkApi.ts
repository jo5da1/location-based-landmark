import axios from "axios";

import { MAP_CENTER } from '../constants/map';
import { BBox, MapData } from '../types';
import {
        MapPoint, Landmark, Category, Coordinates, LandmarkOption,
        LandmarkOptionsResponse,
        LandmarksRequest, LandmarksResponse
    } from '../types/landmarkTypes';




export const fetchLandmarkOptionsMock = async (): Promise<LandmarkOption[]> => {
  console.log('MOCK!! Fetch Landmark Options');

  const mockOptions: LandmarkOption[] = [
    { id: 1, name: 'Museum' },
    { id: 2, name: 'Park' },
    { id: 3, name: 'Restaurant' },
    { id: 4, name: 'Historic Site' },
    { id: 5, name: 'Monument' },
  ];

  return new Promise(resolve => setTimeout(() => resolve(mockOptions), 500));
};

export const fetchLandmarkOptions = async (): Promise<LandmarkOption[]> => {

  try {
    console.log('Fetching Landmark Options from API...');

    const response = await axios.get<LandmarkOptionsResponse>(
        'http://localhost:8086/api/landmark/options',
        { headers: { 'Content-Type': 'application/json'} }
    );

    console.log('API Response: ', response.data);
    console.log('API Response: Options: ', response.data.options);

    return response.data.options;

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
      category: { name: 'Park' },
      coordinates: { latitude: 57.695, longitude: 11.993 },
      distance: 2.2
    },
    {
      id: "1234",
      name: 'Universeum',
      category: { name: 'Museum' },
      coordinates: { latitude: 57.6956, longitude: 11.987 },
      distance: 1.8
    },
  ];

  return new Promise(resolve => setTimeout(() => resolve(mockLandmarks), 500));
};

export const fetchLandmarks = async (
  bbox: BBox,
  options: string[],
  mapPoint: MapPoint,
  zoom: number
  ): Promise<Landmark[]> => {

  console.log('Fetch Landmarks:');
  console.log('BBox: ', bbox, ', Options:', options, ', Point: ', mapPoint);

  const request: LandmarksRequest = {
    requestId: crypto.randomUUID(),
    coordinates: {
      latitude: mapPoint.latitude,
      longitude: mapPoint.longitude
    },
    radius: 500,
    categories: options,
    page: 0,
    pageSize: 50
  };
  console.log("LandmarksRequest: ", request);

  try {
    console.log('Fetching Landmarks from API...');

    const response = await axios.post<LandmarksResponse>(
          'http://localhost:8086/api/landmark/nearby',
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

