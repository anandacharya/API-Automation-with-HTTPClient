/**
 * 
 */
package com.qa.tests;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.data.Users;

/**
 * @author anand acharya
 *
 */
public class PostAPITest extends TestBase {

	TestBase testbase;
	String serviceUrl;
	String url;
	String apiUrl;
	RestClient restClient;
	
	
	@BeforeMethod
	public void setup(){
		testbase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		//https://reqres.in/api/users
		url = serviceUrl + apiUrl;
	}
	
	@Test
	public void postAPITest() throws JsonGenerationException, JsonMappingException, IOException{
		restClient = new RestClient();
		HashMap<String, String> headerMap = new HashMap<String, String>(); //hashmap obbject for header
		headerMap.put("Content-Type", "application/json");
		
		//jackson API: for marshelling ie to convert Users.java to json to send the request
		ObjectMapper mapper = new ObjectMapper();
		Users users = new Users("morpheus","leader");
		
		//object to json file:
		mapper.writeValue(new File(System.getProperty("user.dir")+"/src/main/java/com/qa/data/users.json"), users);
		
		//object to json string:
		String usersJsonString = mapper.writeValueAsString(users);
		System.out.println(usersJsonString);
		
		//trigger the post request
		CloseableHttpResponse httpResponse = restClient.post(url, usersJsonString, headerMap);
		
		//1. Status code
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		Assert.assertEquals(statusCode, testbase.RESPONSE_STATUS_CODE_201);
		
		//2. JSON String
		//Fetch the response json string using EntityUtils and convert into entity in std UTF-8 format
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		
		JSONObject responseJson = new JSONObject(responseString);
		System.out.println("The response from API is "+responseString);
		
		//json to java object: ie unmarshelling
		Users usersResObj = mapper.readValue(responseString, Users.class);
		System.out.println(usersResObj);
		
		Assert.assertTrue(users.getName().equals(usersResObj.getName()));
		Assert.assertTrue(users.getJob().equals(usersResObj.getJob()));
		
		System.out.println(usersResObj.getId());
		System.out.println(usersResObj.getCreatedAt());

	}
	
}
