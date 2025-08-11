package tests.featuresTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.ProductPage;
import pages.SignInPage;
import tests.base.BaseDriver;

public class ProductTest extends BaseDriver {
    private HomePage homePage;
    private ProductPage productPage;
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        homePage = new HomePage(driver);
        homePage.openProductByIndex(2);
        productPage= new ProductPage(driver);
    }
    @Test (priority = 2)
    public void TC1_outOfStockItem(){
        productPage.setQuantity(1);
        productPage.chooseSizeAndColor("M","White");
        productPage.clickAddToCart();
        Assert.assertEquals(productPage.getErrorMessage(),"The requested qty is not available");
    }
    @Test (priority = 3)
    public void TC2_qtySetToZero(){
        productPage.chooseSizeAndColor("S","White");
        productPage.setQuantity(0);
        productPage.clickAddToCart();
        Assert.assertEquals(productPage.getQtyErrorMessage(),"Please enter a quantity greater than 0.");
    }
    @Test (priority = 4)
    public void TC3_wishListForGuests(){
        productPage.clickAddToWishlist();
        SignInPage signInPage = new SignInPage(driver);
        Assert.assertEquals(signInPage.getWishlistLoginMessage(),"You must login or register to add items to your wishlist.");
    }
    @Test (priority = 1)
    public void TC3_notChoosingSizeAndColor(){
        productPage.clickAddToCart();
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(productPage.getSizeErrorMessage(),"This is a required field.");
        softAssert.assertEquals(productPage.getColorErrorMessage(),"This is a required field.");
    }
    @AfterClass
    public void quit(){
        quitDriver();
    }

}
