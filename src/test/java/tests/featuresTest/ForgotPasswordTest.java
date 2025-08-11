package tests.featuresTest;
import pages.ForgotPasswordPage;
import pages.HomePage;
import pages.SignInPage;
import tests.base.BaseDriver;
import utility.DataUtility;
import org.testng.Assert;
import org.testng.annotations.*;
import listeners.MyListeners;
@Listeners(MyListeners.class)
public class ForgotPasswordTest extends BaseDriver {
    private SignInPage signInPage;
    private ForgotPasswordPage forgotPassword;
    private HomePage homePage;
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        homePage = new HomePage(driver);
        homePage.navigateToSignInPage();
        signInPage = new SignInPage(driver);
        signInPage.clickForgotPasswordLink();
        forgotPassword = new ForgotPasswordPage(driver);
    }
    @Test (priority = 1)
    public void TC1_invalidEmailFormat(){
        forgotPassword.setEmailInput(DataUtility.getJsonData("SignInData","invalid email format"));
        forgotPassword.clickResetMyPassword();
        Assert.assertEquals(forgotPassword.getErrorMsg(),"Please enter a valid email address (Ex: johndoe@domain.com).");
    }
    @Test (priority = 0)
    public void TC2_invalidBlankEmail(){
        forgotPassword.clickResetMyPassword();
        Assert.assertEquals(forgotPassword.getErrorMsg(),"This is a required field.");
    }
    @Test (priority = 2)
    public void TC1_validEmail(){
        forgotPassword.ClearEmailField();
        forgotPassword.setEmailInput(DataUtility.getJsonData("SignInData","valid email"));
        forgotPassword.clickResetMyPassword();
        Assert.assertTrue(signInPage.getResetPassConfirmationMsg().contains("you will receive an email with a link to reset your password."));
    }
    @AfterClass
    public void quit(){
        quitDriver();
    }
}
