package com.wen.api.pivotaltracker;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wen.api.util.ApiRequestClient;
import com.wen.api.util.ApiRequestClient.HttpMethod;
import com.wen.api.util.ApiUtilities;
import com.wen.api.util.KeyValuePair;
import com.wen.api.util.UrlBuilder;
import com.wen.api.util.UrlBuilder.Protocol;

public class PivotalTrackerProjects {
	
	public List<String> getProjectIds(String apiToken) throws Exception {
		List<String> projectIds = new ArrayList<>();
		
		UrlBuilder urlBuilder = new UrlBuilder();
		
		urlBuilder = urlBuilder
				.setProtocol(Protocol.HTTPS)
				.setDomain("www.pivotaltracker.com")
				.setPath("services/v5/projects");
		
		ApiRequestClient requestClient = new ApiRequestClient();
		
		InputStream responseStream = requestClient.establishURLConnection(urlBuilder.build(), HttpMethod.GET, null, new KeyValuePair("x-TrackerToken", apiToken));
	
		String response = ApiUtilities.convertStreamToString(responseStream);
		
		JsonParser parser = new JsonParser();
		JsonArray responseArray = parser.parse(response).getAsJsonArray();
		for (JsonElement project : responseArray) {
			JsonObject projectObj = project.getAsJsonObject();
			projectIds.add(projectObj.getAsString());
		}
		
		return projectIds;
	}

}
