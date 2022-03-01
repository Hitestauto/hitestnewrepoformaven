package rFQModules;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import baseclassFiles.BaseClass;



public class Login extends BaseClass 
{

	@FindBy(xpath = "//a[text()=' LOGIN']") WebElement logIn;
	@FindBy(id = "txtUserName") WebElement  userName;
	@FindBy(id = "Password") WebElement password;
	@FindBy(id = "LoginBtn") WebElement submitBtn;
	@FindBy(xpath = "//h1[contains(text(),'Surveys')]") WebElement surveytext;
	
	//GmailLogin
	@FindBy(id = "identifierId") WebElement gmailUname;
	@FindBy(xpath = "//span[contains(text(),'Next')]") WebElement gmailNext;
	@FindBy(xpath = "//body/div[1]/div[1]/div[2]/div[1]/div[2]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/form[1]/span[1]/section[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/div[1]/input[1]") WebElement gmailPassword;
	
	//Sample Cube
	@FindBy(id = "uname") WebElement scUname;
	@FindBy(id = "pass") WebElement scPass;
	@FindBy(id ="login-button") WebElement scLogin;
	@FindBy(xpath ="//body/div[1]/div[3]/div[1]/div[1]/ul[1]/li[7]/a[1]/span[1]") WebElement scBid;
	@FindBy(xpath="//span[contains(text(),'Search MailBox')]") WebElement searchMailBox;
	@FindBy(id = "txtSearchmail") WebElement bidSearchTxt;
	@FindBy(id = "ddlTime") WebElement searchMailTime;
	@FindBy(id = "btnSearchSurvey") WebElement searchBtn;
	@FindBy(xpath ="//tbody/tr[1]/td[1]/input[1]") WebElement selectBidMail;
	@FindBy(id ="sid_28611") WebElement pullInBidCubeBtn;
	@FindBy(xpath = "//p[contains(text(),'Email pulled in Bid Cube')]") WebElement bidMSG;	
	@FindBy(xpath ="//button[contains(text(),'OK')]") WebElement bidMailPopup;
	@FindBy(xpath = "//body/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[3]/div[1]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/input[1]") WebElement searchBidEmail;
	@FindBy(xpath ="//span[contains(text(),'Bidder')]") WebElement assignBidder;
	@FindBy(xpath = "//body/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[6]/div[1]/div[1]/div[2]/div[1]/div[1]/select[1]") WebElement selectBidder;
	@FindBy(xpath = "//body/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[6]/div[1]/div[1]/div[3]/button[2]") WebElement saveBidder;
	@FindBy(xpath = "//span[contains(text(),'OW')]") WebElement assignOW;
	@FindBy(xpath = "//body/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[5]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/select[1]") WebElement selectOW;
	@FindBy(xpath = "//body/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[5]/div[1]/div[1]/div[3]/button[2]") WebElement saveOW;
	@FindBy(xpath = "//span[contains(text(),'SR')]") WebElement assignSR;
	@FindBy(xpath = "//body/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[5]/div[1]/div[1]/div[2]/div[1]/div[1]/div[1]/select[1]") WebElement selectSR;
	@FindBy(xpath = "//body/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[5]/div[1]/div[1]/div[3]/button[2]") WebElement saveSR;
	@FindBy(xpath = "//span[contains(text(),'Selenium Selenium')]") WebElement mapClient;
	@FindBy(id = "clinetName") WebElement selectMapClient;
	@FindBy(xpath ="//body/div[1]/div[3]/div[1]/div[3]/div[3]/div[1]/div[7]/div[1]/div[1]/div[3]/button[1]") WebElement saveBtn;
	@FindBy(xpath ="//button[contains(text(),'Create Proposal')]") WebElement createProposal;
	
	
	
