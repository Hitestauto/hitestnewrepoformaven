/**
 * 
 */
package rFQModules;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseclassFiles.BaseClass;



public class ForgotPassword {

	@FindBy(xpath = "//a[text()=' LOGIN']") WebElement login;
	@FindBy(xpath = "//a[contains(text(),' Forgot Password?')]") WebElement forgotPassword;
	@FindBy(id = "PasswordResetEmail") WebElement emailText;
	@FindBy(xpath = "//button[@id='FindPasswordBtn']") WebElement sendEmailBtn;
	@FindBy(id = "FindPasswordMsg") WebElement emailFoundMsg;
	@FindBy(xpath = "//h4[contains(text(),' Send Email')]/preceding-sibling::button") WebElement closeBtn;
	
	@FindBy(id = "addOverlay") WebElement emailTextField;
	@FindBy(xpath = "//td[contains(text(),'Password recovery requested')]") List<WebElement> referLinkonMail;
	@FindBy(xpath = "(//p[contains(text(),'Hello Test,')]/following-sibling::p/a)[1]") WebElement forgotPwdLink;
	@FindBy(id = "NewPassword") WebElement newPwdField;
	@FindBy(id = "ConfirmPassword") WebElement confirmPwdField;
	@FindBy(id = "btnSbmt") WebElement submitBtn;
	@FindBy(xpath = "//div[@id='container_common']/descendant::h3") WebElement successMsg;
	
	@FindBy(xpath = "(//p[contains(text(),'Hello Test,')]/following-sibling::p/a)[2]") WebElement siteLink;
	@FindBy(xpath = "//h3[contains(text(),'Unsubscribe')]") WebElement unsubscribeText;
	
	@FindBy(xpath = "//a[text()='Privacy Policy']") WebElement privacyLink;
	@FindBy(xpath = "//body[@id='home']/descendant::h3[1]") WebElement text;
	@FindBy(xpath = "//a[text()='Terms and Conditions']") WebElement conditionsLink;

	
	WebDriver driver;
	public static String msg=null;
	
	public ForgotPassword(WebDriver driver) {
		
		this.driver=driver;
		PageFactory.initElements(driver, this);
	}
	
	public String forgotPasswordEmail(String emailID) throws IOException {
		
		BaseClass.sleep(2000);
		login.click();
		BaseClass.sleep(2000);
		forgotPassword.click();
		BaseClass.sleep(2000);;
		emailText.sendKeys(emailID);
		BaseClass.sleep(2000);
		sendEmailBtn.click();
		BaseClass.sleep(4000);
		
		msg=emailFoundMsg.getText();
		System.out.println(msg);
		BaseClass.sleep(2000);
		if(msg.equalsIgnoreCase("A Reset Password link has been sent to your email address.")) {
			System.out.println("Email has been sent to email address");
			verifyForgotPasswordEmail(emailID);
			forgotPassword();
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			verifyUnsubscribeSite();
			driver.switchTo().window(tabs.get(1));
			verifyPrivacyPolicyLink();
			driver.switchTo().window(tabs.get(1));
			verifytermCondition();
			driver.switchTo().window(tabs.get(1));
			driver.close();
			BaseClass.sleep(1000);
			driver.switchTo().window(tabs.get(0));
			closeBtn.click();
			return msg;
		}
		else if(msg.equalsIgnoreCase("Email does not exist.")) {
			System.out.println("Email has not sent to email address");
			BaseClass.sleep(1000);
			closeBtn.click();
			return msg;
		}
		else {
			System.out.println("Please enter email id");
			return "Please enter email id";
		}
	}
	
	public void verifyForgotPasswordEmail(String email) {
		
		((JavascriptExecutor)driver).executeScript("window.open()");
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		driver.switchTo().window(tabs.get(1));
		driver.navigate().to("https://www.mailinator.com/");
		BaseClass.sleep(5000);
		
		emailTextField.sendKeys(email);
		emailTextField.sendKeys(Keys.ENTER);
		BaseClass.sleep(1000);
		driver.navigate().refresh();
		BaseClass.sleep(5000);
		
		Iterator<WebElement> itr = referLinkonMail.iterator();
		while (itr.hasNext()) {
				
			 WebElement webelement = itr.next();
			 String values=webelement.getText();
			if(values.equalsIgnoreCase("Password recovery requested")) {
				webelement.click();
				break;
			}
		}
		BaseClass.sleep(1000);
		driver.navigate().refresh();
		BaseClass.sleep(3000);
	}
	
	public void forgotPassword() throws IOException {
		
		driver.switchTo().frame("html_msg_body");
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", forgotPwdLink);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		BaseClass.sleep(1000);
		driver.switchTo().window(tabs.get(1));
		BaseClass.sleep(1000);
		driver.switchTo().window(tabs.get(2));
		
		newPwdField.sendKeys(BaseClass.readExcelData("ForgotPassword", 1, 1));
		BaseClass.sleep(1000);
		confirmPwdField.sendKeys(BaseClass.readExcelData("ForgotPassword", 1, 1));
		BaseClass.sleep(2000);
		submitBtn.click();
		BaseClass.sleep(3000);
		
		String msg=successMsg.getText();
		if(msg.contains("Your password has been reset successfully....")) {
			System.out.println("Password reset successfully");
		}else {
			System.out.println("Password not reset");
		}
	}
	public void verifyUnsubscribeSite() {
		driver.switchTo().frame("html_msg_body");
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", siteLink);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		BaseClass.sleep(1000);
		driver.switchTo().window(tabs.get(2));
		BaseClass.sleep(2000);
		
		String txt=unsubscribeText.getText();
		if(txt.equalsIgnoreCase("Unsubscribe")) {
			System.out.println("Unsubscribe List verified");
		}else {System.out.println("Unsubscribe Link not verify");}
		
		BaseClass.sleep(1000);
		driver.close();
	}
	
	public void verifyPrivacyPolicyLink() {
		driver.switchTo().frame("html_msg_body");
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", privacyLink);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		BaseClass.sleep(1000);
		driver.switchTo().window(tabs.get(2));
		BaseClass.sleep(2000);
		
		String txt=text.getText();
		if(txt.equalsIgnoreCase("Privacy Policy")) {
			System.out.println("Privacy Policy link verified");
		}else {System.out.println("Privacy Policy Link not verify");}
		
		BaseClass.sleep(1000);
		driver.close();
	}
	
	public void verifytermCondition() {
		
		driver.switchTo().frame("html_msg_body");
		((JavascriptExecutor)driver).executeScript("arguments[0].click();", conditionsLink);
		ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
		BaseClass.sleep(1000);
		driver.switchTo().window(tabs.get(2));
		BaseClass.sleep(2000);
		
		String txt=text.getText();
		if(txt.equalsIgnoreCase("Terms and Conditions")) {
			System.out.println("Terms and Conditions link verified");
		}else {System.out.println("Terms and Conditions Link not verify");}
		
		BaseClass.sleep(1000);
		driver.close();
	}
}
