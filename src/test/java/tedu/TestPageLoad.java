package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestPageLoad {
	WebDriver driver;

	@Test
	public void f() {
		driver.get("file:///D:/example1/link.html");
		// driver.findElement(By.partialLinkText("id")).click();
		// Utils.waitForPageLoad30();
		WebElement e1 = driver.findElement(By.partialLinkText("id"));
		Utils.clickAndWait(e1);
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
