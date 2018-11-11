/**
 * 
 */
package com.qa.tests;

import java.io.IOException;
import java.util.HashMap;

import org.apache.http.Header;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.qa.base.TestBase;
import com.qa.client.RestClient;
import com.qa.util.TestUtil;

/**
 * @author anand acharya
 * Test the API call
 */
public class GetAPITest extends TestBase{
	TestBase testBase;
	String serviceUrl;
	String apiUrl;
	String url;
	RestClient restClient;
	CloseableHttpResponse httpResponse;
	
	@BeforeMethod
	public void setup(){
		testBase = new TestBase();
		serviceUrl = prop.getProperty("URL");
		apiUrl = prop.getProperty("serviceURL");
		//https://reqres.in/api/users
		url = serviceUrl + apiUrl;
	}
	
	@Test(priority=1)
	public void getAPITestWithoutHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		httpResponse = restClient.get(url); //Trigger the GET request
		
		//a. Status Code:
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code is : " +statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
				
		//b. JSON String:
		//To get entire response string we have class EntityUtlis, and we use getEntitiy method to get the string
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		//Convert the response string to JSON object
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Reponse JSON from API is ---> "+responseJSON);
		
		//single value assertion:
		//per_page:
		String perPageValue = TestUtil.getValueByJPath(responseJSON, "/per_page"); //parse the JSON response to get value
		System.out.println("value of per page is --> "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
				
		//total:
		String totalValue = TestUtil.getValueByJPath(responseJSON, "/total"); //parse the JSON response to get value
		System.out.println("value of total is --> "+totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get value from JSON array
		String lastName = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		Assert.assertEquals(lastName,"Bluth");
		Assert.assertEquals(Integer.parseInt(id),1);
		Assert.assertEquals(avatar,"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
		Assert.assertEquals(firstName,"George");
		
		
		//c. All Headers:
		//To get the header we use getAllHeader() method
		//We have to convert header array into hashmap to store the data in key-value format
		Header[] headersArray = httpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for(Header header : headersArray){
			allHeaders.put(header.getName(), header.getValue());	
		}
		System.out.println("Headers Array --> "+allHeaders);
	}
	
	@Test(priority=2)
	public void getAPITestWithHeaders() throws ClientProtocolException, IOException{
		restClient = new RestClient();
		
		//Create a HashMap object
		HashMap<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("Content-Type", "application/json");
//		headerMap.put("Username", "test123");
//		headerMap.put("Password", "test@123");
//		headerMap.put("Auth Token", "12345");
		
		httpResponse = restClient.get(url,headerMap); //trigger the GET request
		
		//a. Status Code:
		int statusCode = httpResponse.getStatusLine().getStatusCode();
		System.out.println("Status code is : " +statusCode);
		
		Assert.assertEquals(statusCode, RESPONSE_STATUS_CODE_200, "Status code is not 200");
				
		//b. JSON String:
		//To get entire response string we have class EntityUtlis, and we use getEntitiy method to get the string
		String responseString = EntityUtils.toString(httpResponse.getEntity(), "UTF-8");
		//Convert the response string to JSON object
		JSONObject responseJSON = new JSONObject(responseString);
		System.out.println("Reponse JSON from API is ---> "+responseJSON);
		
		//single value assertion:
		//per_page:
		String perPageValue = TestUtil.getValueByJPath(responseJSON, "/per_page"); //parse the JSON response to get value
		System.out.println("value of per page is --> "+perPageValue);
		Assert.assertEquals(Integer.parseInt(perPageValue), 3);
				
		//total:
		String totalValue = TestUtil.getValueByJPath(responseJSON, "/total"); //parse the JSON response to get value
		System.out.println("value of total is --> "+totalValue);
		Assert.assertEquals(Integer.parseInt(totalValue), 12);
		
		//get value from JSON array
		String lastName = TestUtil.getValueByJPath(responseJSON, "/data[0]/last_name");
		String id = TestUtil.getValueByJPath(responseJSON, "/data[0]/id");
		String avatar = TestUtil.getValueByJPath(responseJSON, "/data[0]/avatar");
		String firstName = TestUtil.getValueByJPath(responseJSON, "/data[0]/first_name");
		
		System.out.println(lastName);
		System.out.println(id);
		System.out.println(avatar);
		System.out.println(firstName);

		Assert.assertEquals(lastName,"Bluth");
		Assert.assertEquals(Integer.parseInt(id),1);
		Assert.assertEquals(avatar,"https://s3.amazonaws.com/uifaces/faces/twitter/calebogden/128.jpg");
		Assert.assertEquals(firstName,"George");
		
		
		//c. All Headers:
		//To get the header we use getAllHeader() method
		//We have to convert header array into hashmap to store the data in key-value format
		Header[] headersArray = httpResponse.getAllHeaders();
		HashMap<String, String> allHeaders = new HashMap<String, String>();
		for(Header header : headersArray){
			allHeaders.put(header.getName(), header.getValue());	
		}
		System.out.println("Headers Array --> "+allHeaders);
	}
	
}
