package tests.flightreservation;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import pages.flightreservations.FlightConfirmationPage;
import pages.flightreservations.FlightSearchPage;
import pages.flightreservations.FlightSelectionPage;
import pages.flightreservations.RegistrationCofirmationPage;
import pages.flightreservations.RegistrationPage;
import tests.AbstractTest;

public class FlightReservationTest extends AbstractTest{
	
	private String noOfPassengers;
	private String expectedPrice;
	
	@BeforeTest
	@Parameters({"noOfPassengers", "expectedPrice"})
	public void setParameters(String noOfPassengers, String expectedPrice) {
		this.noOfPassengers = noOfPassengers;
		this.expectedPrice = expectedPrice;
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
	
}
