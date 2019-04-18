package com.wen.api.pivotaltracker;

import java.io.IOException;
import java.io.InputStream;

import com.wen.api.util.ApiRequestClient;
import com.wen.api.util.UrlBuilder;
import com.wen.api.util.ApiRequestClient.HttpMethod;
import com.wen.api.util.ApiUtilities;
import com.wen.api.util.KeyValuePair;
import com.wen.api.util.UrlBuilder.Protocol;

public class PivotalTrackerStories {
	
	public String getProjectStores(String projectId, String apiToken) throws IOException {
		
		String response = "";
		
		UrlBuilder urlBuilder = new UrlBuilder();
		
		urlBuilder = urlBuilder
				.setProtocol(Protocol.HTTPS)
				.setDomain("www.pivotaltracker.com")
				.setPath(String.format("services/v5/projects/%s/stories", projectId));
		
		ApiRequestClient requestClient = new ApiRequestClient();
		InputStream responseStream = requestClient.establishURLConnection(urlBuilder.build(), HttpMethod.GET, null, 
				new KeyValuePair("x-TrackerToken", apiToken));
		
		response = ApiUtilities.convertStreamToString(responseStream);
		
		return response;
		
	}
}
