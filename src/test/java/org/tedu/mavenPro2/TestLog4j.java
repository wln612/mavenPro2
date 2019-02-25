package org.tedu.mavenPro2;


import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;


import utils.Log;
@Test
public class TestLog4j {
	WebDriver driver;
	
  public void f() {
	  driver.get("http://baidu.com");
	  Log.info("Baidu page is loaded");
	  try {
		Thread.sleep(3000);
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
		Log.error("Catched InterruptedException");
		
	}
  }
  @BeforeMethod
  public void beforeMethod() {
	  Log.info("Try to open browser");
	  System.setProperty("webdriver.firefox.bin", "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
	  driver=new FirefoxDriver();
	  Log.info("Browser if opend");
  }

  @AfterMethod
  public void afterMethod() {
	  Log.info("Try to close browser");
	  driver.quit();
	  Log.info("Browser is closed");
  }

}
