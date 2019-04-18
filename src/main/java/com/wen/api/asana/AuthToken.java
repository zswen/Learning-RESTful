package com.wen.api.asana;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Properties;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.wen.api.util.ApiRequestClient;
import com.wen.api.util.UrlBuilder;
import com.wen.api.util.ApiRequestClient.HttpMethod;
import com.wen.api.util.ApiUtilities;
import com.wen.api.util.KeyValuePair;
import com.wen.api.util.UrlBuilder.Protocol;

public class AuthToken {

	String token;
	String refreshToken;
	Date expires;
	
	
	public AuthToken() throws Exception {
		token = null;
		refreshToken = null;
		expires = new Date();
		getTokenFromProperties();
	}
	
	public void getAuthTokenFromCode(String authCode) throws Exception {
		UrlBuilder builder = new UrlBuilder();
		builder = builder.setProtocol(Protocol.HTTPS)
				.setDomain("app.asana.com")
				.setPath("-/oauth_token");
		
		String postBody = "grant_type=authoritzation_code&" + 
				"client_id=1119291720841392&" + 
				"client_secret=3fec163ebe32591fa112acd7faf1719a&" + 
				"code=" + authCode + "&" + 
				"redirect_url=" + URLEncoder.encode("https://webhook.site/27bd4dcc-722f-421e-959f-9f180abd2e38", "UTF-8");
		
		ApiRequestClient request = new ApiRequestClient();
		InputStream responseStream = request.establishURLConnection(builder.build(), HttpMethod.POST, postBody.toString(), new KeyValuePair("Content-Type", "application/x-www-form-urlencoded"));
		
		String response = ApiUtilities.convertStreamToString(responseStream);
		setTokenFromJson(response);
		writeTokenToProperties();
	}
	
	private void refreshToken() throws Exception {
		// TODO Auto-generated method stub
		UrlBuilder builder = new UrlBuilder();
		
		builder = builder.setProtocol(Protocol.HTTPS)
				.setDomain("app.asana.com")
				.setPath("-/oauth_token");
		
		String postBody = "grant_type=refresh_token&" + 
				"client_id=1119291720841392&" + 
				"client_secret=3fec163ebe32591fa112acd7faf1719a&" + 
				"code=" + refreshToken + "&" + 
				"redirect_url=" + URLEncoder.encode("https://webhook.site/27bd4dcc-722f-421e-959f-9f180abd2e38", "UTF-8");
		
		ApiRequestClient request = new ApiRequestClient();
		InputStream responseStream = request.establishURLConnection(builder.build(), HttpMethod.POST, postBody.toString(), new KeyValuePair("Content-Type", "application/x-www-form-urlencoded"));

		
		String response = ApiUtilities.convertStreamToString(responseStream);
		setTokenFromJson(response);
	}
	
	private void setTokenFromJson(String response) {
		// TODO Auto-generated method stub
		JsonParser parser = new JsonParser();
		JsonObject authResponse = parser.parse(response).getAsJsonObject();
		this.token = authResponse.get("access_token").getAsString();
		this.refreshToken = authResponse.get("refresh_token").getAsString();
		this.expires = new Date(new Date().getTime() + authResponse.get("expires_in").getAsInt() * 1000);
		
	}

	public void getTokenFromProperties() throws Exception {
		File file = new File("/Users/zhangsu.wen/Desktop/oauth.properties");
		if (file.exists()) {
			InputStream in = new FileInputStream(file);
			Properties props = new Properties();
			props.load(in);
			in.close();
			token = props.getProperty("token");
			refreshToken = props.getProperty("refreashToken");
			expires = new Date(Long.parseLong(props.getProperty("expires")));
			
		}
	}
	
	
	
	private void writeTokenToProperties() throws Exception {
		
		File file = new File("/Users/zhangsu.wen/Desktop/oauth.properties");
		FileOutputStream out = new FileOutputStream(file);
		Properties props = new Properties();
		props.setProperty("token", token);
		props.setProperty("refreshToken", refreshToken);
		props.setProperty("expires", String.valueOf(expires.getTime()));
		props.store(out, null);
		out.close();
	}
	
	
	
	public String getToken() throws Exception {
		if (new Date().after(expires)) {
			refreshToken();
		}
		return token;
	}


	public void setToken(String token) {
		this.token = token;
	}
	public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public Date getExpires() {
		return expires;
	}
	public void setExpires(Date expires) {
		this.expires = expires;
	}
	
	
}
