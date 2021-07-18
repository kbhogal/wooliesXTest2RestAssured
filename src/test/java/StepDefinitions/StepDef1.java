package StepDefinitions;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.junit.Assert;

import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;


public class StepDef1 {

	  public static RestAssured RestAssuredObj; 
      static String City; 
      static String Day; 
      static String[] Temp = new String[2]; 
      static int[] dayFromToday = new int[2]; 
      
      
      static  String DAY[] = {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"}; 
      
      
	
	@Given("^I want to holiday in \"([^\"]*)\"$")
	public void i_want_to_holiday_in(String city) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	City= city; 
	
	}
	
	@Given("^I like to holiday only on \"([^\"]*)\"$")
	public void i_like_to_holiday_only_on(String day) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
	Day = day; 
	
	}
	
	@When("^I look up weather forecast$")
	public void i_look_up_weather_forecast() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		RestAssured.baseURI = "https://api.openweathermap.org/data/2.5/weather/";
	}
	
	@Then("^I receive weather forecast$")
	public void i_receive_weather_forecast() throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		
    	RestAssured.baseURI = "https://api.openweathermap.org/data/2.5/weather/";
 		RequestSpecification request = RestAssured.given();
 		
 		Calendar calendar = Calendar.getInstance();
 		int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
 		int tempIndex =0; 
 		
 		
 	
 		
  //Sunday as 1 Saturday 7 Thursday 5 
 		
 		 for(int i =1; i<=16; i++)   {		
            
 		    System.out.println("Day "+ i + " is Day of the week: "+ DAY[dayOfWeek-1] );
 		 
 			if (dayOfWeek==5) { 
		    io.restassured.response.Response response =  request.queryParam("q", City)
                 .queryParam("cnt", Integer.toString(i))
                 .queryParam("units", "metric")
				 .queryParam("appid", "1048b9c09defa71da59680bb7ac0fd49")	   
                 .get(); 
		    
		    System.out.println(response.getBody().prettyPrint());
		    
		    Assert.assertTrue(response.getStatusCode() == 200);
		    
		    JsonPath jsonPathEvaluator = response.jsonPath();
		    Temp[tempIndex] = jsonPathEvaluator.getString("main.temp");
		    
		    System.out.println("Temperature is: "+ Temp[tempIndex]);
		    dayFromToday[tempIndex] = i;
		    
		    tempIndex ++; 
 			}
 			
 			dayOfWeek++; 
 			if (dayOfWeek == 8) dayOfWeek =1 ;
 			
		 
 	    }


	}
	
	@Then("^the temperature is warmer than (\\d+) degrees$")
	public void the_temperature_is_warmer_than_degrees(int minTemp) throws Throwable {
	    // Write code here that turns the phrase above into concrete actions
		

		Calendar c = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		
         for (int i=0;i<=Temp.length-1; i++) {
        	 
        	
        	 //add sydney city assertion and json status code. 
        	 
        
        	Assert.assertTrue(Float.parseFloat(Temp[i]) > minTemp);
        	
        	if ((i==0) && (Float.parseFloat(Temp[i]) > minTemp)) { 
        		
        		System.out.println("The temperature for " + City + " is pleasant for next " + Day + " and the temperature is: " + Temp[i]);
        		c.add(Calendar.DATE, dayFromToday[i]-1);

        		
                System.out.println("The Date on that " + Day + " is " + sdf.format(c.getTime()));
        		
        	}
        	else if ((i==1) && (Float.parseFloat(Temp[i]) > minTemp)) { 
        		c = Calendar.getInstance();
        		System.out.println("The temperature for " + City + " is pleasant for next to next " + Day + " and the temperature is: " + Temp[i]);
        		c.add(Calendar.DATE, dayFromToday[i]-1);
        		System.out.println("The Date on that " + Day + " is " + sdf.format(c.getTime()));
        	}
        	
        	 
        	 
         }
		

	
	}


}
