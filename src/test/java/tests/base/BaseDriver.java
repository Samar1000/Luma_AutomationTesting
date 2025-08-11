package tests.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class BaseDriver {
    protected WebDriver driver;
    public WebDriver baseDriver(){
        ChromeOptions options = new ChromeOptions();
        options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
        options.addArguments("--start-maximized");
        options.addArguments("--disable-extensions");
        options.addArguments("--disable-popup-blocking");
        options.addArguments("--incognito");
        driver = new ChromeDriver(options);
        driver.get("https://magento.softwaretestingboard.com/");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        return driver;
    }
    public void quitDriver(){
        if (driver != null) {
            driver.quit();
        }
    }
    public WebDriver getDriver() {
        return driver;
    }

}
