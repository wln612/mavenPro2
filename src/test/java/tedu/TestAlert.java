package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestAlert {
	WebDriver driver;

	@Test
	public void f() {
		driver.get("file:///D:/example/alert.html");
		Utils.assertAlertNotPresent();
		driver.findElement(By.id("a1")).click();
		Utils.assertAlertPresent();
		Utils.asserAlertText("Welcome!)");
		driver.findElement(By.id("c1")).click();
		Utils.asserAlertText("Press a button");
		Utils.sleep(3000);
	}

	@Test
	public void f1() {
		driver.get("file:///D:/example/alert.html");
		driver.findElement(By.id("c1")).click();
		Utils.assertAlertAndDismiss("Press a button");
		Utils.sleep(3000);
	}

	@Test
	public void f2() {
		driver.get("file:///D:/example/alert.html");
		driver.findElement(By.id("a1")).click();
		Utils.assertAlertContainsText("Wel", "come", "!");
	}

	@Test
	public void f3() {
		driver.get("file:///D:/example/alert.html");
		driver.findElement(By.id("c1")).click();
		Utils.assertAlertContainsTextAndDismiss("Press", "button");
		Utils.sleep(2000);
	}

	@Test
	public void f4() {
		driver.get("http://localhost/upload/user.php");
		driver.findElement(By.name("submit")).click();
		Utils.assertAlertContainsText("用户名不能为空", "登录密码不能为空");
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
