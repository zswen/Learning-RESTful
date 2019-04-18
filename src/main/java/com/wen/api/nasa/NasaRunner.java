package com.wen.api.nasa;

import java.io.IOException;
import java.io.InputStream;

import com.wen.api.util.ApiRequestClient;
import com.wen.api.util.ApiUtilites;
import com.wen.api.util.KeyValuePair;
import com.wen.api.util.UrlBuilder;
import com.wen.api.util.ApiRequestClient.HttpMethod;
import com.wen.api.util.UrlBuilder.Protocol;

public class NasaRunner {

	public static void main(String[] args) throws IOException {
		
		String date = "2018-10-13";
		
		
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder = urlBuilder.setProtocol(Protocol.HTTPS)
				.setDomain("api.nasa.gov") // Get them from specific API doc
				.setPath("neo/rest/v1/feed") // Get them from specific API doc
				.addQueryParameter(new KeyValuePair("start_date", date)) // Get them from specific API doc
				.addQueryParameter(new KeyValuePair("end_date", date)) // Get them from specific API doc
				.addQueryParameter(new KeyValuePair("api_key", "DEMO_KEY"));
		String url = urlBuilder.build();
		
		
		System.out.println(url);
		ApiRequestClient requestClient = new ApiRequestClient();
		InputStream responseStream = requestClient.establishURLConnection(url, HttpMethod.GET, null);
		String response = ApiUtilites.convertStreamToString(responseStream);
		
		NasaResponseParser responseParser = new NasaResponseParser();
		
		String formattedResponse = responseParser.parseResponse(response, date);
		System.out.println(formattedResponse);
	}
}
