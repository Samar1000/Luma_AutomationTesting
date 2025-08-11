package utility;

import io.qameta.allure.Allure;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class Utility {
    //clicking, send data, get text...etc with wait
   private static final String SCREENSHOTS_PATH = "test-outputs/Screenshots/";
    public static String getTimeStamp(){
        return new SimpleDateFormat("yyyy-MM--dd-hh-mm-ssa").format(new Date());
    }
    public static void takeScreenshot(WebDriver driver, String screenshotName){
        try{
            File ScreenshotSrc = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            File ScreenshotFile = new File(SCREENSHOTS_PATH + screenshotName + "-" + getTimeStamp() + ".png");
            FileUtils.copyFile(ScreenshotSrc, ScreenshotFile);
            Allure.addAttachment(screenshotName, Files.newInputStream(ScreenshotFile.toPath()));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public static boolean waitForUrlContains(WebDriver driver, String partialUrl) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        return wait.until(ExpectedConditions.urlContains(partialUrl));
    }
    public static void waitForOverlayToDisappear(WebDriver driver) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div.loading-mask[data-role='loader']")));
        } catch (TimeoutException e) {
            System.out.println("Overlay still visible after timeout.");
        }
    }
    public static void waitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.out.println("Wait interrupted: " + e.getMessage());
        }
    }
    public static void clickWithFallback(WebDriver driver, By locator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(locator));
        try {
            wait.until(ExpectedConditions.elementToBeClickable(element)).click();
        } catch (ElementClickInterceptedException e) {
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        }
    }
}
