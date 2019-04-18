package com.wen.api.nasa;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class NasaResponseParser {
	private int potentiallyHazardousAsteroidCount;
	
	public NasaResponseParser() {
		potentiallyHazardousAsteroidCount = 0;
	}
	
	public String parseResponse(String apiResponse, String date) {
		
		StringBuilder builder = new StringBuilder();
		builder.append("There is/are %d potentially hazardous asteroid(s) headed toward earth. \n.");
	
		JsonParser parser = new JsonParser();
		JsonObject fullResponse = parser.parse(apiResponse).getAsJsonObject();
		
		
		JsonObject neos = fullResponse.get("near_earth_objects").getAsJsonObject();
		
		JsonArray todaysAsteroids = neos.get(date).getAsJsonArray();
		
		for (JsonElement asteroid : todaysAsteroids) {
			JsonObject asteroidObject = asteroid.getAsJsonObject();
			boolean isPotentiallyHazardous = asteroidObject.get("is_potentially_hazardous_asteroid").getAsBoolean();
			if (isPotentiallyHazardous) {
				potentiallyHazardousAsteroidCount++;
				
				String asteroidName = asteroidObject.get("name").getAsString();
				
				JsonArray cadArray = asteroidObject.get("close_approach_data").getAsJsonArray();
				
				for (JsonElement cad : cadArray) {
					JsonObject cadObject = cad.getAsJsonObject();
					JsonObject velocity = cadObject.get("relative_velocity").getAsJsonObject();
					String kph = velocity.get("kilometers_per_hour").getAsString();
					
					JsonObject missDistance = cadObject.get("miss_distance").getAsJsonObject();
					String misKilometers = missDistance.get("kilometers").getAsString();
					
					builder.append(asteroidName);
					builder.append("is traveling at ");
					builder.append(kph);
					builder.append("kilometers per hour, but fortunately will miss earth by");
					builder.append(misKilometers);
					builder.append("kilometers. \n");
				}
				
			}
			
		}
		
		
		return String.format(builder.toString(), potentiallyHazardousAsteroidCount);
	}
}
