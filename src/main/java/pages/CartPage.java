package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

public class CartPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public CartPage(WebDriver driver) {
        this.driver = driver;
        wait=new WebDriverWait(driver, Duration.ofSeconds(10));
    }
    private By subtotalPrice = By.cssSelector("span.price[data-th='Subtotal']");
    private By discountPrice = By.cssSelector("td[data-th='Discount'] span.price");
    private By shippingPrice = By.cssSelector("span.price[data-th='Shipping']");
    private By orderTotalPrice = By.cssSelector("td[data-th='Order Total'] span.price");
    private By itemTitle = By.xpath("//*[@id=\"shopping-cart-table\"]/tbody/tr[1]/td[1]/div/strong/a");
    private By removeItemLink = By.cssSelector("a.action-delete[title='Remove item']");
    private By qtyInput = By.xpath("//input[@title='Qty' and @type='number']");
    private By discountLabel = By.xpath("//span[@class='title' and text()='Discount']");
    private By updateCartButton = By.cssSelector("button.action.update[name='update_cart_action'][value='update_qty']");
    private By proceedToCheckoutButton = By.xpath("//button[@data-role='proceed-to-checkout']");
    private By emptyCartMsg = By.cssSelector("div.cart-empty");
    private By continueShoppingLink = By.xpath("//a[text()='here']");
    public void removeFirstCartItem() {
        driver.findElement(removeItemLink).click();
    }
    public String getItemTitle(){
        return driver.findElement(itemTitle).getText().trim();
    }
    public List<String> getItemNames(){
        driver.findElement(itemTitle).getText();
        List<WebElement> items = driver.findElements(itemTitle);
        return items.stream()
                .map(e -> e.getText().trim())
                .collect(Collectors.toList());

    }
    public String getSubtotalPrice(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(subtotalPrice));
        return driver.findElement(subtotalPrice).getText();
    }
    public String getOrderTotalPrice(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(orderTotalPrice));
        return driver.findElement(orderTotalPrice).getText();
    }

    public String getDiscountPrice() {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(discountPrice));
            return driver.findElement(discountPrice).getText();
        } catch (NoSuchElementException e) {
            return "Discount not applied";
        }
    }
    public String getShippingPrice(){
        try{
            wait.until(ExpectedConditions.visibilityOfElementLocated(shippingPrice));
            return driver.findElement(shippingPrice).getText();
        } catch (NoSuchElementException e) {
        return "Shipping not calculated yet";
    }
    }
    public void ClickProceedTOCheckOut() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(proceedToCheckoutButton)).click();
    }
    public void setQuantity(String qty){
        driver.findElement(qtyInput ).clear();
        driver.findElement(qtyInput ).sendKeys(qty);
    }
    public void waitForDiscount() {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(discountLabel,"Discount"));
    }
    public void clickUpdateCartButton() {
        wait.until(ExpectedConditions.elementToBeClickable(updateCartButton)).click();
    }
    public String getEmptyCartMsg(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(emptyCartMsg));
        return driver.findElement(emptyCartMsg).getText();
    }
    public void clickContinueShopping() {
        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='here']"))).click();
    }


}
