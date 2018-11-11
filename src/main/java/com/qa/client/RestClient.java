/**
 * 
 */
package com.qa.client;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

/**
 * @author anand acharya
 * For GET, POST, PUT, DELETE methods
 */
public class RestClient {
	
	//1. GET Method without Headers
	public CloseableHttpResponse get(String url) throws ClientProtocolException, IOException{
		//create client connection using createDefault method
		CloseableHttpClient httpClient = HttpClients.createDefault();
		//For Get method we have httpget class, which will create http get connection with the url
		HttpGet httpget = new HttpGet(url); //http get request
		//hit the GET url
		CloseableHttpResponse httpResponse = httpClient.execute(httpget);
		
		return httpResponse; //in the response we have to extract status code, response string, and header from the test class
	}
	
	//2. GET Method with Headers
	public CloseableHttpResponse get(String url, HashMap<String, String> headerMap) throws ClientProtocolException, IOException{ //since headers have key-value pairs, we have to pass HashMap along with url
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url); //http get request
		
		//before execution we have to add header
		for(Map.Entry<String, String> entry : headerMap.entrySet()){
			httpget.addHeader(entry.getKey(), entry.getValue());
		}
		
		CloseableHttpResponse httpResponse = httpClient.execute(httpget);
		return httpResponse;
	}
}
