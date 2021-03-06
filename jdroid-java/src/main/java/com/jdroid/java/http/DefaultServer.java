package com.jdroid.java.http;

import java.util.List;

public class DefaultServer implements Server {
	
	private String name;
	private String baseUrl;
	private Boolean supportsSsl;
	
	public DefaultServer(String name, String baseUrl, Boolean supportsSsl) {
		this.name = name;
		this.baseUrl = baseUrl;
		this.supportsSsl = supportsSsl;
	}
	
	public DefaultServer(String baseUrl) {
		this(DefaultServer.class.getSimpleName(), baseUrl, true);
	}
	
	/**
	 * @see com.jdroid.java.http.Server#getName()
	 */
	@Override
	public String getName() {
		return name;
	}
	
	/**
	 * @see com.jdroid.java.http.Server#getBaseUrl()
	 */
	@Override
	public String getBaseUrl() {
		return baseUrl;
	}
	
	/**
	 * @see com.jdroid.java.http.Server#supportsSsl()
	 */
	@Override
	public Boolean supportsSsl() {
		return supportsSsl;
	}
	
	/**
	 * @see com.jdroid.java.http.Server#isProduction()
	 */
	@Override
	public Boolean isProduction() {
		return true;
	}
	
	/**
	 * @see com.jdroid.java.http.Server#getHttpWebServiceProcessors()
	 */
	@Override
	public List<HttpWebServiceProcessor> getHttpWebServiceProcessors() {
		return null;
	}
	
	/**
	 * @see com.jdroid.java.http.Server#instance(java.lang.String)
	 */
	@Override
	public Server instance(String name) {
		return null;
	}
}
