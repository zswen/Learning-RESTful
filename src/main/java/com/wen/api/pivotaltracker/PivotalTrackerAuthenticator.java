package com.wen.api.pivotaltracker;

import java.io.InputStream;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wen.api.util.ApiRequestClient;
import com.wen.api.util.ApiUtilities;
import com.wen.api.util.ApiRequestClient.HttpMethod;
import com.wen.api.util.UrlBuilder;
import com.wen.api.util.UrlBuilder.Protocol;

public class PivotalTrackerAuthenticator {
	public String getApiToken() throws Exception { // create a api call and retrieve token
		UrlBuilder urlBuilder = new UrlBuilder();
		
		String username = "zswen";
		String password = "!J54w689hwo";
		
		urlBuilder = urlBuilder
				.setProtocol(Protocol.HTTPS)
				.setDomain("www.pivotaltracker.com")
				.setPath("services/v5/me");
		
		ApiRequestClient requestClient = new ApiRequestClient();
		InputStream responseStream = requestClient.establishURLConnection(urlBuilder.build(), HttpMethod.GET, null, ApiUtilities.getBasicAuthHeader(username, password));
	 
		String response = ApiUtilities.convertStreamToString(responseStream);
		
		JsonParser parser = new JsonParser();
		JsonObject responseObject = parser.parse(response).getAsJsonObject();
		
		//ApiUtilites.prettyPrintJson(responseObject);
		
		String apiToken = responseObject.get("api_token").getAsString();
		
		return apiToken;
	}
}