	public void gmailLogin()
	{
		PageFactory.initElements(driver, this);
		BaseClass.implicitilyWait(driver, 10);
        Map<String, String> map=new HashMap<String, String>();
		BaseClass.loadTestDataFile();
		map.put(prop.getProperty("gmailUserNme"), prop.getProperty("gmailPassword"));
	
	for(Map.Entry<String, String> value : map.entrySet())
	{
		String gusername=value.getKey();
		String gpwd=value.getValue();
		
		gmailUname.sendKeys(gusername);
		gmailNext.click();
		BaseClass.sleep(1000);
		gmailPassword.sendKeys(gpwd);
		gmailNext.click();
		BaseClass.sleep(2000);
	}
	
	}		
	/**
	 * This method is used to login in SampleCube 
	 */
	public void SClogin() {
		
		PageFactory.initElements(driver, this);
		/*
		 * BaseClass.implicitilyWait(driver, 10); logIn.click();
		 */
		BaseClass.implicitilyWait(driver, 10);
		Map<String, String> map=new HashMap<String, String>();
		
		BaseClass.loadTestDataFile();
		try {
			
			if(BaseClass.currentURL.contains("qa-admin")) {
				map.put(prop.getProperty("SCUname"), prop.getProperty("SCPass"));
			}
			else {
				map.put(prop.getProperty("SCUname"), prop.getProperty("SCPass"));
			}
		} catch (Exception e) {
			System.out.println("Credential is not correct");
		}
		
		for(Map.Entry<String, String> value : map.entrySet()) 
		{
			String usrnm=value.getKey();
			String pwd=value.getValue();
			
			scUname.sendKeys(usrnm);
			scPass.sendKeys(pwd);
			BaseClass.sleep(1000);
			scLogin.click();
			BaseClass.implicitilyWait(driver, 10);
			bidFuncations();
			
		}
	}
	
	/**
	 * This method is used to select Bid and assign required OW, Sales Rep and Clients 
	 * 
	 * @return
	 */
	public void bidFuncations() {
		try {
			Thread.sleep(1000);
			scBid.click();
			((JavascriptExecutor) driver).executeScript("arguments[0].click();", searchMailBox);
			//searchMailBox.click();
			Thread.sleep(3000);
			//Map<String, String> map=new HashMap<String, String>();	
			Properties props = BaseClass.prop;
			BaseClass.loadTestDataFile();
			String txt = props.getProperty("BIDsearchText");
			bidSearchTxt.sendKeys(txt);
			Thread.sleep(1000);
			BaseClass.selectByIndex(searchMailTime, 3);
			Thread.sleep(1000);
			searchBtn.click();
			Thread.sleep(7000);
			selectBidMail.click();
			Thread.sleep(500);
			pullInBidCubeBtn.click();
			Thread.sleep(8000);			
			System.out.println("Pull Bid message" +bidMSG.getText());
			Thread.sleep(1000);
			bidMailPopup.click();
			Thread.sleep(1000);
			searchMailBox.click();
			Thread.sleep(4000);
			searchBidEmail.sendKeys(txt);
			Thread.sleep(3000);
			assignBidder.click();
			Thread.sleep(500);
			BaseClass.selectByIndex(selectBidder, 40);
			Thread.sleep(1000);
			saveBidder.click();
			Thread.sleep(2000);
			//To search email again
			searchMailBox.click();
			Thread.sleep(4000);
			searchBidEmail.sendKeys(txt);
			Thread.sleep(3000);
			assignOW.click();
			Thread.sleep(1000);
			BaseClass.selectByIndex(assignOW, 57);
			Thread.sleep(500);
			saveOW.click();
			Thread.sleep(2000);
			//To search email again
			searchMailBox.click();
			Thread.sleep(4000);
			searchBidEmail.sendKeys(txt);
			Thread.sleep(3000);
			assignSR.click();
			Thread.sleep(1000);
			BaseClass.selectByIndex(assignSR, 31);
			Thread.sleep(500);
			saveSR.click();
			Thread.sleep(2000);
			//To search email again
			searchMailBox.click();
			Thread.sleep(4000);
			searchBidEmail.sendKeys(txt);
			Thread.sleep(3000);
			mapClient.click();
			Thread.sleep(1000);
			BaseClass.selectByIndex(selectMapClient, 3);
			Thread.sleep(500);
			saveBtn.click();
			Thread.sleep(2000);
			//To search email again
			searchMailBox.click();
			Thread.sleep(4000);
			searchBidEmail.sendKeys(txt);
			Thread.sleep(3000);
			mapClient.click();
			Thread.sleep(1000);
			createProposal.click();
			
			
		} catch (Exception e) {
			System.out.println("Unable to perform action - "+e.getMessage());
			
		}
		
	}
}
