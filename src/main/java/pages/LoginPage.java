package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;

public class LoginPage extends BasePage {
    private final By usernameInput = By.name("username");
    private final By passwordInput = By.name("password");
    private final By loginButton = By.cssSelector("button[type='submit']");
    private final By invalidCredentialsAlert = By.cssSelector(".oxd-alert-content-text");
    private final By requiredFieldMessages = By.cssSelector("span.oxd-input-field-error-message");
    private final By footerBrandingLink = By.cssSelector(".orangehrm-copyright-wrapper a");
    private final By footerTextWrapper = By.cssSelector(".orangehrm-copyright-wrapper");

    public LoginPage(WebDriver driver) {
        super(driver);
    }
    @Step("Enter username: {username}")
    public void enterUsername(String username) {
        logger.info("Entering username: {}", username);
        type(usernameInput, username);
    }
    @Step("Enter password")
    public void enterPassword(String password) {
        logger.info("Entering password");
        type(passwordInput, password);
    }
    @Step("Click Login button")
    public void clickLogin() {
        logger.info("Clicking Login button");
        click(loginButton);
    }
    @Step("Login with username: {username}")
    public DashboardPage login(String username, String password) {
        enterUsername(username);
        enterPassword(password);
        clickLogin();
        return new DashboardPage(driver);
    }
    @Step("Get invalid credentials error message")
    public String getInvalidCredentialsMessage() {
        String msg = getText(invalidCredentialsAlert);
        logger.info("Invalid credentials message displayed: {}", msg);
        return msg;
    }
    @Step("Get count of 'Required' validation messages")
    public List<WebElement> getRequiredValidationMessages() {
        return waitForAllVisible(requiredFieldMessages);
    }
    @Step("Get footer branding text")
    public String getFooterText() {
        scrollToBottom();
        return getText(footerTextWrapper);
    }
    @Step("Click OrangeHRM footer link")
    public void clickFooterLink() {
        scrollToBottom();
        logger.info("Clicking OrangeHRM footer link");
        click(footerBrandingLink);
    }
}