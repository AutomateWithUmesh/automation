package tests.vendorportal;

import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import pages.vendorportal.DashboardPage;
import pages.vendorportal.LoginPage;
import tests.AbstractTest;

public class VendorPortalTest extends AbstractTest{
	
	private LoginPage loginPage;
	private DashboardPage dashboardPage;
	
	@BeforeTest
	public void setPageObjects() {
		this.loginPage = new LoginPage(driver);
		this.dashboardPage = new DashboardPage(driver);
	}
	
	@Test
	public void loginTest() {
		loginPage.goTo("https://d1uh9e7cu07ukd.cloudfront.net/selenium-docker/vendor-app/index.html");
		Assert.assertTrue(loginPage.isAt());
		loginPage.login("sam", "sam");
	}
	
	@Test(dependsOnMethods = "loginTest")
	public void dashboardTest() {
		Assert.assertTrue(dashboardPage.isAt());
		
		//finance metrics
        Assert.assertEquals(dashboardPage.getMonthlyEarning(), "$40,000");
        Assert.assertEquals(dashboardPage.getAnnualEarning(), "$215,000");
        Assert.assertEquals(dashboardPage.getProfitMargin(), "50%");
        Assert.assertEquals(dashboardPage.getAvailableInventory(), "18");
        
        // order history search
        dashboardPage.searchOrderHistoryBy("adams");
        Assert.assertEquals(dashboardPage.getSearchResultsCount(), 8);
	}
	
    @Test(dependsOnMethods = "dashboardTest")
    public void logoutTest(){
        dashboardPage.logout();
        Assert.assertTrue(loginPage.isAt());
    }
	
}
