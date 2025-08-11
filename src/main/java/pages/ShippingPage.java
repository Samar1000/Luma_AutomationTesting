package pages;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utility.LogsUtility;
import java.time.Duration;
import java.util.Map;

public class ShippingPage {
    private WebDriver driver;
    private WebDriverWait wait;
    public ShippingPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }
    private By emailField =  By.id("customer-email");
    private By firstNameField = By.cssSelector("input.input-text[name='firstname']:not([disabled])");
    private By lastNameField = By.cssSelector("input.input-text[name='lastname']:not([disabled])");
    private By companyField = By.cssSelector("input.input-text[name='company']:not([disabled])");
    private By addressLine1 = By.cssSelector("input.input-text[name='street[0]']:not([disabled])");
    private By addressLine2 = By.cssSelector("input.input-text[name='street[1]']:not([disabled])");
    private By addressLine3 = By.cssSelector("input.input-text[name='street[2]']:not([disabled])");
    private By cityField = By.cssSelector("input.input-text[name='city']:not([disabled])");
    private By stateDropdownField = By.cssSelector("select[name='region_id']:not([disabled])");
    private By zipCodeField = By.cssSelector("input.input-text[name='postcode']:not([disabled])");
    private By countryDropdownField = By.cssSelector("select[name='country_id'], select[name*='country']");
    private By phoneField = By.cssSelector("input.input-text[name='telephone']:not([disabled])");
    private By fixedShippingRadio = By.cssSelector("input[type='radio'][value='flatrate_flatrate']");
    private By tableShippingRadio = By.cssSelector("input[type='radio'][value='tablerate_bestway']");
    private By nextButton = By.cssSelector("button[data-role='opc-continue']");
    public void setEmail(String email){
        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(emailField));
        emailInput.sendKeys(email);
    }
    public void setCompany(String company) {
        driver.findElement(companyField).sendKeys(company);
    }
    public void setStreetAddress(String street1,String street2,String street3) {
        driver.findElement(addressLine1).sendKeys(street1);
        driver.findElement(addressLine2).sendKeys(street2);
        driver.findElement(addressLine3).sendKeys(street3);
    }
    public void setCity(String city) {driver.findElement(cityField).sendKeys(city);
    }
    public void setZipCode(String zip) {
        driver.findElement(zipCodeField).sendKeys(zip);
    }
    public void setPhoneNumber(String phone) {
        driver.findElement(phoneField).sendKeys(phone);
    }
    public void selectCountry(String country) {
        WebElement countryDropdown = driver.findElement(countryDropdownField);
        Select select = new Select(countryDropdown);
        select.selectByVisibleText(country);
    }
    public void selectState(String state) {
        WebElement stateDropdown = driver.findElement(stateDropdownField);
        Select select = new Select(stateDropdown);
        select.selectByVisibleText(state);
    }
    public void selectFixedShippingRadio() {
        driver.findElement(fixedShippingRadio).click();
    }
    public void clickNext() {
        driver.findElement(nextButton).click();
    }
    public ShippingPage enterValidAddress(Map<String, String> addressData) {
        String email = addressData.get("email");
        String firstName = addressData.get("firstName");
        String lastName = addressData.get("lastName");
        String company = addressData.get("company");
        String street1 = addressData.get("address.street.line1");
        String street2 = addressData.get("address.street.line2");
        String street3 = addressData.get("address.street.line3");
        String city = addressData.get("address.city");
        String state = addressData.get("address.state");
        String zipCode = addressData.get("address.zip");
        String country = addressData.get("address.country");
        String phone = addressData.get("phone");
        if (email != null) {
            try {
                WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
                if (emailInput.isDisplayed() && emailInput.isEnabled()) {
                    emailInput.clear();
                    emailInput.sendKeys(email);
                    LogsUtility.info("Email field filled with: " + email);
                } else {
                    LogsUtility.warn("Email field not intractable");
                }
            } catch (NoSuchElementException | ElementNotInteractableException ignored) {}
        }
        if (firstName != null) {
            WebElement firstNameInput = driver.findElement(firstNameField);
            firstNameInput.clear();
            firstNameInput.sendKeys(firstName);
        }
        if (lastName != null) {
            WebElement lastNameInput = driver.findElement(lastNameField);
            lastNameInput.clear();
            lastNameInput.sendKeys(lastName);
        }
        setCompany(company);
        setStreetAddress(street1, street2, street3);
        setCity(city);
        selectState(state);
        setZipCode(zipCode);
        selectCountry(country);
        setPhoneNumber(phone);
        selectFixedShippingRadio();
        return this;
    }

}