package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
public class SignOutPage {
    private WebDriver driver;
    private By signOutSuccessMsq= By.cssSelector("span.base[data-ui-id='page-title-wrapper']");
    public SignOutPage(WebDriver driver)
    {
        this.driver=driver;
    }
    public String getSignOutSuccessMsg(){
        return driver.findElement(signOutSuccessMsq).getText();
    }
}
