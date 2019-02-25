package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestText {
	WebDriver driver;

	@Test
	public void f() {
		driver.get("http://localhost/upload/index.php");
		driver.findElement(By.id("keyword")).sendKeys("a");
		driver.findElement(By.name("imageField")).click();
		// 断言个数是4
		WebElement element = driver.findElement(By
				.xpath("//form[@name='selectPageForm']//b"));
		Utils.assertText(element, "4");
	}

	@Test
	public void f1() {
		driver.get("http://localhost/upload/index.php");
		WebElement element = driver.findElement(By.id("ECS_MEMBERZONE"));
		Utils.assertContainsText(element, "欢迎光临本店", "欢迎");
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
