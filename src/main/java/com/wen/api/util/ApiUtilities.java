package com.wen.api.util;

import java.io.InputStream;
import java.util.Base64;
import java.util.Scanner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

public class ApiUtilities {
	
	public static String convertStreamToString(InputStream is) {
		String responseString = "";
		Scanner scanner = new Scanner(is, "UTF-8");
		responseString = scanner.useDelimiter("\\A").next();
		scanner.close();
		return responseString;
	}
	
	public static KeyValuePair getBasicAuthHeader(String username, String password) {
		String toEncodeString = username + ":" + password; // Basic Authentication takes a username and a password, uses : to seperate them. And base64-encode them
		String encodedString = Base64.getEncoder().encodeToString(toEncodeString.getBytes());
		return new KeyValuePair("Authorization", "Basic " + encodedString);
	}
	
	public static void prettyPrintJson(JsonElement element) {
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		String prettyJson = gson.toJson(element);
		System.out.println(prettyJson);

	}
}
