package com.wen.api.pivotaltracker;

import java.util.List;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wen.api.util.ApiUtilities;

public class PivotalTrackerRunner {
	
	public static void main(String[] args) throws Exception {
		PivotalTrackerAuthenticator authenticator = new PivotalTrackerAuthenticator(); // get apiToken
		String apiToken = authenticator.getApiToken();
		
		PivotalTrackerProjects project = new PivotalTrackerProjects();
		List<String> projectIds = project.getProjectIds(apiToken); // fetch project ids
		
		
		
		PivotalTrackerStories stories = new PivotalTrackerStories();
		JsonParser parser = new JsonParser();
		
		for (String id : projectIds) {
			String response = stories.getProjectStores(id, apiToken);
			JsonElement storiesJson = parser.parse(response);
			ApiUtilities.prettyPrintJson(storiesJson);
			
		}
		
		
	}
	
}
