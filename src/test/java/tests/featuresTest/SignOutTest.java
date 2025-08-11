package tests.featuresTest;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.HomePage;
import pages.SignInPage;
import pages.SignOutPage;
import listeners.MyListeners;
import tests.base.BaseDriver;
import utility.LogsUtility;

@Listeners(MyListeners.class)
public class SignOutTest extends BaseDriver {
    private pages.SignOutPage signOutPage;
    private HomePage homePage;
    private SignInPage SignInPage;
    SoftAssert softAssert = new SoftAssert();
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        homePage = new HomePage(driver);
        homePage.navigateToSignInPage();
        SignInPage = new SignInPage(driver);
        SignInPage.signedIn();
        signOutPage = new SignOutPage(driver);
    }
    @Test
    public void TC1_signOut(){
        homePage.clickSignOutHyperLink();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/customer/account/logoutSuccess/");
        softAssert.assertEquals(signOutPage.getSignOutSuccessMsg(), "You are signed out");
    }
    @AfterClass
    public void quit(){
        quitDriver();
    }

}
