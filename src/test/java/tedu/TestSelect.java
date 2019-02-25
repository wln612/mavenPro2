package tedu;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import utils.Utils;

public class TestSelect {
	WebDriver driver;

	@Test
	public void f() {
		driver.get("file:///D:/example/select.html");

		// 断言列表框可以多选
		WebElement nl = driver.findElement(By.id("menus_navlist"));
		Utils.assertMultiple(nl);
		// 断言当前默认选项是否等于“所有品牌”
		WebElement brand = driver.findElement(By.id("brand"));
		// 断言下拉框不可以多选
		Utils.assertNotMultiple(brand);
		Utils.assertSelectedOption(brand, "所有品牌");
		// 断言所有品牌包含“联想”和“三星”
		Utils.assertOptionsContains(brand, "联想", "三星");

	}

	@Test
	public void f1() {
		driver.get("file:///D:/example/select.html");
		WebElement n1 = driver.findElement(By.id("menus_navlist"));
		Utils.selectDropDown(n1, "byindex", "1");
		Utils.selectDropDown(n1, "byindex", "3");
		Utils.selectDropDown(n1, "byindex", "4");
		Utils.sleep(2000);
		// 断言当前被选中的下拉框选项中包含“会员列表”和“订单”
		Utils.assertSelectedContains(n1, "会员列表", "订单");
		// 断言列表框选项个数是5
		Utils.assertOptionsCount(n1, 5);
		// 断言列表被选中的选项个数是3
		Utils.assertSelectedOptionsCount(n1, 3);
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
