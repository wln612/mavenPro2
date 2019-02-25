package utils;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoAlertPresentException;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchFrameException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Utils {
	public static WebDriver driver;
	// 是否接受弹框中的要求，默认值true代表接受，就是点击“确定”或“是”
	public static boolean acceptNextAlert = true;

	// 打开浏览器
	public static WebDriver openBrowser(String browser) {
		try {
			if (browser.equalsIgnoreCase("firefox")) {
				FirefoxProfile profile = new FirefoxProfile();
				// 不要禁用混合内容
				profile.setPreference(
						"security.mixed_content.block_active_content", false);
				profile.setPreference(
						"security.mixed_content.block_display_content", true);
				// 不显示下载管理器
				profile.setPreference(
						"browser.download.manager.showWhenStarting", false);
				// 指定下载管理的类型
				profile.setPreference("browser.helperApps.neverAsk.saveDisk",
						"application/octet-stream,application/vnd.ms-excel,text/csv,application/zip");
				// 指定默认下载到自定义的文件夹
				profile.setPreference("browser.download.folderList", 2);
				profile.setPreference("browser.download",
						Constants.DOWNLOAD_PATH);

				// 启动firefox
				System.setProperty("webdriver.firefox.bin",
						"D:\\Program Files (x86)\\Mozilla Firefox\\firefox.exe");
				driver = new FirefoxDriver(profile);
			} else if (browser.equalsIgnoreCase("ie")) {
				DesiredCapabilities ieCapabilities = DesiredCapabilities
						.internetExplorer();
				ieCapabilities.setCapability("nativeEvents", true);
				ieCapabilities.setCapability("unexpectedAlertBehaviour",
						"accept");
				ieCapabilities.setCapability("ignoreProtectedModeSettings",
						true);
				ieCapabilities.setCapability("disable-popup-blocking", true);
				ieCapabilities
						.setCapability(
								InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,
								true);
				ieCapabilities.setCapability("requireWindowFocus", false);
				ieCapabilities.setCapability("enablePersistenHover", false);

				System.setProperty("webdriver.ie.driver", Constants.IE_DRIVER);
				driver = new InternetExplorerDriver(ieCapabilities);
			} else if (browser.equalsIgnoreCase("chrome")) {
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability("chrome.switches",
						Arrays.asList("--incognito"));
				ChromeOptions options = new ChromeOptions();
				options.addArguments("--test-type");
				options.addArguments("enable-automation");
				options.addArguments("--disable-infobars");
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);

				System.setProperty("webdriver.chrome.driver",
						Constants.CHROME_DRIVER);
				driver = new ChromeDriver(capabilities);
			} else {
				Log.error("Invalid browser type" + browser);
			}

			Log.info("Browser is opened,Type is" + browser);
			// Maximize
			driver.manage().window().maximize();
			// Set wait time
			driver.manage().timeouts()
					.implicitlyWait(Constants.WAIT_TIME, TimeUnit.SECONDS);
			driver.manage().timeouts()
					.pageLoadTimeout(Constants.WAIT_TIME, TimeUnit.SECONDS);
			driver.manage().timeouts()
					.setScriptTimeout(Constants.WAIT_TIME, TimeUnit.SECONDS);
			return driver;
		} catch (Exception e) {
			Log.error("Unable to open browser");
			Log.error(e.getMessage());
			return null;
		}
	}

	/**
	 * 截图
	 * 
	 * @param sTestCaseName
	 *            测试用例名称
	 */
	public static void takeScreenShot(String sTestCaseName) {
		DateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd hh_mm_ss");
		Date date = new Date();
		File file = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File destFile = new File(Constants.SCREENSHOT + "\\" + sTestCaseName
				+ "\\" + sTestCaseName + " #" + dateformat.format(date)
				+ ".png");
		try {
			FileUtils.copyFile(file, destFile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.error(e.getMessage());
		}
	}

	/**
	 * 等待固定毫秒数
	 * 
	 * @param time
	 */
	public static void sleep(long time) {
		try {
			Thread.sleep(time);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log.error("The sleep thread is interrupted!");
		}
	}

	/**
	 * 等待网页加载完毕
	 * */
	public static void waitForPageLoad() {
		String js = "return document.readyState;";
		while (!"complete".equals((String) ((JavascriptExecutor) driver)
				.executeScript(js))) {
			Log.info("page is loading.....");
			sleep(1000);
		}
		Log.info("The page is loaded...");
	}

	/**
	 * 在30秒内等待网页加载完毕 *
	 */
	public static void waitForPageLoad30() {
		boolean flag = false;
		String js = "return document.readyState;";
		for (int i = 0; i <= 29; i++) {
			if ("complete".equals((String) ((JavascriptExecutor) driver)
					.executeScript(js))) {
				flag = true;
				Log.info("Page is loaded in" + i + "seconds");
				break;
			}
			sleep(1000);
			if (!flag) {
				fail();
				Log.error("Page is not loaded in 30 seconds!");
			}
		}
	}

	/**
	 * 显式等待，等待网页标题包含预期字符串 *
	 */
	public static void explictWaitTitle(final String title) {
		WebDriverWait wait = new WebDriverWait(driver,
				Constants.EXPLICT_WAIT_TIME);
		try {
			wait.until(new ExpectedCondition<Boolean>() {
				@Override
				public Boolean apply(WebDriver d) {
					return d.getTitle().toLowerCase()
							.contains(title.toLowerCase());
				}
			});
		} catch (TimeoutException e) {
			Log.error("Tile in not correct");
			throw new IllegalStateException(driver.getTitle());
		}
	}

	/**
	 * 获得页面元素的状态
	 * 
	 * @param WebElement
	 * 
	 * @return boolean*
	 */
	public static boolean getElementStatus(WebElement element) {
		if (!element.isDisplayed()) {
			Log.error("The element is not displayed." + element.toString());
		} else if (!element.isEnabled()) {
			Log.error("The element is not enabled." + element.toString());
		}
		return element.isDisplayed() && element.isEnabled();
	}

	/**
	 * 选择下拉框的选项
	 * 
	 * @param flag
	 *            byvalue --通过value选择属性 byindex --通过index选择属性 byvisibletext
	 *            --通过文本选择属性
	 * 
	 * @param data
	 *            选项的具体数值
	 */
	public static void selectDropDown(WebElement element, String flag,
			String data) {
		if (getElementStatus(element)) {
			Select select = new Select(element);
			Log.info("Select options in drop down list" + flag);
			if (flag.equalsIgnoreCase("byindex")) {
				select.selectByIndex(Integer.parseInt(data));
			} else if (flag.equalsIgnoreCase("byvalue")) {
				select.selectByValue(data);
			} else {
				select.selectByVisibleText(data);
			}
			Log.info(data + "is selected in drop list: " + element.toString());
		}
	}

	/**
	 * 点击页面元素
	 * 
	 * @param element
	 */
	public static void click(WebElement element) {
		try {
			if (getElementStatus(element)) {
				element.click();
				Log.info("The element is clicked.");
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error("Method [click]" + e.getMessage());
		}
	}

	/**
	 * 点击页面元素，并且等待新网页加载完毕
	 * 
	 * @param element
	 */
	public static void clickAndWait(WebElement element) {
		click(element);
		waitForPageLoad();
	}

	/**
	 * 向指定的页面元素输入数据
	 * 
	 * @param element
	 * 
	 * @param value
	 */
	public static void inputValue(WebElement element, String value) {
		try {
			if (getElementStatus(element)) {
				if (value != null) {
					element.clear();
					element.sendKeys(value);
					Log.info(value + "is input element");
				} else {
					Log.error("value is null!");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			Log.error(e.getMessage());
		}
	}

	/**
	 * 切换到默认
	 */
	public static void switchToDefault() {
		driver.switchTo().defaultContent();
		Log.info("Switched to default content.");
	}

	/**
	 * 使用id或name属性值来切换frame
	 * 
	 * @param idOrName
	 */
	public static void switchFrame(String idOrName) {
		try {
			switchToDefault();
			driver.switchTo().frame(idOrName);
			Log.info("switched to frame,id or name" + idOrName);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
			Log.error("No frame to switch");
		}
	}

	/**
	 * 通过编号来切换frame
	 * 
	 * @param index
	 */
	public static void switchFrame(int index) {
		try {
			switchToDefault();
			driver.switchTo().frame(index);
			Log.info("switched to frame,index" + index);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
			Log.error("No frame to switch");
		}
	}

	/**
	 * 通过其它方式来定位Frame，再切换frame
	 * 
	 * @param frameElement
	 */
	public static void switchFrame(WebElement frameElement) {
		try {
			switchToDefault();
			driver.switchTo().frame(frameElement);
			Log.info("switched to frame" + frameElement);
		} catch (NoSuchFrameException e) {
			e.printStackTrace();
			Log.error("No frame to switch");
		}
	}

	/**
	 * 通过网页内容包含指定字符串来切换frame
	 * 
	 * @param pageSource
	 */
	public static void switchFrameByPageSource(String pageSource) {
		switchToDefault();
		List<WebElement> frames = driver.findElements(By.tagName("frame"));
		if (frames.size() > 0) {
			boolean flag = false;
			for (int i = 0; i < frames.size(); i++) {
				switchFrame(i);
				Log.info("switch to frame" + i);
				if (driver.getPageSource().contains(pageSource)) {
					flag = true;
					break;
				}
			}
			if (flag) {
				Log.info("Switch to frame contains" + pageSource);
			} else {
				Log.error("No frame contains" + pageSource);
			}
		} else {
			Log.error("No frame to switch");
		}
	}

	/**
	 * 通过窗口名称或句柄来切换窗口
	 * 
	 * @param nameOrHdl
	 */
	public static void switchWindow(String nameOrHdl) {
		try {
			driver.switchTo().window(nameOrHdl);

		} catch (NoSuchWindowException e) {
			e.printStackTrace();
			Log.error("The window cannot be found:" + nameOrHdl);
		}
	}

	/**
	 * 切换到另一个窗口，只适合有两个窗口的情况
	 */
	public static void switchWindow() {
		String originalWinHandle = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		if (allWindows.size() == 2) {
			Iterator<String> it = allWindows.iterator();
			while (it.hasNext()) {
				String currentWindow = it.next();
				if (!currentWindow.endsWith(originalWinHandle)) {
					driver.switchTo().window(currentWindow);

				}
			}
			Log.info("switch to new window");
		} else {
			Log.error("There are not two windows");
		}
	}

	/**
	 * 切换到符合指定条件的新窗口
	 * 
	 * @param type
	 *            1：ByTitle包含信息 2：ByURL包含信息 3：ByPageSource包含信息
	 * 
	 * @param value
	 *            要包含的信息
	 */
	public static void switchWindow(int type, String value) {
		String originalWindowHandle = driver.getWindowHandle();
		Set<String> allWindows = driver.getWindowHandles();
		if (allWindows.size() > 1) {
			boolean flag = false;// 未切换到目标窗口
			Iterator<String> it = allWindows.iterator();
			while (it.hasNext()) {
				String currentWindow = it.next();
				if (!currentWindow.equals(originalWindowHandle)) {
					driver.switchTo().window(currentWindow);
					String currentValue;
					switch (type) {
					case 1:
						currentValue = driver.getTitle();
						Log.info("Switch window by title value");
						break;
					case 2:
						currentValue = driver.getCurrentUrl();
						Log.info("Switch window by url value");
						break;
					default:
						currentValue = driver.getPageSource();
						Log.info("Switch window by pagesource value");
						break;
					}
					if (currentValue.contains(value)) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				Log.info("Switched to target window");
			} else {
				Log.error("Can not find target window contains" + value);
			}
		} else {
			Log.error("Only one window,no other windows.");
		}
	}

	/**
	 * 判断页面元素是否存在
	 * 
	 * @param by
	 *            条件
	 * 
	 * @return true 代表存在 false 代表不存在
	 */
	public static boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			Log.info("Can find the element.");
			return true;
		} catch (NoSuchElementException e) {
			e.printStackTrace();
			Log.error("Can not find the element.");
			return false;
		}
	}

	/**
	 * 断言页面元素出现|存在
	 * 
	 * @param by
	 */
	public static void assertElementPresent(By by) {
		assertTrue(isElementPresent(by));
	}

	/**
	 * 断言页面元素不出现|不存在
	 * 
	 * @param by
	 */
	public static void assertElementNoPresent(By by) {
		assertFalse(isElementPresent(by));
	}

	/**
	 * 判断是否出现弹框
	 * 
	 * @return true 代表出现 false代表未出现或消失
	 */
	public static boolean isAlertPresent() {
		try {
			driver.switchTo().alert();
			Log.info("Can find alert.");
			return true;
		} catch (NoAlertPresentException e) {
			e.printStackTrace();
			Log.error("Can not find alert.");
			return false;
		}
	}

	/**
	 * 断言弹框出现|存在
	 */
	public static void assertAlertPresent() {
		assertTrue(isAlertPresent());
	}

	/**
	 * 断言弹框不出现|不存在|消失
	 */
	public static void assertAlertNotPresent() {
		assertFalse(isAlertPresent());
	}

	/**
	 * 获得弹框的内容，并且关闭弹框 默认点击“确定”或“是” 如果点击”取消“或者”否“,先给acceptNextAlert赋值false，再调用该方法
	 * 
	 * @return 弹框文本的内容
	 */
	public static String closeAlertAndGetItsText() {
		try {
			Alert alert = driver.switchTo().alert();
			String alertText = alert.getText();
			if (acceptNextAlert) {
				Log.info("Accept the alert.");
				alert.accept();
			} else {
				Log.info("Dismiss the alert.");
				alert.dismiss();
			}
			Log.info("The text in the alert is:" + alertText);
			return alertText;
		} finally {
			acceptNextAlert = true;
		}
	}

	/**
	 * 断言弹框文本等于预期值，并且通过点击“确定”或“是”来关闭弹框
	 * 
	 * @param expTxt
	 */
	public static void asserAlertText(String expTxt) {
		try {
			String alertText = closeAlertAndGetItsText();
			assertEquals(alertText, expTxt);
		} catch (AssertionError e) {
			e.printStackTrace();
			Log.error("Alert text is not equals to expected text.");
		}
	}

	/**
	 * 断言弹框文本等于预期值，并且通过点击“取消”或“否”来关闭弹窗
	 * 
	 * @param expTxt
	 */
	public static void assertAlertAndDismiss(String expText) {
		acceptNextAlert = false;
		asserAlertText(expText);

	}

	/**
	 * 断言弹框中的实际文本中包含预期的文本字符串
	 * 
	 * @param expTexts
	 */
	public static void assertAlertContainsText(String... expTexts) {
		// 获得实际弹框文本并且关闭弹框
		String actText = closeAlertAndGetItsText();
		for (String expText : expTexts) {
			try {
				assertTrue(actText.contains(expText));
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("Actual alert text:【" + actText
						+ "】does not contains expText【" + expText + "】.");
			}
		}
	}

	/**
	 * 断言弹框中的实际文本中包含预期的文本字符串,并且点击取消或否来关闭弹框
	 * 
	 * @param expTexts
	 */
	public static void assertAlertContainsTextAndDismiss(String... expTexts) {
		acceptNextAlert = false;
		assertAlertContainsText(expTexts);
	}

	/**
	 * 断言元素中的文本等于预期值
	 * 
	 * @param element
	 * @param expText
	 */
	public static void assertText(WebElement element, String expText) {
		if (getElementStatus(element)) {
			if (expText != null) {
				try {
					assertEquals(element.getText(), expText);
				} catch (AssertionError e) {
					e.printStackTrace();
					Log.error(e.getMessage());
				}
			} else {
				Log.error("Expected text:" + expText + "is null");
			}
		}
	}

	/**
	 * 断言元素的文本包含指定的那些预期文本字符串
	 * 
	 * @param element
	 * @param expTexts
	 * */
	public static void assertContainsText(WebElement element,
			String... expTexts) {
		if (getElementStatus(element)) {
			for (String expText : expTexts) {
				try {
					assertTrue(element.getText().contains(expText));
				} catch (AssertionError e) {
					e.printStackTrace();
					Log.error("Actual element text[" + element.getText()
							+ "]does not contains expected text:[" + expText
							+ "].");
					fail("Actual element text[" + element.getText()
							+ "]does not contains expected text:[" + expText
							+ "].");
				}
			}
		}
	}

	/**
	 * 断言value属性值等于预期值
	 * 
	 * @param element
	 * @param expValue
	 */
	public static void assertValue(WebElement element, String expValue) {
		assertAttribute(element, "value", expValue);

	}

	/**
	 * 断言指定属性值等于预期值
	 * 
	 * @param element
	 * @param attributeName
	 * @param expValue
	 */
	public static void assertAttribute(WebElement element,
			String attributeName, String expValue) {
		if (getElementStatus(element)) {
			if (expValue != null) {
				try {
					assertEquals(element.getAttribute(attributeName), expValue);
				} catch (AssertionError e) {
					e.printStackTrace();
					Log.error(e.getMessage());
					fail(e.getMessage());
				}
			} else {
				Log.error("Expected value:[" + expValue + "]is null.");
				fail("Expected value:[" + expValue + "]is null.");
			}
		}
	}

	/**
	 * 断言文本框内容包含指定的一些预期字符串
	 * 
	 * @param element
	 * @param expValues
	 * */
	public static void assertContainsValue(WebElement element,
			String... expValues) {
		assertContainsAttribute(element, "value", expValues);
	}

	/**
	 * 断言指定属性值包含指定的一些预期字符串
	 * 
	 * @param element
	 * @param attributeName
	 * @param expValues
	 * */
	public static void assertContainsAttribute(WebElement element,
			String attributeName, String... expValues) {
		if (getElementStatus(element)) {
			for (String expValue : expValues) {
				try {
					assertTrue(element.getAttribute(attributeName).contains(
							expValue));
				} catch (AssertionError e) {
					e.printStackTrace();
					Log.error("Actual attribute[" + attributeName
							+ "]value does not contains expected value"
							+ expValue + "].");
					fail("Actual attribute[" + attributeName
							+ "]value does not contains expected value"
							+ expValue + "].");
				}
			}
		}
	}

	/**
	 * 断言单选框或复选框被选中
	 * 
	 * @param element
	 * */
	public static void assertChecked(WebElement element) {
		if (getElementStatus(element)) {
			try {
				assertTrue(element.isSelected());
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("The checkbox or radiobutton is not checked!");
				fail("The checkbox or radiobutton is not checked!");
			}
		}
	}

	/**
	 * 断言单选框或复选框未被选中
	 * 
	 * @param element
	 * */
	public static void assertNotChecked(WebElement element) {
		if (getElementStatus(element)) {
			try {
				assertFalse(element.isSelected());
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("The checkbox or radiobutton is  checked!");
				fail("The checkbox or radiobutton is  checked!");
			}
		}
	}

	/**
	 * 断言下拉框或列表框的当前选项等于预期选项 一般适用于只要一个选项被选中的情况
	 * 
	 * @param element
	 * @param expOption
	 */
	public static void assertSelectedOption(WebElement element, String expOption) {
		if (getElementStatus(element)) {
			try {
				String actOption = new Select(element).getFirstSelectedOption()
						.getText();
				assertEquals(actOption, expOption);
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error(e.getMessage());
				fail(e.getMessage());
			}
		}
	}

	/**
	 * 断言下拉框或列表框中的所有选项中包含一些预期文本
	 * 
	 * @param element
	 * @param expOptions
	 * */
	public static void assertOptionsContains(WebElement element,
			String... expOptions) {
		if (getElementStatus(element)) {
			// 遍历每个要检查存在的预期选项文本
			for (String expOption : expOptions) {
				// 默认标记没有找到
				boolean flag = false;
				// 遍历所有选项
				for (WebElement option : new Select(element).getOptions()) {
					String actOption = option.getText();
					if (actOption.contains(expOption)) {
						flag = true;// 找到了
						break;
					}
				}
				try {
					assertTrue(flag);
				} catch (AssertionError e) {
					e.printStackTrace();
					Log.error("All options does not contains expected option:"
							+ expOption);
					fail(e.getMessage());
				}
			}
		}
	}

	/**
	 * 断言下拉框或列表框可以多选
	 * 
	 * @param element
	 * */
	public static void assertMultiple(WebElement element) {
		if (getElementStatus(element)) {
			try {
				assertTrue(new Select(element).isMultiple());
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("The drop down lis is not multiple.");
				fail(e.getMessage());
			}
		}
	}

	/**
	 * 断言下拉框或列表框不可以多选
	 * 
	 * @param element
	 * */
	public static void assertNotMultiple(WebElement element) {
		if (getElementStatus(element)) {
			try {
				assertFalse(new Select(element).isMultiple());
			} catch (AssertionError e) {
				e.printStackTrace();
				Log.error("The drop down lis is  multiple.");
				fail(e.getMessage());
			}
		}
	}

	public static void assertSelectedContains(WebElement element,
			String... expOptions) {
		if (getElementStatus(element)) {
			boolean flag = false;
			for (String expOption : expOptions) {
				// 遍历所有被选中的选项
				for (WebElement option : new Select(element)
						.getAllSelectedOptions()) {
					String actOption = option.getText();
					if (actOption.contains(expOption)) {
						flag = true;
						break;
					}
				}
				try {
					assertTrue(flag);
				} catch (AssertionError e) {
					e.printStackTrace();
					Log.error("Selected options does not contains :"
							+ expOption);
					fail("Selected options does not contains :" + expOption);
				}
			}
		}
	}

	/**
	 * 断言下拉框的所有选项的个数正确
	 * 
	 * @param element
	 * @param expCount
	 */
	public static void assertOptionsCount(WebElement element, int expCount) {
		if (getElementStatus(element)) {
			int actCount = new Select(element).getOptions().size();
			try {
				assertEquals(actCount, expCount);
			} catch (AssertionError e) {
				e.getStackTrace();
				Log.error(e.getMessage());
				fail(e.getMessage());
			}
		}
	}

	/**
	 * 断言下拉框当前被选中的选项的个数正确
	 * 
	 * @param element
	 * @param expCount
	 */
	public static void assertSelectedOptionsCount(WebElement element,
			int expCount) {
		if (getElementStatus(element)) {
			int actCount = new Select(element).getAllSelectedOptions().size();
			try {
				assertEquals(actCount, expCount);
			} catch (AssertionError e) {
				e.getStackTrace();
				Log.error(e.getMessage());
				fail("All selected options count:" + e.getMessage());
			}
		}
	}
}
