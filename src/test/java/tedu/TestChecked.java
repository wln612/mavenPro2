package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestChecked {
	WebDriver driver;

	@Test
	public void f() {
		driver.get("file:///D:/example/check.html");
		WebElement e = driver.findElement(By.id("m1"));
		Utils.assertChecked(e);
		WebElement e2 = driver.findElement(By.id("m2"));
		Utils.assertNotChecked(e2);
	}

	@BeforeMethod
	public void beforeMethod() {
		driver = Utils.openBrowser("firefox");
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
