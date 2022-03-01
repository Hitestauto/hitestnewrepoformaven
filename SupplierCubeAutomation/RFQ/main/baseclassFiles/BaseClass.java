package baseclassFiles;



import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;


/**
 * Description-
 * This Class is used to contains reusing method like browser method, launch URL
 * load properties file, load excel data file, Waits class etc
 * 
*/
public class BaseClass 
{
	
	@FindBy(xpath = "//button[@id='details-button']") WebElement advanceBtn;
	@FindBy(id = "proceed-link") WebElement proceedLink;
	
	public static WebDriver driver=null;
	public static Properties prop;
	public static XSSFWorkbook workbook;
	public static XSSFSheet sheet;
	public static XSSFRow row;
	public static XSSFCell cell;
	public static Select select;
	public static String currentURL;
	public static String urls=null;
	
	/**
	 * This method is used to load properties files
	 * @param str
	 */
	public static void loadPropertiesFiles(String str) 
	{
		
		try {	
			prop=new Properties();	
			prop.load(new FileInputStream(System.getProperty("user.dir")+str));	
				
		} catch (Exception e) {
			System.out.println("File not found"+e.getMessage());
		}
	}
	
	/**
	 * Load test data file in this method 
	 */
	public static void loadTestDataFile() {
				
		String testdatafilepath="/RFQ/main/configFiles/RFQConfig.properties";
		loadPropertiesFiles(testdatafilepath);
	}
	
	/**
	 *  Load Query file in this method
	 */
	public static void queryfile() {
		
		String queryfilepath="/USHealthcare_UserSite/main/ushealthcare_usersite/config/queries.properties";
		loadPropertiesFiles(queryfilepath);
	}
	
	/**
	 * This method is used to load excel sheet file and get data from it
	 * 
	 * @param sheetName
	 * @param rowNo
	 * @param cellNo
	 * @return
	 * @throws IOException
	 */
	public static String readExcelData(String sheetName, int rowNo,int cellNo) throws IOException {
		
		String str = null;
		File file=new File(System.getProperty("user.dir")+"/USHealthcare_UserSite/main/ushealthcare_usersite/exceldata/HealthcareData.xlsx");
		
		try {
			FileInputStream fis=new FileInputStream(file);
			workbook= new XSSFWorkbook(fis);
				
		} catch (FileNotFoundException e) {
			System.out.println("Excel File not loaded "+e.getMessage());
		}
		
		cell=workbook.getSheet(sheetName).getRow(rowNo).getCell(cellNo);

		switch (cell.getCellType())
        {
            case STRING:    //field that represents string cell type
                str = cell.getStringCellValue();
                break;
            case NUMERIC:    //field that represents number cell type            
                str = (long) cell.getNumericCellValue()+"";
                break;
//              case BOOLEAN:    //field that represents boolean cell type  
//            	bValue = cell.getBooleanCellValue();
                default:
    
        }
		return str;
	} 
	
	//To read excel data
	/*	public static String getReaddata(String path,int rowno,int colno)
	{
		String data="D:\\Project\\MavenDemoproject\\testdata\\Excel sheet.xlsx";
		try
		{
			FileInputStream fis=new FileInputStream(path);
			XSSFWorkbook wb= new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			data=sheet.getRow(rowno).getCell(colno).getStringCellValue();
		} catch (Exception e)
		{
			System.out.println("Issue in GetRead data "+e);
		}
		return data;
	}
	public static int getrowcount(String path)
	{
		int count=0;
		try 
		{
			FileInputStream fis=new FileInputStream(path);
			XSSFWorkbook wb= new XSSFWorkbook(fis);
			XSSFSheet sheet = wb.getSheetAt(0);
			count=sheet.getLastRowNum();
		} catch (Exception e)
		{
			System.out.println("Issue in getrowcount "+e);
		}
		return count;
	}*/
	

	//launching browser 
	public void browser(String browser) {
		
		if(browser.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();	
			driver=new ChromeDriver();
			}
		else if(browser.equalsIgnoreCase("firefox")) {	
			WebDriverManager.firefoxdriver().setup();
			driver=new FirefoxDriver();
		    }
		else {
			System.out.println("Please choose browser type");
		}
		
		maximizeBrowser();
		deleteCookies();
	}
	
	// maximize browser
	public void maximizeBrowser() {
		
		driver.manage().window().maximize();
	}
	
	//delete cockies
	public void deleteCookies() {
		
		driver.manage().deleteAllCookies();
	}
	
	//GmailLaunch URL
	public void gmaillaunchURL()
	{
		loadTestDataFile();
		driver.navigate().to(prop.getProperty("GmailURL"));
		BaseClass.sleep(2000);
		currentURL= driver.getCurrentUrl();
		BaseClass.sleep(3000);
		//return url= "gmailurl";
	}
	
