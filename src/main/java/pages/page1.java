package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;


public class page1 {
	

	
	public By qTextbox = By.name("q");
	
	
	public void typeQTextbox(String s, WebDriver driver) { 
		
	driver.findElement(qTextbox).sendKeys(s); 
	
		
	}
	
	
}
