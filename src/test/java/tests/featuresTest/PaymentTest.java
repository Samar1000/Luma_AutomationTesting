package tests.featuresTest;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.HomePage;
import pages.PaymentPage;
import pages.ShippingPage;
import tests.base.BaseDriver;
import listeners.MyListeners;
import org.testng.annotations.Listeners;
import utility.DataUtility;
import utility.LogsUtility;
import java.time.Duration;

@Listeners(MyListeners.class)
public class PaymentTest extends BaseDriver {
    private HomePage homePage;
    private ShippingPage shippingPage;
    private PaymentPage paymentPage;
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        homePage = new HomePage(driver);
        shippingPage = new ShippingPage(driver);
        paymentPage = new PaymentPage(driver);
        homePage.addITemToCart("Blue", "M");
        homePage.clickCartIcon();
        homePage.clickCheckoutButton();
        shippingPage.enterValidAddress(DataUtility.getValidShippingData()).clickNext();
        LogsUtility.info("Choosing payment method...");
        paymentPage.choosePayment();
    }
    @Test (priority = 2)
    public void TC1_ChoosePayment(){
        paymentPage.clickPlaceOrderBtn();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("success"));
        Assert.assertEquals(driver.getCurrentUrl(),"https://magento.softwaretestingboard.com/checkout/onepage/success/");
    }
    @Test
    public void TC2_ApplyDiscount() {
        double previousTotal = paymentPage.getTotal();
        LogsUtility.info("Applying discount code: 20Poff");
        paymentPage.applyDiscountCode("20Poff");
        paymentPage.waitForOrderTotalChange(previousTotal);
        LogsUtility.info("Validating discount is applied");
        Assert.assertEquals(String.format("%.2f", paymentPage.getTotal()), String.format("%.2f", paymentPage.calculateExpectedTotal(20.0)), "Order total after applying discount is incorrect.");
    }
    @AfterClass
    public void quit(){
        quitDriver();
    }
}