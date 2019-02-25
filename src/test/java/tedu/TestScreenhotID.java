package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Log;
import utils.Utils;

public class TestScreenhotID {
	WebDriver driver;

	@Test
	public void f() {
		driver.get("file:///D:/example1/id.html");
		try {
			driver.findElement(By.id("abcde")).click();
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
			String sTestCaseName = this.getClass().getName();
			Utils.takeScreenShot(sTestCaseName);
		}

	}

	@BeforeMethod
	public void beforeMethod() {
		driver = Utils.openBrowser("firefox");
	}

	@AfterMethod
	public void afterMethod() {
	}

}
