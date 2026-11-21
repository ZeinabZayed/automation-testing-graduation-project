package listeners;

import base.DriverFactory;
import io.qameta.allure.Attachment;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IAnnotationTransformer;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.annotations.ITestAnnotation;
import utils.RetryAnalyzer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class TestListener implements ITestListener, IAnnotationTransformer {
    private static final Logger logger = LogManager.getLogger(TestListener.class);

    @Override
    public void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {
        annotation.setRetryAnalyzer(RetryAnalyzer.class);
    }

    @Override
    public void onTestStart(ITestResult result) {
        logger.info("=== STARTING TEST: {} ===", result.getName());
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        logger.info("=== PASSED TEST: {} ===", result.getName());
    }

    @Override
    public void onTestFailure(ITestResult result) {
        logger.error("=== FAILED TEST: {} ===", result.getName(), result.getThrowable());
        WebDriver driver = DriverFactory.getDriver();
        if (driver != null) {
            saveScreenshot(driver);
        }
    }

    @Attachment(value = "Failure Screenshot", type = "image/png")
    public byte[] saveScreenshot(WebDriver driver) {
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}