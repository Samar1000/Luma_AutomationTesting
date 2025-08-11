package tests.featuresTest;
import listeners.MyListeners;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.ShippingPage;
import tests.base.BaseDriver;
import utility.DataUtility;
import utility.LogsUtility;
import utility.Utility;

import java.time.Duration;

@Listeners(MyListeners.class)
public class ShippingTest extends BaseDriver {
    private HomePage homePage;
    private ShippingPage shippingPage;
    @BeforeClass
    public void setup() {
        driver = baseDriver();
        homePage = new HomePage(driver);
        homePage.addITemToCart("Blue", "M");
        homePage.clickCartIcon();
        homePage.clickCheckoutButton();
        shippingPage = new ShippingPage(driver);
    }
    @Test
    public void TC1_enterValidAddress(){
        shippingPage.enterValidAddress(DataUtility.getValidShippingData()).clickNext();
        Utility.waitForUrlContains(driver,"#payment");
        Assert.assertEquals(driver.getCurrentUrl(),"https://magento.softwaretestingboard.com/checkout/#payment");
    }
//    @Test
//    public void TC2_enterInvalidAddress(){
//        shippingPage.
//                      .clickNext();
//        Utility.waitForUrlContains(driver,"#payment");
//        Assert.assertEquals(driver.getCurrentUrl(),"https://magento.softwaretestingboard.com/checkout/#payment");
//    }
    @AfterClass
    public void quit(){
        quitDriver();
    }
}