	// launching url
	public String launchURL(String url) {
		loadTestDataFile();
		if(url.equalsIgnoreCase("stg")) {
			driver.navigate().to(prop.getProperty("SCSTGURL"));
	//		clickOnAdvanceSettingInBrowser();
			BaseClass.sleep(2000);
			currentURL= driver.getCurrentUrl();
			return urls="Stg";
		}
		else if(url.equalsIgnoreCase("prod")){
			driver.navigate().to(prop.getProperty("ProdURL"));
	//		clickOnAdvanceSettingInBrowser();
			BaseClass.sleep(2000);
			currentURL= driver.getCurrentUrl();
			return urls="Prod";
		}
		else {
			System.out.println("Please enter URL");
			return "Please enter URL";
		}
	}
	
	//Select method to select Text
	public static void selectByvisibletext(WebElement locators,String text) {
			
		select=new Select(locators);
		select.selectByVisibleText(text);
	}
	//Select method - to select by index
	public static void selectByIndex(WebElement locators,int indexNo) {
		select =new Select(locators);
		select.selectByIndex(indexNo);;
	}
	// Select method - to select by value
	public static void selectByValue(WebElement locators,String value) {
		select= new Select(locators);
		select.selectByValue(value);
			
	}
	public static String getFirstSelectOptions(WebElement locators) {
		select= new Select(locators);
		return select.getFirstSelectedOption().getText();
	}
	public static void implicitilyWait(WebDriver driver,int times) {
		
		driver.manage().timeouts().implicitlyWait(times, TimeUnit.SECONDS);
	}
	
	public static void sleep(int time ) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public static String[] splitXpath(String xpath) {
		String[] a = xpath.split(">");
		return a;
	}
	
	public void clickOnAdvanceSettingInBrowser() {
		
		boolean isAdvanceBtnDispay=advanceBtn.isDisplayed();
		try {
			if(isAdvanceBtnDispay==true) {
				advanceBtn.click();
				sleep(1000);
				proceedLink.click();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	/**
	 * Generating random alphanumeric string size up to 10 characters
	 * @return
	 */
	public static String generateRandomChars() {
		String candidateChars="ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
	    StringBuilder sb = new StringBuilder();
	    Random random = new Random();
	    for (int i = 0; i < 10; i++) {
	        sb.append(candidateChars.charAt(random.nextInt(candidateChars
	                .length())));
	    }
	    return sb.toString();
	}
	
	public static void GetScrollbyElements(WebElement ele)
	{
		try 
		{
			JavascriptExecutor js = (JavascriptExecutor) driver;
			 js.executeScript("arguments[0].scrollIntoView(true)", ele);
			 
			 
		} catch (Exception e)
		{
			System.out.println("Issue in GetScrollbyElements "+e);
		}
	}
	
	public static void ExplicitWait(WebElement ele)
	{
		try 
		{
			WebDriverWait wait = new WebDriverWait(driver, 10);
			WebElement element = wait.until(
			ExpectedConditions.visibilityOfElementLocated(By.id("someid")));
			 
			 
		} catch (Exception e)
		{
			System.out.println("Issue in GetScrollbyElements "+e);
		}
	}
	
	
	
	public static void Doubleclick(WebElement ele)
	{
		try 
		{
			Actions act=new Actions(driver);
			act.doubleClick(ele).perform();
			
		} catch (Exception e)
		{
			System.out.println("Issue in Doubleclick method "+e);
		}
	}
	public static void Rightclick(WebElement ele)
	{
		try 
		{
			Actions act=new Actions(driver);
			act.contextClick(ele).perform();
			
		} catch (Exception e)
		{
			System.out.println("Issue in Doubleclick method "+e);
		}
	}
	
	public static void SwitchTotab(int tabno)
	{
		try
		{
			ArrayList<String>tab=new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tab.get(tabno));
			
		} catch (Exception e) 
		{
			System.out.println("Issue in Switchtab method "+e);
		}
	}
	
	public static void Dropdownbyvalue(WebElement ele, String value)
	{
		try 
		{
			Select sel=new Select(ele);
			sel.selectByVisibleText(value);
		} catch (Exception e)
		{
			System.out.println("Issue in Dropdownvalue method "+e);
		}
	}
	
	public static void Dropdownbyindex(WebElement ele, int index)
	{
		try 
		{
			Select sel=new Select(ele);
			sel.selectByIndex(index);
		} catch (Exception e)
		{
			System.out.println("Issue in Dropdownvalue method "+e);
		}
	}
	public static void Dropdownbycontainsvalue(WebElement ele, String value)
	{
		try 
		{
			Select sel=new Select(ele);
			List<WebElement> list = sel.getOptions();
			for(WebElement dd:list)
			{
				String data = dd.getText();
				if(data.contains(value))
				{
					sel.selectByVisibleText(data);
					break;
				}
			}
		} catch (Exception e)
		{
			System.out.println("Issue in Dropdownvalue method "+e);
		}
	}
	
	public static void Slider(WebElement ele, int range)
	{
		try 
		{
			Actions act= new  Actions(driver);
			
			act.clickAndHold(ele).moveByOffset((60), 0).release().perform();
		} catch (Exception e) 
		{
			System.out.println("Issue in slider method "+e);
		}
	}

}
