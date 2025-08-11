package listeners;
import tests.base.BaseDriver;
import utility.LogsUtility;
import utility.ScreenshotUtility;
import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.testng.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class MyListeners implements IInvokedMethodListener, ITestListener, ISuiteListener {
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult){
        System.out.println(method.getTestMethod().getMethodName());
    }
    public void afterInvocation(IInvokedMethod method, ITestResult testResult){
        System.out.println(testResult.getStatus());
    }
    public void onStart(org.testng.ISuite testResult) {
        System.out.println(testResult.getName()+" test started");
    }

    public void onFinish(org.testng.ISuite testResult) {
        System.out.println(testResult.getName()+" test finished"); }
    public void onTestSuccess(ITestResult testResult) {
        LogsUtility.info(testResult.getName() + " Test is successful");
    }
    public void onTestFailure(ITestResult testResult) {
        LogsUtility.info(testResult.getName() + " Test failed");
        LogsUtility.error("Failure reason: " + testResult.getThrowable());

        Object testInstance = testResult.getInstance();
        if (testInstance instanceof BaseDriver) {
            WebDriver driver = ((BaseDriver) testInstance).getDriver();
            // Capture screenshot
        String screenshotPath = ScreenshotUtility.capture(driver, testResult.getName());
        LogsUtility.error("📸 Screenshot saved at: " + screenshotPath);
        // Attach screenshot to Allure
        try {
            Allure.addAttachment("Screenshot on Failure", new FileInputStream(screenshotPath));
        } catch (IOException e) {
            LogsUtility.error("Failed to attach screenshot to Allure: " + e.getMessage());
        }

        // Optionally attach log file
        File latestLog = new File("test-outputs/Logs/latest.log"); // Adjust if needed
        if (latestLog.exists()) {
            try {
                Allure.addAttachment("Execution Log", new FileInputStream(latestLog));
            } catch (IOException e) {
                LogsUtility.error("Failed to attach log to Allure: " + e.getMessage());
            }
        }
    }

}
    public void onTestSkipped(ITestResult testResult) {
        LogsUtility.info(testResult.getName() + " Test is skipped");
    }
}