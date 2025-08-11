package tests.featuresTest;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import pages.HomePage;
import pages.SignInPage;
import tests.base.BaseDriver;
import utility.DataUtility;
import org.testng.Assert;
import org.testng.annotations.*;
import listeners.MyListeners;
@Listeners(MyListeners.class)
public class SignInTest extends BaseDriver {
    private SignInPage signInPage;
    private HomePage homePage;
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        homePage = new HomePage(driver);
        homePage.navigateToSignInPage();
        signInPage = new SignInPage(driver);
    }

    @Epic("Web interface")
    @Feature("Essential features")
    @Story("Authentication")
    @Test (priority=5)
    public void TC1_validSignIn(){
        signInPage.ClearFields();
        signInPage.setEmailInput(DataUtility.getJsonData("SignInData","valid email"));
        signInPage.setPassInput(DataUtility.getJsonData("SignInData","valid password"));
        signInPage.clickSignInButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/");
    }
    @Test
    public void TC2_invalidPassword(){
        signInPage.ClearFields();
        signInPage.setEmailInput(DataUtility.getJsonData("SignInData","valid email"));
        signInPage.setPassInput(DataUtility.getJsonData("SignInData","invalid password"));
        signInPage.clickSignInButton();
        Assert.assertTrue(signInPage.isSignInErrorVisible());
        Assert.assertEquals(signInPage.getSignInErrorMsg(),
                "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
    }

    @Test
    public void TC3_invalidEmail(){
        signInPage.ClearFields();
        signInPage.setEmailInput(DataUtility.getJsonData("SignInData","invalid email"));
        signInPage.setPassInput(DataUtility.getJsonData("SignInData","valid password"));
        signInPage.clickSignInButton();
        Assert.assertTrue(signInPage.isSignInErrorVisible());
        Assert.assertEquals(signInPage.getSignInErrorMsg(),
                "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
    }
    @Test
    public void TC5_invalidEmailFormat(){
        signInPage.ClearFields();
        signInPage.setEmailInput(DataUtility.getJsonData("SignInData","invalid email format"));
        signInPage.clickSignInButton();
        Assert.assertTrue(signInPage.getEmailFieldError());
    }
    @Test
    public void TC6_blankFieldsError(){
        signInPage.ClearFields();
        signInPage.clickSignInButton();
        Assert.assertTrue(signInPage.getBlankEmailFieldError());
        Assert.assertTrue(signInPage.getPassFieldError());
    }
    @AfterClass
    public void quit(){
        quitDriver();
    }
}
