package com.orangehrm.testbase;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.NoSuchElementException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.FileChooserUI;

import org.apache.commons.io.FileUtils;
import org.apache.poi.util.SystemOutLogger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.model.ITest;

public class TestBase {
	public WebDriver driver;
	public Properties OR;
	public File FL;
	public FileInputStream filedoc;
	//for reports
	
	public static ExtentReports extent;
	public static ExtentTest test;
	
	public ITestResult result;
	
	//for report generation
	static
	{
		Calendar calndrobj = Calendar.getInstance();
		SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
		extent =new ExtentReports(System.getProperty("user.dir")+"/src/main/java/com/orangehrm/report/test"+formater.format(calndrobj.getTime())+".html",false);
	}
	
	//method to launch the application irrespective of OS and Browser
	public void LaunchBrowser( String browser)
	{
		if(System.getProperty("os.name").contains("window"))
		{
			if(browser.equalsIgnoreCase("chrome"))
			{
				System.out.println(System.getProperty("user.dir"));
				System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/drivers/chromedriver.exe");
				driver=new ChromeDriver();
				
			}
		}
	}
	
//	this is loading the properties file
	public void loadPropertiesfile() throws IOException
	{
		System.out.println("this is loadfile");
			 OR = new Properties();
			 FL=new File(System.getProperty("user.dir")+"/src/main/java/com/orangehrm/config/setup.properties");
			 filedoc = new FileInputStream(FL);
			OR.load(filedoc);
			System.out.println(OR.getProperty("username"));
			System.out.println(OR.getProperty("baseurl"));
			//second property file we are loading
			 OR = new Properties();
			 FL=new File(System.getProperty("user.dir")+"/src/main/java/com/orangehrm/config/or.properties");
			 FileInputStream filedoc1 = new FileInputStream(FL);
			OR.load(filedoc1);
			System.out.println(OR.getProperty("username"));
		
	}
	//method to take screenshot
	public void takeScreenshot(String imageName) throws IOException{
		if (imageName.equals(""))
		{
			imageName="blank";
		}
	File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
	String imagelocation=System.getProperty("user.dir")+"/src/main/java/com/orangehrm/screenshot/";
	Calendar calndr = Calendar.getInstance();
	SimpleDateFormat formater = new SimpleDateFormat("dd_MM_yyyy_hh_mm_ss");
	String actualimageName=imagelocation+imageName+"_"+formater.format(calndr.getTime()+"png");
	File destinationfile=new File(actualimageName);
	FileUtils.copyFile(screenshot,destinationfile );
	
	}
	
	//wait without polling interval[explicit wait]
	public WebElement waitForElement(WebDriver driver,long timeoutsec,WebElement oElement)
	{
		WebDriverWait waitobj = new WebDriverWait(driver, timeoutsec);
		return waitobj.until(ExpectedConditions.elementToBeClickable(oElement));
		
	}
	
	
	//wait method with polling interval[explicit wait]
	public WebElement waitForElementwithPollingIntervel(WebDriver driver,long timeoutsec,WebElement oElement)
	{
		WebDriverWait waitobj = new WebDriverWait(driver, timeoutsec);
		waitobj.pollingEvery(5, TimeUnit.SECONDS);
		waitobj.ignoring(NoSuchElementException.class);
		return waitobj.until(ExpectedConditions.elementToBeClickable(oElement));
		
	}
	
	// implicit wait mehtod
	public void implicitWait(long timesec)
	{
		driver.manage().timeouts().implicitlyWait(timesec, TimeUnit.SECONDS);
	}
	//get the results
	public void getResult(ITestResult result)
	{
		if(result.getStatus()==ITestResult.SUCCESS)
		{
			test.log(LogStatus.PASS, result.getName()+"test is pass");
		}
			
		
	}
	//main method need to delete
	public static void main(String[] args) throws IOException
	{
		TestBase base=new TestBase();
		//base.LaunchBrowser("chrome");
		base.loadPropertiesfile();
		System.out.println(base.OR.getProperty("username"));
		System.out.println(base.OR.getProperty("baseurl"));
		System.out.println(base.OR.getProperty("testcase"));
		
	}

}
