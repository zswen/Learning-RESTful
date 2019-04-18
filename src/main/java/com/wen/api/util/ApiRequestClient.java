package com.wen.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ApiRequestClient {
	
	public InputStream establishURLConnection(String url, HttpMethod requestMethod, KeyValuePair[] headers) throws IOException {
		URL urlConnection = new URL(url);
		HttpsURLConnection connection = (HttpsURLConnection)urlConnection.openConnection();
		
		connection.setRequestMethod(requestMethod.name());
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(300_000);
		
		if (headers != null && headers.length > 1) {
			for (KeyValuePair header : headers) {
				connection.setRequestProperty(header.getKey(), header.getValue());
			}
		}
		
		connection.connect();
		
		return connection.getInputStream();
		
	}
	
	public enum HttpMethod {
		GET, POST, PUT, DELETE
	}
}
