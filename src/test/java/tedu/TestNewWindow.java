package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestNewWindow {
	WebDriver driver;

	@Test
	public void f() {
		driver.get("file:///D:/example/newWindow.html");
		String hd1 = driver.getWindowHandle();
		driver.findElement(By.partialLinkText("3")).click();
		Utils.switchWindow("windName");
		driver.findElement(By.name("username")).sendKeys("123456");
		Utils.sleep(2000);
		driver.close();
		Utils.switchWindow(hd1);
		System.out.println(driver.getTitle());
	}

	@Test
	public void f1() {
		driver.get("file:///D:/example/newWindow.html");
		driver.findElement(By.partialLinkText("2")).click();
		Utils.switchWindow();
		driver.findElement(By.id("username")).sendKeys("jack");
		Utils.sleep(2000);
		Utils.switchWindow();
		System.out.println(driver.getTitle());
	}

	@Test
	public void f2() {
		driver.get("file:///D:/example/newWindow.html");
		driver.findElement(By.partialLinkText("2")).click();
		driver.findElement(By.partialLinkText("3")).click();
		Utils.switchWindow(1, "id");
		driver.findElement(By.id("username")).sendKeys("rose");
		Utils.sleep(2000);
		Utils.switchWindow(3, "3");
		System.out.println(driver.getTitle());
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
