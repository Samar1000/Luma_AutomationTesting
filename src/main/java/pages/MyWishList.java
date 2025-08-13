package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MyWishList {
    private WebDriver driver;
    public MyWishList(WebDriver driver) {
        this.driver=driver;
    }
    private By messageLocator = By.xpath("//div[@data-bind='html: $parent.prepareMessageForHtml(message.text)']");
    public String getWishlistMessageText() {
        String messageElement = driver.findElement(messageLocator).getText().trim();
        return messageElement;
    }
}
