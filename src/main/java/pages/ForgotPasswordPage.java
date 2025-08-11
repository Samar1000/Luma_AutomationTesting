package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage {
    private WebDriver driver;
    public ForgotPasswordPage(WebDriver driver) {
        this.driver=driver;
    }
    private By signInEmailInput = By.id("email_address");
    private By emailFieldError=By.id("email_address-error");
    private By resetMyPassword = By.xpath("//button[span[text()='Reset My Password']]");
    public void clickResetMyPassword(){
        driver.findElement(resetMyPassword).click();
    }
    public void setEmailInput(String email){
        driver.findElement(signInEmailInput).sendKeys(email);
    }
    public String getErrorMsg(){
        return driver.findElement(emailFieldError).getText().trim();
    }
    public void ClearEmailField(){
        driver.findElement(signInEmailInput).clear();
    }
}
