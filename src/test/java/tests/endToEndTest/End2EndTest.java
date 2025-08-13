package tests.endToEndTest;
import listeners.MyListeners;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pages.*;
import tests.base.BaseDriver;
import utility.DataUtility;
import utility.LogsUtility;
import utility.Utility;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
@Listeners(MyListeners.class)
public class End2EndTest extends BaseDriver {
    SoftAssert softAssert = new SoftAssert();
    @BeforeMethod(alwaysRun = true)
    public void setup() {
        LogsUtility.info("Open home page");
        driver = baseDriver();
    }

    @Test (groups = {"Positive Scenarios"} )//test covers adding item to cart from homepage and checkout for guests
    public void endToEndAsAGuest() {
        LogsUtility.info("End to end Guest scenario test");
        LogsUtility.info("add item to cart");
        HomePage homePage = new HomePage(driver);
        homePage.addITemToCart("Blue", "M");
        LogsUtility.info("open cart");
        homePage.clickCartIcon();
        LogsUtility.info("Go to Checkout");
        homePage.clickCheckoutButton();
        Utility.waitForUrlContains(driver, "#shipping");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/#shipping");
        LogsUtility.info("Fill shipping address");
        ShippingPage shippingPage = new ShippingPage(driver);
        shippingPage.enterValidAddress(DataUtility.getValidShippingData()).clickNext();
        Utility.waitForUrlContains(driver, "#payment");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/#payment");
        LogsUtility.info("Navigate to payment page and choose payment method");
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.choosePayment();
        paymentPage.clickPlaceOrderBtn();
        LogsUtility.info("Place order");
        Utility.waitForUrlContains(driver, "success");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/onepage/success/", "order not confirmed");
        softAssert.assertAll();
        LogsUtility.info("End of Guest scenario");
    }

