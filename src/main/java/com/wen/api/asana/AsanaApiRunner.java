package com.wen.api.asana;

import java.io.InputStream;

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.wen.api.util.ApiRequestClient;
import com.wen.api.util.ApiRequestClient.HttpMethod;
import com.wen.api.util.ApiUtilities;
import com.wen.api.util.KeyValuePair;
import com.wen.api.util.UrlBuilder;
import com.wen.api.util.UrlBuilder.Protocol;

public class AsanaApiRunner {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		AuthToken token = new AuthToken();
		
		if (token.getToken() == null) {
			token.getAuthTokenFromCode("0/a87267c23cdd2bf7a7da3d8b39d87408");
		}
		
		UrlBuilder urlBuilder = new UrlBuilder();
		
		urlBuilder = urlBuilder.setProtocol(Protocol.HTTPS)
				.setDomain("app.asana.com")
				.setPath("api/1.0/projects");
		
		ApiRequestClient request = new ApiRequestClient();
		InputStream responseStream = request.establishURLConnection(urlBuilder.build(), HttpMethod.GET, null, 
				new KeyValuePair("Authorization", "Bearer " + token.getToken()));
		
		String response = ApiUtilities.convertStreamToString(responseStream);
		JsonParser parser = new JsonParser();
		JsonElement responseJson = parser.parse(response);
		ApiUtilities.prettyPrintJson(responseJson);
	}

}
