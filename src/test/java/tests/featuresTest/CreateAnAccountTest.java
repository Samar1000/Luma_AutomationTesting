package tests.featuresTest;

import org.testng.asserts.SoftAssert;
import pages.CreateAnAccountPage;
import pages.HomePage;
import tests.base.BaseDriver;
import utility.DataUtility;
import org.testng.Assert;
import org.testng.annotations.*;
import listeners.MyListeners;
import utility.LogsUtility;

@Listeners(MyListeners.class)
public class CreateAnAccountTest extends BaseDriver {
    private CreateAnAccountPage createAnAccountPage;
    @BeforeClass
    public void setup(){
        driver = baseDriver();
        LogsUtility.info("Navigating to Create Account page");
        HomePage homePage = new HomePage(driver);
        homePage.NavigateToCreateAccountPage();
        createAnAccountPage = new CreateAnAccountPage(driver);
    }
    @Test (priority=7)
    public void TC1_validSignUp(){
        LogsUtility.info("Running TC1: Valid Sign Up");
        createAnAccountPage.ClearFields();
        createAnAccountPage.validSignUp(DataUtility.getValidSignUpData());
        createAnAccountPage.clickCreateAnAccountButton();
        Assert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/customer/account/");
    }
    @Test
    public void TC2_blankFieldsErrorSignUp(){
        LogsUtility.info("Running TC2: Blank Fields Error");
        createAnAccountPage.ClearFields();
        createAnAccountPage.clickCreateAnAccountButton();
        String expectedMessage = "This is a required field.";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createAnAccountPage.getBlankFirstNameError(), expectedMessage, "First name error mismatch");
        softAssert.assertEquals(createAnAccountPage.getBlankLastNameError(), expectedMessage, "Last name error mismatch");
        softAssert.assertEquals(createAnAccountPage.getEmailFieldError(), expectedMessage, "Email error mismatch");
        softAssert.assertEquals(createAnAccountPage.getPassFieldError(), expectedMessage, "Password error mismatch");
        softAssert.assertEquals(createAnAccountPage.getConfirmPassError(), expectedMessage, "Confirm password error mismatch");
        softAssert.assertAll();
    }
    @Test
    public void TC3_invalidEmailErrorSignUp(){
        LogsUtility.info("Running TC3: Invalid Email Format");
        createAnAccountPage.ClearFields();
        createAnAccountPage.setEmailInput(DataUtility.getJsonData("CreateAnAccountData","invalid email format"));
        createAnAccountPage.clickCreateAnAccountButton();
        Assert.assertEquals(createAnAccountPage.getEmailFieldError(), "Please enter a valid email address (Ex: johndoe@domain.com).", "Email error mismatch");
    }
    @Test (priority=1)
    public void TC4_emailExistsErrorSignUp(){
        LogsUtility.info("Running TC4: Email Already Exists");
        createAnAccountPage.ClearFields();
        createAnAccountPage.validSignUp(DataUtility.getValidSignUpData());
        createAnAccountPage.ClearEmail();
        createAnAccountPage.setEmailInput(DataUtility.getJsonData("CreateAnAccountData","Existent email"));
        createAnAccountPage.clickCreateAnAccountButton();
        Assert.assertEquals(createAnAccountPage.getEmailExistsError(), "There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.", "Email error mismatch");
    }
    @Test
    public void TC5_checkErrorForPasswordLengthSignUp(){
        LogsUtility.info("Running TC5: Password Too Short");
        createAnAccountPage.ClearFields();
        createAnAccountPage.setPassInput(DataUtility.getJsonData("CreateAnAccountData","password 6 digit"));
        createAnAccountPage.clickCreateAnAccountButton();
        Assert.assertEquals(createAnAccountPage.getPassFieldError(), "Minimum length of this field must be equal or greater than 8 symbols. Leading and trailing spaces will be ignored.", "Password error mismatch");
    }
    @Test
    public void TC6_checkErrorForInvalidPasswordFormatSignUp(){
        LogsUtility.info("Running TC6: Invalid Password Format");
        createAnAccountPage.ClearFields();
        createAnAccountPage.setPassInput(DataUtility.getJsonData("CreateAnAccountData","invalid password format"));
        createAnAccountPage.clickCreateAnAccountButton();
        Assert.assertEquals(createAnAccountPage.getPassFieldError(), "Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.", "Password error mismatch");
    }

    @Test
    public void TC7_checkErrorForMisMatchedConfirmPasswordSignUp(){
        LogsUtility.info("Running TC7: Mismatched Confirm Password");
        createAnAccountPage.ClearFields();
        createAnAccountPage.setPassInput(DataUtility.getJsonData("CreateAnAccountData","valid password"));
        createAnAccountPage.setPassConfirmationInput(DataUtility.getJsonData("CreateAnAccountData","wrong confirm pass"));
        createAnAccountPage.clickCreateAnAccountButton();
        Assert.assertEquals(createAnAccountPage.getConfirmPassError(), "Please enter the same value again.", "Confirm Password error mismatch");
    }
    @AfterClass
    public void quit(){
        LogsUtility.info("Closing browser");
        quitDriver();
    }

}
