package com.wen.api.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

public class ApiRequestClient {
	
	public InputStream establishURLConnection(String url, HttpMethod requestMethod, String requestBody, KeyValuePair... headers) throws IOException { // 三个点表示可以有任意多个 KeyValuePair
		URL urlConnection = new URL(url);
		HttpsURLConnection connection = (HttpsURLConnection)urlConnection.openConnection();
		
		connection.setRequestMethod(requestMethod.name());
		connection.setConnectTimeout(30000);
		connection.setReadTimeout(300_000);
		
		if (headers != null && headers.length > 0) {
			for (KeyValuePair header : headers) {
				connection.setRequestProperty(header.getKey(), header.getValue());
			}
		}
		
		if (requestMethod == HttpMethod.POST && requestBody != null && !requestBody.isEmpty()) {
			connection.setDoInput(true);
			OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
			osw.write(requestBody);
			osw.flush();
		}
		
		connection.connect();
		
		return connection.getInputStream();
		
	}
	
	public enum HttpMethod {
		GET, POST, PUT, DELETE
	}
}
