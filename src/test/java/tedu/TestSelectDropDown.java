package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestSelectDropDown {
	WebDriver driver;

	@Test
	public void f() {
		driver.get("file:///D:/example/select.html");
		WebElement e1 = driver.findElement(By.id("menus_navlist"));
		Utils.selectDropDown(e1, "byvisibletext", "用户评论");
		Utils.sleep(3000);
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
