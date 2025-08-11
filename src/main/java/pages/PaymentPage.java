package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class PaymentPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public PaymentPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    private By paymentAddressCheckbox = By.id("billing-address-same-as-shipping-checkmo");
    private By placeOrderButton = By.cssSelector("button.action.primary.checkout");
    private By discountDropdown = By.id("block-discount-heading");
    private By discountCodeField= By.id("discount-code");
    private By applyDiscountButton = By.cssSelector("button.action.action-apply");
    private By discountAmount = By.cssSelector("span.price[data-th='checkout.sidebar.summary.totals.discount']");
    private By subtotalAmount = By.cssSelector("span.price[data-th='Cart Subtotal']");
    private By shippingAmount = By.cssSelector("span.price[data-th='Shipping']");
    private By orderTotalAmount = By.cssSelector("td.amount[data-th='Order Total'] span.price");
    public void choosePayment() {
        WebElement checkbox = wait.until(ExpectedConditions.presenceOfElementLocated(paymentAddressCheckbox));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
    }
    public void clickPlaceOrderBtn() {
        wait.until((ExpectedConditions.visibilityOfElementLocated(placeOrderButton)));
        wait.until(ExpectedConditions.elementToBeClickable(placeOrderButton)).click();
    }
    public void applyDiscountCode(String code){
        wait.until(ExpectedConditions.elementToBeClickable(discountDropdown)).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(discountCodeField)).sendKeys(code);
        driver.findElement(applyDiscountButton).click();
    }
    public double getSubtotal() {
        return Double.parseDouble(driver.findElement(subtotalAmount).getText().replace("$", "").trim());
    }

    public double getShipping() {
        return Double.parseDouble(driver.findElement(shippingAmount).getText().replace("$", "").trim());
    }

    public double getTotal() {
        return Double.parseDouble(driver.findElement(orderTotalAmount).getText().replace("$", "").trim());
    }
    public void waitForOrderTotalChange(double previousTotal) {
        wait.until(d -> {
            WebElement element = d.findElement(orderTotalAmount);
            String rawText = element.getText().replaceAll("[^\\d.]", ""); // Remove currency symbols
            double currentTotal = Double.parseDouble(rawText);
            return Double.compare(currentTotal, previousTotal) != 0;
        });
    }
    public double calculateExpectedTotal(double discountPercent) {
        double subtotal = getSubtotal();
        double shipping = getShipping();
        return subtotal + shipping - (subtotal * discountPercent / 100.0);
    }
}
