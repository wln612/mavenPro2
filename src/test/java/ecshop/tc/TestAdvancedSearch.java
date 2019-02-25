package ecshop.tc;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import utils.BaseTest;
import utils.ReadFile;
import ecshop.page.AdvancedSearchPage;
import ecshop.page.SearchResultPage;

public class TestAdvancedSearch extends BaseTest {
	// WebDriver driver;
	AdvancedSearchPage asp;
	SearchResultPage srp;

	@Test(dataProvider = "dp")
	public void f(String kw,// 关键字
			String cg,// 分类
			String bd,// 品牌
			String minp,// 最小价格
			String maxp,// 最大价格
			String ext,// 扩展选项
			String dt,// 上市日期
			String color,// 颜色
			String expCount// 预期结果个数
	) throws InterruptedException {
		asp = new AdvancedSearchPage(driver);
		asp.get();
		srp = asp.advancedSearch(kw, cg, bd, minp, maxp, ext, dt, color);
		String actCount = srp.getCount();
		assertEquals(actCount, expCount);
	}

	// @BeforeMethod
	// @Parameters({ "browser" })
	// public void beforeMethod(String b) {
	// System.setProperty("webdriver.firefox.bin",
	// "D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
	// // driver=new FirefoxDriver();
	// driver = Utils.openBrowser(b);
	// asp = new AdvancedSearchPage(driver);
	//
	// }
	//
	// @AfterMethod
	// public void afterMethod() {
	// driver.quit();
	// }

	@DataProvider
	public Object[][] dp() {
		// return ReadFile.getTestDataFromExcel("C:\\", "数据_ECShop_高级搜索.xlsx",
		// "高级搜索");
		return ReadFile.getTestDataFromCVSFile("C:\\", "数据_ECShop_高级搜索.csv");
	}
}
