package flightreservation;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import io.github.bonigarcia.wdm.WebDriverManager;
import pages.flightreservations.FlightConfirmationPage;
import pages.flightreservations.FlightSearchPage;
import pages.flightreservations.FlightSelectionPage;
import pages.flightreservations.RegistrationCofirmationPage;
import pages.flightreservations.RegistrationPage;

public class FlightReservationTest {
	
	private WebDriver driver;
	private String noOfPassengers;
	private String expectedPrice;
	
	@BeforeTest
	@Parameters({"noOfPassengers", "expectedPrice"})
	public void setDriver(String noOfPassengers, String expectedPrice) {
		this.noOfPassengers = noOfPassengers;
		this.expectedPrice = expectedPrice;
		WebDriverManager.chromedriver().setup();
		//WebDriverManager.chromedriver().clearDriverCache().setup();
		this.driver = new ChromeDriver();
		this.driver.manage().window().maximize();
	}
	
	@Test
	public void  userRegistrationTest() {
		RegistrationPage registrationPage = new RegistrationPage(driver);
		registrationPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/reservation-app/index.html");
		Assert.assertTrue(registrationPage.isAt());
		registrationPage.enterUserDetails("umesh", "deshmukh");
		registrationPage.enterUserCredentials("test", "test123");
		registrationPage.enterAddress("123 street", "Pune", "222123");
		registrationPage.register();
	}

	@Test(dependsOnMethods = "userRegistrationTest")
	public void registrationConfirmationTest() {
		RegistrationCofirmationPage registrationConfirmationPage = new RegistrationCofirmationPage(driver);
		Assert.assertTrue(registrationConfirmationPage.isAt());
		registrationConfirmationPage.goToFlightSearch();
	}
	
	@Test(dependsOnMethods = "registrationConfirmationTest")
	public void flightSearchTest() {
		FlightSearchPage flightSearchPage = new FlightSearchPage(driver);
		Assert.assertTrue(flightSearchPage.isAt());
		flightSearchPage.selectPassengers(this.noOfPassengers);
		flightSearchPage.searchFlights();
	}
	
	@Test(dependsOnMethods = "flightSearchTest")
	public void flightSelectionTest() {
		FlightSelectionPage flightSelectionTest = new FlightSelectionPage(driver);
		Assert.assertTrue(flightSelectionTest.isAt());
		flightSelectionTest.selectFlights();
		flightSelectionTest.confirmFlights();
	}
	
	@Test(dependsOnMethods = "flightSelectionTest")
	public void flightReservationConfirmationTest() {
		FlightConfirmationPage flightConfirmationPage = new FlightConfirmationPage(driver);
		Assert.assertTrue(flightConfirmationPage.isAt());
		Assert.assertEquals(flightConfirmationPage.getPrice(), this.expectedPrice);
	}
	
	@AfterTest
	public void quitDriver() {
		this.driver.quit();
	}
}
