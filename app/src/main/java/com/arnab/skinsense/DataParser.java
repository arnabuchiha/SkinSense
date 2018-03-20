package com.arnab.skinsense;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

class DataParser {


    private HashMap<String,String> getDuration(JSONArray googleDirectionsJson)
    {
        HashMap<String,String> googleDirectionsMap = new HashMap<>();
        String duration = "";
        String distance ="";


        try {

            duration = googleDirectionsJson.getJSONObject(0).getJSONObject("duration").getString("text");
            distance = googleDirectionsJson.getJSONObject(0).getJSONObject("distance").getString("text");

            googleDirectionsMap.put("duration" , duration);
            googleDirectionsMap.put("distance", distance);

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return googleDirectionsMap;
    }


    private HashMap<String, String> getPlace(JSONObject googlePlaceJson)
    {
        HashMap<String, String> googlePlacesMap = new HashMap<>();
        String placeName = "-NA-";
        String vicinity = "-NA-";
        String latitude = "";
        String longitude = "";
        String reference = "";
        Log.d("getPlace", "Entered");


        try {
            if(!googlePlaceJson.isNull("name"))
            {

                placeName = googlePlaceJson.getString("name");

            }
            if( !googlePlaceJson.isNull("vicinity"))
            {
                vicinity = googlePlaceJson.getString("vicinity");

            }
            latitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lat");
            longitude = googlePlaceJson.getJSONObject("geometry").getJSONObject("location").getString("lng");

            reference = googlePlaceJson.getString("reference");

            googlePlacesMap.put("place_name" , placeName);
            googlePlacesMap.put("vicinity" , vicinity);
            googlePlacesMap.put("lat" , latitude);
            googlePlacesMap.put("lng" , longitude);
            googlePlacesMap.put("reference" , reference);


            Log.d("getPlace", "Putting Places");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return googlePlacesMap;
    }



    private List<HashMap<String,String>> getPlaces(JSONArray jsonArray)
    {
        int count = jsonArray.length();
        List<HashMap<String,String>> placesList = new ArrayList<>();
        HashMap<String,String> placeMap = null;
        Log.d("Places", "getPlaces");

        for(int i = 0;i<count;i++)
        {
            try {
                placeMap = getPlace((JSONObject) jsonArray.get(i));
                placesList.add(placeMap);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return placesList;

    }

    public List<HashMap<String,String>> parse(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject;

        try {
            Log.d("Places", "parse");

            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("results");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getPlaces(jsonArray);
    }

    public HashMap<String,String> parseDirections(String jsonData)
    {
        JSONArray jsonArray = null;
        JSONObject jsonObject = null;

        try {
            jsonObject = new JSONObject(jsonData);
            jsonArray = jsonObject.getJSONArray("routes").getJSONObject(0).getJSONArray("legs"); //routes array
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return getDuration(jsonArray);

    }



    private List<HashMap<String,String>> getPaths(JSONArray googleDirectionsJson)
    {
        List<HashMap<String,String>> googleDirectionsList = new ArrayList<>();
        JSONObject googlePathJson = null;
        int count_paths = googleDirectionsJson.length();


        for(int i = 0;i<count_paths;i++)
        {
            try {
                googlePathJson = googleDirectionsJson.getJSONObject(i);
                googleDirectionsList.add(getPath(googlePathJson));

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return googleDirectionsList;
    }

    private HashMap<String,String> getPath(JSONObject googlePathJson)
    {
        HashMap<String, String> googlePathMap = new HashMap<>();
        String start_lat = "";
        String start_lng = "";
        String end_lat = "";
        String end_lng = "";
        String polyline = "";

        try {
            start_lat = googlePathJson.getJSONObject("start_location").getString("lat");
            start_lng = googlePathJson.getJSONObject("start_location").getString("lng");
            end_lat = googlePathJson.getJSONObject("end_location").getString("lat");
            end_lng = googlePathJson.getJSONObject("end_location").getString("lng");
            polyline = googlePathJson.getJSONObject("polyline").getString("points");

            googlePathMap.put("start_lat" , start_lat);
            googlePathMap.put("start_lng", start_lng);
            googlePathMap.put("end_lat" , end_lat);
            googlePathMap.put("end_lng", end_lng);
            googlePathMap.put("polyline", polyline);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        return googlePathMap;
    }



}
