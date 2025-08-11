package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Utility;

import java.time.Duration;

public class ProductPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public ProductPage(WebDriver driver) {
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By addToCartButton = By.id("product-addtocart-button");
    private By cartMessage = By.cssSelector("div[data-bind*='prepareMessageForHtml']");
    private By productTitle = By.cssSelector("span.base[data-ui-id='page-title-wrapper']");
    private By errorMessage = By.cssSelector("div.message-error[data-ui-id='message-error'] > div");
    private By addToWishlistLink = By.cssSelector("a.action.towishlist[data-action='add-to-wishlist']");
    private By quantityInput = By.cssSelector("input#qty.input-text.qty");
    private By qtyErrorMessage = By.cssSelector("div.mage-error#qty-error");
    private By sizeErrorMessage = By.cssSelector("div.mage-error#super_attribute\\[143\\]-error");
    private By colorErrorMessage = By.cssSelector("div.mage-error#super_attribute\\[93\\]-error");
    public void clickAddToCart() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(addToCartButton));
        button.click();
    }
    public String getProductMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(cartMessage)).getText().trim();
    }
    public void chooseSizeAndColor(String size, String color) {
        By sizeLocator = By.cssSelector("div.swatch-option.text[option-label='" + size + "']");
        By colorLocator = By.cssSelector("div.swatch-option.color[option-label='" + color + "']");
        Utility.clickWithFallback(driver,sizeLocator);
        Utility.clickWithFallback(driver, colorLocator);
    }
    public String getProductTitle(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(productTitle));
        return driver.findElement(productTitle).getText();
    }
    public String getErrorMessage() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(errorMessage)).getText().trim();
    }
    public void clickAddToWishlist() {
        WebElement wishlistLink = wait.until(ExpectedConditions.presenceOfElementLocated(addToWishlistLink));
        Utility.clickWithFallback(driver, addToWishlistLink);
    }
    public void setQuantity(int qty) {
        WebElement qtyField = wait.until(ExpectedConditions.visibilityOfElementLocated(quantityInput));
        qtyField.clear();
        qtyField.sendKeys(String.valueOf(qty));
    }
    public String getQtyErrorMessage() {
        WebElement errorDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(qtyErrorMessage));
        return errorDiv.getText().trim();
    }
    public String getColorErrorMessage() {
        WebElement errorDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(colorErrorMessage));
        return errorDiv.getText().trim();
    }
    public String getSizeErrorMessage() {
        WebElement errorDiv = wait.until(ExpectedConditions.visibilityOfElementLocated(sizeErrorMessage));
        return errorDiv.getText().trim();
    }
}
