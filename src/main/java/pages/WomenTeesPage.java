package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Utility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class WomenTeesPage {
    private WebDriver driver;
    public WomenTeesPage(WebDriver driver) {
        this.driver=driver;
    }
    private By sortBy = By.cssSelector("select#sorter.sorter-options:nth-of-type(1)");
    private By sortDirection = By.cssSelector("a[data-role='direction-switcher']:nth-of-type(1)");
    private By productPrice = By.cssSelector("span.price-wrapper > span.price");
    private By productName = By.cssSelector("strong.product-item-name > a.product-item-link");
    public void sortBy(String sort) {
        Select sorter=new Select(driver.findElement(sortBy));
        sorter.selectByVisibleText(sort);
    }
    public void setSortOrder(String order){
        WebElement toggle = driver.findElement(sortDirection);
        String currentToggleValue = toggle.getAttribute("data-value");
        String currentOrder = currentToggleValue.equalsIgnoreCase("asc") ? "desc" : "asc";
        if (!currentOrder.equals(order)) {
            toggle.click();
            new WebDriverWait(driver, Duration.ofSeconds(10)).until(ExpectedConditions.presenceOfAllElementsLocatedBy(By.cssSelector("strong.product-item-name > a")));
        }

    }
    public List<Float> getProductPrices() {
        List<WebElement> priceElements = driver.findElements(productPrice);
        List<Float> prices = new ArrayList<>();
        for (WebElement element : priceElements) {
            String raw = element.getText().replace("$", "").trim();
            if (!raw.isEmpty()) {
                try {
                    prices.add(Float.parseFloat(raw));
                } catch (NumberFormatException ignored){}
            }
        }
        return prices;
    }
    public List<String> getProductNames() {
        List<WebElement> nameElements = driver.findElements(productName);
        List<String> names = new ArrayList<>();
        for (WebElement element : nameElements) {
            String name = element.getText().trim();
            if (!name.isEmpty()) {
                names.add(name);
            }
        }
        return names;
    }
    public void chooseProduct(){
        Utility.clickWithFallback(driver,productName);
    }


}