    @Test (groups = {"Positive Scenarios"} )
    //test cover signup, category navigation, cart discount, adding shipping address and payment, checkout for new users, update cart
    public void endToEndAsNewUser() {
        LogsUtility.info("End to end new user scenario test");
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);
        LogsUtility.info("Navigating to Create Account page");
        HomePage homePage = new HomePage(driver);
        homePage.NavigateToCreateAccountPage();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/customer/account/create/", "failed to navigate to create account page");
        LogsUtility.info("Fill Fields with valid data and click create an account button");
        createAnAccountPage.validSignUp(DataUtility.getValidSignUpData());
        createAnAccountPage.clickCreateAnAccountButton();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/customer/account/", "Failed to register");
        MyAccountPage myAccountPage = new MyAccountPage(driver);
        softAssert.assertEquals(myAccountPage.getRegistrationSuccessMessage(), "Thank you for registering with Main Website Store.", "Registration success message mismatch");
        myAccountPage.NavigateToHomePage();
        LogsUtility.info("Navigate to desired category and choose a product");
        homePage.navigateToTees().chooseProduct();
        softAssert.assertTrue(driver.getCurrentUrl().contains("tee"), "Failed to navigate to desired product");
        ProductPage productPage = new ProductPage(driver);
        productPage.chooseSizeAndColor("S", "Black");
        productPage.clickAddToCart();
        LogsUtility.info("Validate product added to cart");
        softAssert.assertEquals(productPage.getProductMessage(), ("You added " + productPage.getProductTitle() + " to your shopping cart."), "miss match confirmation message for adding an item");
        CartPage cart = new CartPage(driver);
        homePage.openCart();
        cart.setQuantity("4");
        cart.clickUpdateCartButton();
        cart.waitForDiscount();
        LogsUtility.info("Validate cart discount was added");
        softAssert.assertNotEquals(cart.getOrderTotalPrice(), cart.getSubtotalPrice(), "discount not applied");
        cart.ClickProceedTOCheckOut();
        LogsUtility.info("Validate adding shipping address and payment method and checking out");
        Utility.waitForUrlContains(driver, "#shipping");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/#shipping");
        ShippingPage shippingPage = new ShippingPage(driver);
        shippingPage.enterValidAddress(DataUtility.getRegisteredShippingData()).clickNext();
        Utility.waitForUrlContains(driver, "#payment");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/#payment");
        PaymentPage paymentPage = new PaymentPage(driver);
        paymentPage.choosePayment();
        paymentPage.clickPlaceOrderBtn();
        LogsUtility.info("Validate order is confirmed");
        Utility.waitForUrlContains(driver, "success");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/onepage/success/", "order not conformed");
        softAssert.assertAll();
        LogsUtility.info("End of new user scenario");
    }

    @Test (groups = {"Positive Scenarios"} )
    //test cover sign in, search, sort by price, sort order/direction Asc, discount code, sign out, wishlist, remove item
    public void endToEndAsOldUser() {
        LogsUtility.info("End to end old user scenario test");
        SignInPage signInPage = new SignInPage(driver);
        HomePage homePage = new HomePage(driver);
        //------------sign in----------
        homePage.navigateToSignInPage();
        signInPage.setEmailInput(DataUtility.getJsonData("SignInData", "valid email"));
        signInPage.setPassInput(DataUtility.getJsonData("SignInData", "valid password"));
        signInPage.clickSignInButton();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/");
        homePage.setSearchInput("Tee");
        homePage.clickSearchIcon();
        LogsUtility.info("Validate correct product list");
        softAssert.assertTrue(homePage.validateSearchResults("Tee"), "'Tee' should be in the search results");
        LogsUtility.info("Validate search url");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/catalogsearch/result/?q=Tee");
        WomenTeesPage womenTeesPage = new WomenTeesPage(driver);
        //-----------search&sort----------------------
        womenTeesPage.sortBy("Price");
        womenTeesPage.setSortOrder("asc");
        List<Float> prices = womenTeesPage.getProductPrices();
        List<Float> sortedPrices = new ArrayList<>(prices);
        Collections.sort(sortedPrices);
        softAssert.assertEquals(prices, sortedPrices, "items sorted wrong");
        softAssert.assertTrue(driver.getCurrentUrl().contains("product_list_dir=asc"), "wrong URL for sort order");
        softAssert.assertTrue(driver.getCurrentUrl().contains("product_list_order=price"), "wrong URL for sort method");
        //------------add item to cart---------------
        homePage.chooseProduct();
        ProductPage productPage = new ProductPage(driver);
        productPage.chooseSizeAndColor("M", "Orange");
        productPage.clickAddToCart();
        LogsUtility.info("Validate product added to cart");
        softAssert.assertEquals(productPage.getProductMessage(), ("You added " + productPage.getProductTitle() + " to your shopping cart."), "miss match confirmation message for adding an item");
        //------------add item to wishlist and assert it's added --------------
        String productTitle = productPage.getProductTitle();
        productPage.clickAddToWishlist();
        MyWishList myWishList = new MyWishList(driver);
        softAssert.assertEquals(myWishList.getWishlistMessageText(),productTitle+" has been added to your Wish List. Click here to continue shopping.");
        CartPage cart = new CartPage(driver);
        homePage.openCart();
        //------------remove item from cart--------------
        cart.removeFirstCartItem();
        softAssert.assertTrue(cart.getEmptyCartMsg().contains("no items in your shopping cart."),"mismatched empty cart message");
        cart.clickContinueShopping();
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/", "failed to navigate to home");
        //-----------add another item to cart------------
        homePage.addITemToCart("Blue", "M");
        homePage.clickCartIcon();
        LogsUtility.info("Go to Checkout");
        homePage.clickCheckoutButton();
        Utility.waitForUrlContains(driver, "#shipping");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/#shipping");
        ShippingPage shippingPage = new ShippingPage(driver);
        //------------selecting shipping method----------
        shippingPage.selectFixedShippingRadio();
        shippingPage.clickNext();
        //------------applying discount code in payment page-----------
        PaymentPage paymentPage = new PaymentPage(driver);
        double previousTotal = paymentPage.getTotal();
        LogsUtility.info("Applying discount code: 20Poff");
        Utility.waitForOverlayToDisappear(driver);
        paymentPage.applyDiscountCode("20poff");
        paymentPage.waitForOrderTotalChange(previousTotal);
        LogsUtility.info("Validating discount is applied");
        softAssert.assertEquals(String.format("%.2f", paymentPage.getTotal()), String.format("%.2f", paymentPage.calculateExpectedTotal(20.0)), "Order total after applying discount is incorrect.");
        paymentPage.clickPlaceOrderBtn();
        LogsUtility.info("Validate order is confirmed");
        Utility.waitForUrlContains(driver, "success");
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/checkout/onepage/success/", "order not confirmed");
        homePage.NavigateToHomePage();
        //------------sign out----------
        LogsUtility.info("Sign out");
        homePage.clickSignOutHyperLink();
        SignOutPage signOutPage= new SignOutPage(driver);
        softAssert.assertEquals(driver.getCurrentUrl(), "https://magento.softwaretestingboard.com/customer/account/logoutSuccess/");
        softAssert.assertEquals(signOutPage.getSignOutSuccessMsg(), "You are signed out");
        softAssert.assertAll();
        LogsUtility.info("End of old user scenario");
    }

    @Test (groups = {"Negative Scenarios"} )
    public void forgotPassword(){// test invalid data for email and password in email and test forgot password with valid and invalid data
        LogsUtility.info("Forgot password Scenario");
        SignInPage signInPage = new SignInPage(driver);
        HomePage homePage = new HomePage(driver);
        homePage.navigateToSignInPage();
        signInPage.clickSignInButton();
        softAssert.assertTrue(signInPage.getBlankEmailFieldError());
        softAssert.assertTrue(signInPage.getPassFieldError());
        signInPage.setEmailInput(DataUtility.getJsonData("SignInData","invalid email format"));
        signInPage.clickSignInButton();
        softAssert.assertTrue(signInPage.getEmailFieldError());
        signInPage.ClearFields();
        signInPage.setEmailInput(DataUtility.getJsonData("SignInData","valid email"));
        signInPage.setPassInput(DataUtility.getJsonData("SignInData","invalid password"));
        signInPage.clickSignInButton();
        softAssert.assertTrue(signInPage.isSignInErrorVisible());
        softAssert.assertEquals(signInPage.getSignInErrorMsg(), "The account sign-in was incorrect or your account is disabled temporarily. Please wait and try again later.");
        signInPage.clickForgotPasswordLink();
        ForgotPasswordPage forgotPassword = new ForgotPasswordPage(driver);
        forgotPassword.clickResetMyPassword();
        softAssert.assertEquals(forgotPassword.getErrorMsg(),"This is a required field.");
        forgotPassword.setEmailInput(DataUtility.getJsonData("SignInData","invalid email format"));
        forgotPassword.clickResetMyPassword();
        Assert.assertEquals(forgotPassword.getErrorMsg(),"Please enter a valid email address (Ex: johndoe@domain.com).");
        forgotPassword.ClearEmailField();
        forgotPassword.setEmailInput(DataUtility.getJsonData("SignInData","valid email"));
        forgotPassword.clickResetMyPassword();
        softAssert.assertTrue(signInPage.getResetPassConfirmationMsg().contains("you will receive an email with a link to reset your password."));
        softAssert.assertAll();
        LogsUtility.info("End of forgot password scenario");
    }
    @Test (groups = {"Negative Scenarios"} ) // check if user Enters Weak Password(format and length), any blank fields, invalid formats(typos), existing email, fail to confirm password
    public void userEntersInvalidSignUpData(){
        LogsUtility.info("Invalid sign up data scenario");
        LogsUtility.info("Navigating to Create Account page");
        HomePage homePage = new HomePage(driver);
        homePage.NavigateToCreateAccountPage();
        CreateAnAccountPage createAnAccountPage = new CreateAnAccountPage(driver);
        //------------blank fields test------------
        LogsUtility.info("Blank Fields Error");
        createAnAccountPage.clickCreateAnAccountButton();
        String expectedMessage = "This is a required field.";
        SoftAssert softAssert = new SoftAssert();
        softAssert.assertEquals(createAnAccountPage.getBlankFirstNameError(), expectedMessage, "First name error mismatch");
        softAssert.assertEquals(createAnAccountPage.getBlankLastNameError(), expectedMessage, "Last name error mismatch");
        softAssert.assertEquals(createAnAccountPage.getEmailFieldError(), expectedMessage, "Email error mismatch");
        softAssert.assertEquals(createAnAccountPage.getPassFieldError(), expectedMessage, "Password error mismatch");
        softAssert.assertEquals(createAnAccountPage.getConfirmPassError(), expectedMessage, "Confirm password error mismatch");
        LogsUtility.info("Invalid Email Format");
        createAnAccountPage.setEmailInput(DataUtility.getJsonData("CreateAnAccountData","invalid email format"));
        createAnAccountPage.clickCreateAnAccountButton();
        softAssert.assertEquals(createAnAccountPage.getEmailFieldError(), "Please enter a valid email address (Ex: johndoe@domain.com).", "Email error mismatch");
        LogsUtility.info("Email Already Exists");
        createAnAccountPage.ClearFields();
        createAnAccountPage.validSignUp(DataUtility.getValidSignUpData());
        createAnAccountPage.ClearEmail();
        createAnAccountPage.setEmailInput(DataUtility.getJsonData("CreateAnAccountData","Existent email"));
        createAnAccountPage.clickCreateAnAccountButton();
        softAssert.assertEquals(createAnAccountPage.getEmailExistsError(), "There is already an account with this email address. If you are sure that it is your email address, click here to get your password and access your account.", "Email error mismatch");
        LogsUtility.info("Invalid Password Format");
        createAnAccountPage.setPassInput(DataUtility.getJsonData("CreateAnAccountData","invalid password format"));
        createAnAccountPage.clickCreateAnAccountButton();
        softAssert.assertEquals(createAnAccountPage.getPassFieldError(), "Minimum of different classes of characters in password is 3. Classes of characters: Lower Case, Upper Case, Digits, Special Characters.", "Password error mismatch");
        LogsUtility.info("Mismatched Confirm Password");
        createAnAccountPage.ClearFields();
        createAnAccountPage.setPassInput(DataUtility.getJsonData("CreateAnAccountData","valid password"));
        createAnAccountPage.setPassConfirmationInput(DataUtility.getJsonData("CreateAnAccountData","wrong confirm pass"));
        createAnAccountPage.clickCreateAnAccountButton();
        softAssert.assertEquals(createAnAccountPage.getConfirmPassError(), "Please enter the same value again.", "Confirm Password error mismatch");
        softAssert.assertAll();
        LogsUtility.info("End of Invalid sign up data scenario");
    }
    @Test (groups = {"Negative Scenarios"} ) // out of stock and wishlist for quests
    public void invalidProductSelection(){
        LogsUtility.info("Invalid quantity and and guest adding item to wishlist scenario");
        HomePage homePage = new HomePage(driver);
        homePage.openProductByIndex(2);
        ProductPage productPage= new ProductPage(driver);
        productPage.setQuantity(0);
        productPage.clickAddToCart();
        SoftAssert softAssert=new SoftAssert();
        softAssert.assertEquals(productPage.getSizeErrorMessage(),"This is a required field.");
        softAssert.assertEquals(productPage.getColorErrorMessage(),"This is a required field.");
        softAssert.assertEquals(productPage.getQtyErrorMessage(),"Please enter a quantity greater than 0.");
        productPage.setQuantity(1);
        productPage.chooseSizeAndColor("M","White");
        productPage.clickAddToCart();
        softAssert.assertEquals(productPage.getErrorMessage(),"The requested qty is not available");
        productPage.clickAddToWishlist();
        SignInPage signInPage = new SignInPage(driver);
        softAssert.assertEquals(signInPage.getWishlistLoginMessage(),"You must login or register to add items to your wishlist.");
        softAssert.assertAll();
        LogsUtility.info("End of Invalid quantity and guest adding item to wishlist scenario");
    }
    @AfterMethod (alwaysRun = true)
    public void quit(){
        quitDriver();
    }
}
