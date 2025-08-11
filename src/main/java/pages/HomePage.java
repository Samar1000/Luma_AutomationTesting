package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.Utility;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class HomePage {
    private final WebDriver driver;
    private WebDriverWait wait;
    public HomePage(WebDriver driver) {
        this.driver=driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    private By signInHyperLink = By.cssSelector("a[href*='customer/account/login']");
    private By createAnAccountHyperLink = By.cssSelector("a[href*='customer/account/create']");
    private By dropDown =By.cssSelector("button.action.switch[data-action='customer-menu-toggle']");
    private By searchInput =By.id("search");
    private By searchIcon = By.cssSelector("button.action.search");
    private By cartIcon = By.cssSelector("a.action.showcart");
    private By noSearchResult = By.xpath("//div[normalize-space()='Your search returned no results.']");
    private By signOutHyperLink = By.xpath("//a[normalize-space()='Sign Out']");
    private By cartItemsCount = By.cssSelector("span.counter-number");
    private By categoryWomen = By.id("ui-id-4");
    private By WomenTops = By.id("ui-id-9");
    private By teesLink = By.id("ui-id-13");
    private By teeProduct =By.xpath("//li[@class='product-item']//a[@title='Radiant Tee']");
    private By productName = By.cssSelector("strong.product-item-name > a");
    private By addToCartButton = By.cssSelector("button.action.tocart.primary");
    private By viewAndEditCart = By.cssSelector("span[data-bind=\"i18n: 'View and Edit Cart'\"]");
    private By cartMessage = By.cssSelector("div.message-success.success.message > div");
    private By checkoutButton = By.cssSelector("button#top-cart-btn-checkout");
    private By lumaLogo = By.cssSelector("img[src*='logo.svg'][alt='']");
    private By productNameLink = By.cssSelector("strong.product-item-name a.product-item-link");
    public void navigateToSignInPage(){
        driver.findElement(signInHyperLink).click();
    }
    public void NavigateToCreateAccountPage(){
        driver.findElement(createAnAccountHyperLink).click();
    }
    public void setSearchInput(String searchText){
        wait.until(ExpectedConditions.elementToBeClickable(searchInput)).sendKeys(searchText);
    }
    public void clickSearchIcon(){
        wait.until(ExpectedConditions.elementToBeClickable(searchIcon)).click();
    }
    public void clickCartIcon(){
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(cartItemsCount, "0")));
        Utility.waitForSeconds(1);
        wait.until(ExpectedConditions.elementToBeClickable(cartIcon)).click();
    }
    public void clickSignOutHyperLink(){
        driver.findElement(dropDown).click();
        driver.findElement(signOutHyperLink).click();
    }
    public String getNoResultsMsg(){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement noResultElement = wait.until(ExpectedConditions.visibilityOfElementLocated(noSearchResult));
        return noResultElement.getText().trim();
    }
    public void clearSearchField(){
        driver.findElement(searchInput).clear();
    }
    public String getCatItemsCount(){
        return driver.findElement(cartItemsCount).getText().trim();
    }
    public void clickProductOne(){
        driver.findElement(teeProduct).click();
    }
    public void clickWomenTops(){
        Actions actions = new Actions(driver);
        WebElement womenMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(categoryWomen));
        actions.moveToElement(womenMenu).perform();
        WebElement topsMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(WomenTops));
        actions.moveToElement(topsMenu).click().perform();
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
    public void addITemToCart(String color, String size){
        By sizeSelector = By.cssSelector("div.swatch-attribute.size div.swatch-option.text[option-label='" + size + "']");
        By colorSelector = By.cssSelector("div.swatch-attribute.color div.swatch-option.color[aria-label='" + color + "']");
        driver.findElement(sizeSelector).click();
        driver.findElement(colorSelector).click();
        driver.findElement(addToCartButton).click();
    }
    public void openCart(){
        wait.until(ExpectedConditions.not(ExpectedConditions.textToBe(cartItemsCount, "0")));
        driver.findElement(cartIcon).click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(viewAndEditCart)).click();
    }
    public String getCartMessage(){
        return driver.findElement(cartMessage).getText().trim();
    }
    public void clickCheckoutButton(){
        wait.until(ExpectedConditions.presenceOfElementLocated(checkoutButton));
        wait.until(ExpectedConditions.elementToBeClickable(checkoutButton)).click();
    }
    public WomenTeesPage navigateToTees() {
        Actions actions = new Actions(driver);
        actions.moveToElement(driver.findElement(categoryWomen)).perform();
        actions.moveToElement(driver.findElement(WomenTops)).perform();
        wait.until(ExpectedConditions.elementToBeClickable(teesLink)).click();
        return new WomenTeesPage(driver);
    }

    public boolean validateSearchResults(String keyword) {
        wait.until(ExpectedConditions.urlContains("catalogsearch"));
        List<String> productNames = getProductNames();
        for (String name : productNames) {
            if (name.toLowerCase().contains(keyword.toLowerCase())) {
                return true;
            }
        }
        return false;
    }
    public void chooseProduct(){
        wait.until(ExpectedConditions.visibilityOfElementLocated(productNameLink)).click();
    }
    public void NavigateToHomePage(){
        driver.findElement(lumaLogo).click();
    }
    public void openProductByIndex(int index) {
        By productImageLink = By.xpath("(//div[@class='product-item-info']//a[contains(@class,'product-item-photo')])[" + index + "]");
        Utility.clickWithFallback(driver,productImageLink);
    }

}