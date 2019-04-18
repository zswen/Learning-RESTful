package com.wen.api.util;

import java.util.ArrayList;
import java.util.List;

public class UrlBuilder {
	
	Protocol protocol;
	String domain;
	String path;
	List<KeyValuePair>  queryParameters;
	
	public String build() {
		StringBuilder builder = new StringBuilder();
		builder.append(protocol.print());
		builder.append(domain);
		
		
		if (!domain.endsWith("/") && !path.startsWith("/")) builder.append("/");
		
		if (domain.endsWith("/") && path.startsWith("/")) {
			path = path.substring(1, path.length());
		}
		
		builder.append(path);
		if (queryParameters.size() > 0) {
			builder.append("?");
			for (KeyValuePair param : queryParameters) {
				if (!builder.toString().endsWith("?")) {
					builder.append("&");
				}
				builder.append(param.getKey());
				builder.append("=");
				builder.append(param.getValue());
			}
		}
		return builder.toString();
	}
	
	public UrlBuilder() {
		queryParameters = new ArrayList<>();
	}
	
	public UrlBuilder setProtocol(Protocol protocol) {
		this.protocol = protocol;
		return this;
	}
	
	public UrlBuilder setDomain(String domain) {
		this.domain = domain;
		return this;
	}
	
	public UrlBuilder setPath(String path) {
		this.path = path;
		return this;
	}
	
	public UrlBuilder addQueryParameter(KeyValuePair queryParameter) {
		this.queryParameters.add(queryParameter);
		return this;
	}
	
	
	public enum Protocol {
		HTTP,
		HTTPS,
		SFTP,
		FTP;

		public String print() {
			return name().toLowerCase() + "://";
		}
		
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
