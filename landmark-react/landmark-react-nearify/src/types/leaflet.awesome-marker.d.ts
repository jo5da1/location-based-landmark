import L from "leaflet";

import "leaflet.awesome-markers";

import "leaflet/dist/leaflet.css";
import "leaflet.awesome-markers/dist/leaflet.awesome-markers.css";
import "@fortawesome/fontawesome-free/css/all.min.css";

const AwesomeMarkers = (L as any).AwesomeMarkers;


// FOOD_AND_DRINK
export const foodAndDrinkIcon = AwesomeMarkers.icon({
  icon: "utensils",
  prefix: "fa",
  markerColor: "blue"
});

export const restaurantIcon = AwesomeMarkers.icon({
  icon: "utensils",
  prefix: "fa",
  markerColor: "red"
});

export const cafeIcon = AwesomeMarkers.icon({
  icon: "utensils",
  prefix: "fa",
  markerColor: "pink"
});

export const barIcon = AwesomeMarkers.icon({
  icon: "beer",
  prefix: "fa",
  markerColor: "orange"
});

export const fastFoodIcon = AwesomeMarkers.icon({
  icon: "burger",
  prefix: "fa",
  markerColor: "red"
});

export const iceCreamIcon = AwesomeMarkers.icon({
  icon: "ice-cream",
  prefix: "fa",
  markerColor: "lightblue"
});

export const foodCourtIcon = AwesomeMarkers.icon({
  icon: "utensils",
  prefix: "fa",
  markerColor: "purple"
});

export const pubIcon = AwesomeMarkers.icon({
  icon: "beer",
  prefix: "fa",
  markerColor: "darkred"
});

export const bbqIcon = AwesomeMarkers.icon({
  icon: "burger",
  prefix: "fa",
  markerColor: "black"
});

// ACCOMMODATION
export const accommodationIcon = AwesomeMarkers.icon({
  icon: "bed",
  prefix: "fa",
  markerColor: "blue"
});

export const hotelIcon = AwesomeMarkers.icon({
  icon: "hotel",
  prefix: "fa",
  markerColor: "red"
});

export const loungeIcon = AwesomeMarkers.icon({
  icon: "couch",
  prefix: "fa",
  markerColor: "purple"
});

export const spaIcon = AwesomeMarkers.icon({
  icon: "spa",
  prefix: "fa",
  markerColor: "pink"
});


// HEALTH_AND_EMERGENCY
export const healthAndEmergencyIcon = AwesomeMarkers.icon({
  icon: "briefcase-medical",
  prefix: "fa",
  markerColor: "red"
});

export const hospitalIcon = AwesomeMarkers.icon({
  icon: "hospital",
  prefix: "fa",
  markerColor: "darkred"
});

export const clinicIcon = AwesomeMarkers.icon({
  icon: "clinic-medical",
  prefix: "fa",
  markerColor: "red"
});

export const doctorIcon = AwesomeMarkers.icon({
  icon: "stethoscope",
  prefix: "fa",
  markerColor: "cadetblue"
});

export const dentistIcon = AwesomeMarkers.icon({
  icon: "tooth",
  prefix: "fa",
  markerColor: "blue"
});

export const pharmacyIcon = AwesomeMarkers.icon({
  icon: "clinic-medical",
  prefix: "fa",
  markerColor: "green"
});

export const educationIcon = AwesomeMarkers.icon({
  icon: "graduation-cap",
  prefix: "fa",
  markerColor: "blue"
});

export const artCultureEntertainmentIcon = AwesomeMarkers.icon({
  icon: "theater-masks",
  prefix: "fa",
  markerColor: "purple"
});

export const shoppingAndServicesIcon = AwesomeMarkers.icon({
  icon: "shopping-bag",
  prefix: "fa",
  markerColor: "green"
});

export const parkIcon = AwesomeMarkers.icon({
  icon: "tree",
  prefix: "fa",
  markerColor: "green"
});

export const museumIcon = AwesomeMarkers.icon({
  icon: "landmark",
  prefix: "fa",
  markerColor: "blue"
});

export const defaultIcon = AwesomeMarkers.icon({
  icon: "map-marker-alt",
  prefix: "fa",
  markerColor: "cadetblue"
});

export const getIcon = (category: string, subCategory: string) => {
  console.log("Category: ", category, "SubCategory: ", subCategory)

  if (category === "FOOD_AND_DRINK") {
    switch (subCategory?.toLowerCase()) {
      case "restaurant":
        return restaurantIcon;
      case "cafe":
        return cafeIcon;
      case "bar":
        return barIcon;
      case "fast_food":
        return fastFoodIcon;
      case "ice_cream":
        return iceCreamIcon;
      case "food_court":
        return foodCourtIcon;
      case "pub":
        return pubIcon;
      case "bbq":
        return bbqIcon;
      default:
        return foodAndDrinkIcon;
    }
  }

  if (category === "ACCOMMODATION") {
    switch (subCategory?.toLowerCase()) {
      case "hotel":
        return hotelIcon;
      case "lounge":
        return loungeIcon;
      case "spa":
        return spaIcon;
      default:
        return accommodationIcon;
    }
  }

  if (category === "HEALTH_AND_EMERGENCY") {
    switch (subCategory?.toLowerCase()) {
      case "hospital":
        return hospitalIcon;
      case "clinic":
        return clinicIcon;
      case "doctor":
        return doctorIcon;
      case "dentist":
        return dentistIcon;
      case "pharmacy":
        return pharmacyIcon;
      default:
        return healthAndEmergencyIcon;
    }
  }

  if (category === "EDUCATION") {
    switch (subCategory?.toLowerCase()) {
      default:
        return educationIcon;
    }
  }

  if (category === "ARTS_CULTURE_ENTERTAINMENT") {
    switch (subCategory?.toLowerCase()) {
      default:
        return artCultureEntertainmentIcon;
    }
  }

  if (category === "SHOPPING_AND_SERVICES") {
    switch (subCategory?.toLowerCase()) {
      default:
        return shoppingAndServicesIcon;
    }
  }



  switch (subCategory?.toLowerCase()) {
    case "restaurant":
      return restaurantIcon;
    case "cafe":
      return cafeIcon;
    case "bar":
      return barIcon;
    case "park":
      return parkIcon;
    case "museum":
      return museumIcon;
    default:
      return defaultIcon;
  }
};