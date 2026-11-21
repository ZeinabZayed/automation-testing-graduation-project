package base;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utils.ConfigReader;

public class BaseTest {
    protected final Logger logger = LogManager.getLogger(this.getClass());

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        logger.info("Initializing WebDriver on thread: {}", Thread.currentThread().getId());
        DriverFactory.initDriver();
        WebDriver driver = DriverFactory.getDriver();
        String url = ConfigReader.getProperty("base.url") + "auth/login";
        logger.info("Navigating to URL: {}", url);
        driver.get(url);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {
        logger.info("Closing WebDriver on thread: {}", Thread.currentThread().getId());
        DriverFactory.quitDriver();
    }

    protected WebDriver getDriver() {
        return DriverFactory.getDriver();
    }
}