package tests.featuresTest;
import tests.base.BaseDriver;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.*;
import pages.CartPage;
import pages.HomePage;
import listeners.MyListeners;

@Listeners(MyListeners.class)
public class CartTests extends BaseDriver {
    private HomePage homePage;
    private CartPage cart;
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        homePage = new HomePage(driver);
        cart = new CartPage(driver);
        homePage.addITemToCart("Blue","M");
        homePage.openCart();
    }
    @Test
    public void TC1_itemAddedToCart() {
        Assert.assertEquals(cart.getItemTitle(),homePage.getProductNames().get(0));
    }
    @Test
    public void TC2_discountAppliedFor4OrMoreItemsInCart() {
        cart.setQuantity("4");
        cart.clickUpdateCartButton();
        cart.waitForDiscount();
        Assert.assertNotEquals(cart.getOrderTotalPrice(),cart.getSubtotalPrice(), "discount not applied");
    }
    @Test (priority = 3)
    public void TC3_emptyCartMsg(){
        cart.removeFirstCartItem();
        Assert.assertTrue(cart.getEmptyCartMsg().contains("You have no items"));
    }
    @AfterClass
    public void quit(){
        quitDriver();
    }
}