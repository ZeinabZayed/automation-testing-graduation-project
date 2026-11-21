package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class AdminPage extends BasePage {
    private final By addButton = By.xpath("//button[normalize-space()='Add']");
    private final By userRoleLabel = By.xpath("//label[text()='User Role']");
    private final By employeeNameLabel = By.xpath("//label[text()='Employee Name']");
    private final By usernameLabel = By.xpath("//label[text()='Username']");
    private final By passwordLabel = By.xpath("//label[text()='Password']");

    public AdminPage(WebDriver driver) {
        super(driver);
    }

    @Step("Click 'Add' button on Admin Users page")
    public void clickAddUser() {
        logger.info("Clicking 'Add' button in Admin > User Management > Users");
        click(addButton);
    }

    @Step("Verify Add User form fields (User Role, Employee Name, Username, Password) are displayed")
    public boolean areAddUserFormFieldsDisplayed() {
        logger.info("Verifying Add User form fields visibility");
        return isDisplayed(userRoleLabel)
                && isDisplayed(employeeNameLabel)
                && isDisplayed(usernameLabel)
                && isDisplayed(passwordLabel);
    }
}