package pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MyAccountPage {
    private WebDriver driver;
    public MyAccountPage(WebDriver driver) {
        this.driver=driver;
    }
    private By registrationSuccessMessage = By.xpath("//div[contains(text(),'Thank you for registering with Main Website Store.')]");
    private By lumaLogo = By.cssSelector("img[src*='logo.svg'][alt='']");
    public String getRegistrationSuccessMessage() {
        return driver.findElement(registrationSuccessMessage).getText().trim();
    }
    public void NavigateToHomePage(){
        driver.findElement(lumaLogo).click();
    }
}
