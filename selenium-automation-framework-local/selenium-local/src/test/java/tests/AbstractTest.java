package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import io.github.bonigarcia.wdm.WebDriverManager;

public abstract class AbstractTest {

	protected WebDriver driver;
	
	@BeforeTest
	public void setDriver() {
		WebDriverManager.chromedriver().setup();
		//WebDriverManager.chromedriver().clearDriverCache().setup();
		this.driver = new ChromeDriver();
		this.driver.manage().window().maximize();
	}
	
	@AfterTest
	public void quitDriver() {
		this.driver.quit();
	}
}
