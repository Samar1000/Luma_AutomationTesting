package pages;
import utility.DataUtility;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class SignInPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public SignInPage(WebDriver driver) {
        this.driver=driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By signInEmailInput = By.id("email");
    private By signInPassInput = By.name("login[password]");
    private By signInButton = By.cssSelector("button#send2.action.login.primary");
    private By forgotPasswordLink=By.linkText("Forgot Your Password?");
    private By emailFieldError=By.id("email-error");
    private By passwordFieldError=By.id("pass-error");
    private By errorMessage = By.cssSelector("div.message-error > div");
    private By ResetPasswordConfirmationMsg = By.cssSelector("div[data-bind='html: $parent.prepareMessageForHtml(message.text)']");
    private By wishlistLoginMessage = By.cssSelector("div[data-bind*='prepareMessageForHtml']");
    public void setEmailInput(String email){
        driver.findElement(signInEmailInput).sendKeys(email);
    }
    public void setPassInput(String pass){
        driver.findElement(signInPassInput).sendKeys(pass);
    }

    public void clickSignInButton(){
        driver.findElement(signInButton).click();
    }
    public void ClearFields(){
        driver.findElement(signInEmailInput).clear();
        driver.findElement(signInPassInput).clear();
    }
    public String getSignInErrorMsg(){
        return driver.findElement(errorMessage).getText().trim();
    }

    public boolean isSignInErrorVisible() {
        return driver.findElement(errorMessage).isDisplayed();
    }

    public boolean getBlankEmailFieldError() {
        String error = driver.findElement(emailFieldError).getText().trim();
        return error.equals("This is a required field.");
    }

    public boolean getPassFieldError(){
        String error = driver.findElement(passwordFieldError).getText().trim();
        return error.equals("This is a required field.");
    }
    public boolean getEmailFieldError() {
        String error = driver.findElement(emailFieldError).getText().trim();
        return error.equals("Please enter a valid email address (Ex: johndoe@domain.com).");
    }
    public void signedIn() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(signInEmailInput)).clear();
        driver.findElement(signInEmailInput).sendKeys(DataUtility.getJsonData("SignInData","valid email"));
        wait.until(ExpectedConditions.visibilityOfElementLocated(signInPassInput)).clear();
        driver.findElement(signInPassInput).sendKeys(DataUtility.getJsonData("SignInData","valid password"));
        wait.until(ExpectedConditions.elementToBeClickable(signInButton)).click();
    }
    public void clickForgotPasswordLink(){
        driver.findElement(forgotPasswordLink).click();
    }
    public String getResetPassConfirmationMsg(){
        return driver.findElement(ResetPasswordConfirmationMsg).getText().trim();
    }
    public String getWishlistLoginMessage() {
         return wait.until(ExpectedConditions.visibilityOfElementLocated(wishlistLoginMessage)).getText().trim();
    }

}