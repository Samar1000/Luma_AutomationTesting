package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Map;

public class CreateAnAccountPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public CreateAnAccountPage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By firstNameInput = By.id("firstname");
    private By lastNameInput = By.id("lastname");
    private By emailInput = By.id("email_address");
    private By passwordInput = By.id("password");
    private By confirmPasswordInput = By.id("password-confirmation");
    private By passwordStrengthMeter = By.id("password-strength-meter-label");
    private By createAccountButton = By.cssSelector("button.action.submit.primary[title='Create an Account']");
    private By firstNameError = By.id("firstname-error");
    private By lastNameError = By.id("lastname-error");
    private By emailFieldError = By.id("email_address-error");
    private By passwordError = By.id("password-error");
    private By confirmPasswordError = By.id("password-confirmation-error");
    private By emailExistsError = By.cssSelector("div[data-bind*='prepareMessageForHtml']");

    public void setFirstNameInput(String fName){
        driver.findElement(firstNameInput).sendKeys(fName);
    }
    public void setLastNameInput(String lName){
        driver.findElement(lastNameInput).sendKeys(lName);
    }
    public void setEmailInput(String email){
        driver.findElement(emailInput).sendKeys(email);
    }
    public void setPassInput(String pass){
        driver.findElement(passwordInput).sendKeys(pass);
    }
    public void setPassConfirmationInput(String pass){
        driver.findElement(confirmPasswordInput).sendKeys(pass);
    }
    public void clickCreateAnAccountButton(){
        driver.findElement(createAccountButton).click();
    }
    public void ClearFields(){
        driver.findElement(firstNameInput).clear();
        driver.findElement(lastNameInput).clear();
        driver.findElement(emailInput).clear();
        driver.findElement(passwordInput).clear();
        driver.findElement(confirmPasswordInput).clear();
    }
    public void validSignUp(Map<String, String> signUpData) {
        setFirstNameInput(signUpData.get("first name"));
        setLastNameInput(signUpData.get("last name"));
        setEmailInput(signUpData.get("valid email"));
        setPassInput(signUpData.get("valid password"));
        setPassConfirmationInput(signUpData.get("valid password"));
    }
    public String getBlankFirstNameError() {
        return driver.findElement(firstNameError).getText().trim();
    }
    public String getBlankLastNameError() {
        return driver.findElement(lastNameError).getText().trim();
    }
    public String getEmailFieldError() {
        return driver.findElement(emailFieldError).getText().trim();
    }
    public String getPassFieldError() {
        return driver.findElement(passwordError).getText().trim();
    }
    public String getConfirmPassError() {
        return driver.findElement(confirmPasswordError).getText().trim();
    }
    public String getEmailExistsError() {
        WebElement errorElement = wait.until(ExpectedConditions.visibilityOfElementLocated(emailExistsError));
        return errorElement.getText().trim();
    }
        public void ClearEmail() {
        driver.findElement(emailInput).clear();
    }
}