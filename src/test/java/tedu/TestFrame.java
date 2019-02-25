package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestFrame {
	WebDriver driver;

	@Test
	public void f() {
	}

	@BeforeMethod
	public void beforeMethod() {
		driver = Utils.openBrowser("firefox");
		driver.get("file:///D:/example/main1.html");
		// WebElement w1 = driver.findElement(By.name("f1"));
		// WebElement w2 = driver.findElement(By.name("f2"));
		// Utils.switchFrame(w1);
		Utils.switchFrameByPageSource("input1");
		Utils.inputValue(driver.findElement(By.id("input1")), "123456");
		// Utils.switchToDefault();
		// Utils.switchFrame(w2);
		Utils.switchFrameByPageSource("input2");
		Utils.inputValue(driver.findElement(By.id("input2")), "123456");
	}

	@AfterMethod
	public void afterMethod() {
		driver.quit();
	}

}
