package com.practice.steps;

import java.util.List;
import java.util.Map;

import com.practice.utils.DBUtils;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DatabaseTesting extends DBUtils {
	List<Map<String,String>> customerList;

	@Given("I am connected to the database")
	public void i_am_connected_to_the_database() {
		getConnection();
		System.out.println("Successfull connection");
	}
	
	
	@When("I get the top three customers by credit limit")
	public void i_get_the_top_three_customers_by_credit_limit() {
		
		customerList = storeDataFromDB("Select * from customers order by creditLimit desc limit 3");
		
	 
	}
	@Then("I print their names and credit limits")
	public void i_print_their_names_and_credit_limits() {
		
		
		for(Map<String,String> customer : customerList) {
			
			String name = customer.get("customerName");
			String credit = customer.get("creditLimit");
			System.out.println(name + " -> " + credit);
			
		}
	}
	@Then("I close the database connection")
	public void i_close_the_database_connection() {
	    closeConnection();
	    System.out.println("Connection closed");
	}
	
}
