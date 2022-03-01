package rFQTestCaseModues;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import rFQModules.Login;

public class LoginTest extends Login {

	// GmailLogin
	@FindBy(id = "identifierId")
	WebElement gmailUname;
	@FindBy(xpath = "//span[contains(text(),'Next')]")
	WebElement gmailNext;
	@FindBy(xpath = "//body/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/span[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]")
	WebElement gmailPassword;

	
//	  @Parameters("browser")
//	  
//	  @Test
//	  public void launchedURL(String browser) 
//	  { 
//		  browser(browser);
//	      gmaillaunchURL();
//	      gmailLogin();
//	      }
//	 
	@Parameters({"browser","evmnt"})
	//@Parameters("browser")
	@Test
	public void toPerformSCActions(String browser, String evmnt) 
	{
		browser(browser);
		launchURL(evmnt);
		SClogin();
		

	}
	
	

}
