package utility;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class ScreenshotUtility {
    public static String capture(WebDriver driver, String testName) {
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String path = LogsUtility.LOGS_PATH + "/screenshots/" + testName + ".png";
        File dest = new File(path);

        try {
            Files.createDirectories(dest.getParentFile().toPath());
            Files.copy(src.toPath(), dest.toPath(), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            LogsUtility.error("Screenshot capture failed: " + e.getMessage());
        }

        return path;
    }
}