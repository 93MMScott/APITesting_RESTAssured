package com.qa.hotel.test;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class Step {
	
	RequestSpecification request = RestAssured.given();
	Response response;
	public static int id_count = 0;
	String arrayString;
	JSONObject jsonObject;
		
	@Given("^items on the list$")
	public void items_on_the_list() throws Throwable {		
		//GET
		response = request.get("http://localhost:8090/example/v1/hotels");
		
		//CREATE ARRAY OF CURRENT LIST (work in progress)
//		arrayString = response.asString();
//		JSONParser parser = new JSONParser(); 
//		JSONObject jsonObject = (JSONObject) parser.parse(arrayString);
//		JSONArray jsonArray = (JSONArray) jsonObject.get("content");
//		
//		//Array is made up of objects, loop through each
//		for (Object obj : jsonArray) { 
//			//Turn the object to a string which is then parsed to JSONObject
//			jsonObject = (JSONObject) parser.parse(obj.toString());
//			System.out.println(jsonObject.get("id"));
//		}
//		System.out.println("ARRAY:");
//		System.out.println(jsonObject);
		
		//TEST
		response.then().statusCode(200);
		//SYSOUT
		System.out.println("Full list:");
		response.prettyPrint();
	}

	@When("^the item is posted$")
	public void the_item_is_posted() throws Throwable {
		//DATA
		request.header("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		json.put("city", "Glasgow");
		json.put("description", "Beautiful");
		json.put("id", id_count);
		json.put("name", "The Argyll");
		json.put("rating", 5);
		request.body(json);
		//POST
		response = request.post("http://localhost:8090/example/v1/hotels");
		//TEST
		response.then().statusCode(201);
		//SYSOUT
		System.out.println("Posted:");
		request.get("http://localhost:8090/example/v1/hotels/" +id_count).prettyPrint();
	}

	@When("^the item is updated$")
	public void the_item_is_updated() throws Throwable {
		//DATA
		request.header("Content-Type", "application/json");
		JSONObject json = new JSONObject();
		json.put("city", "Edinburgh");
		json.put("description", "Beautiful");
		json.put("id", id_count);
		json.put("name", "The Argyll");
		json.put("rating", 5);
		request.body(json);
		//UPDATE
		response = request.put("http://localhost:8090/example/v1/hotels/" +id_count);
		//TEST
		response.then().statusCode(204);
		// SYSOUT
		System.out.println("Updated:");
		request.get("http://localhost:8090/example/v1/hotels/" +id_count).prettyPrint();
	}

	@When("^the item is deleted$")
	public void the_item_is_deleted() throws Throwable {
		//DELETE
		request.delete("http://localhost:8090/example/v1/hotels/" +id_count);
		response.then().statusCode(204);
    	System.out.println("ID " +id_count+ " deleted.");
	}
	

	@Then("^the status code fails")
	public void the_status_code_fails() throws Throwable {
		System.out.println(id_count);
		System.out.println("http://localhost:8090/example/v1/hotels/" +id_count);
		response = request.get("http://localhost:8090/example/v1/hotels/" +id_count);
		response.then().statusCode(404);
		System.out.println("Test successful: entry not found.");
	}
}
