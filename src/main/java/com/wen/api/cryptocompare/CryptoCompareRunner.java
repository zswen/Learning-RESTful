package com.wen.api.cryptocompare;

import java.io.IOException;
import java.io.InputStream;

import com.wen.api.util.ApiRequestClient;
import com.wen.api.util.ApiRequestClient.HttpMethod;
import com.wen.api.util.ApiUtilites;
import com.wen.api.util.KeyValuePair;
import com.wen.api.util.UrlBuilder;
import com.wen.api.util.UrlBuilder.Protocol;

public class CryptoCompareRunner {
	public static void main(String[] args) throws IOException {
		
		UrlBuilder urlBuilder = new UrlBuilder();
		urlBuilder = urlBuilder.setProtocol(Protocol.HTTPS)
				.setDomain("min-api.cryptocompare.com") // Get them from specific API doc
				.setPath("data/price") // Get them from specific API doc
				.addQueryParameter(new KeyValuePair("fsym", "ETH")) // Get them from specific API doc
				.addQueryParameter(new KeyValuePair("tsyms", "USD")); // Get them from specific API doc
		
		String url = urlBuilder.build();
		
		ApiRequestClient requestClient = new ApiRequestClient();
		InputStream responseStream = requestClient.establishURLConnection(url, HttpMethod.GET, null);
		String response = ApiUtilites.convertStreamToString(responseStream);
		
		System.out.println("API Response: " + response);
	}
}
