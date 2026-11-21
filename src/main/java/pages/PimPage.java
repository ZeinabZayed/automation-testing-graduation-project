package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.util.List;

public class PimPage extends BasePage {
    private final By employeeListTab = By.xpath("//a[text()='Employee List']");
    private final By addEmployeeTab = By.xpath("//a[text()='Add Employee']");
    private final By employeeNameSearchInput = By.xpath("//label[text()='Employee Name']/ancestor::div[contains(@class,'oxd-input-group')]//input");
    private final By searchButton = By.cssSelector("button[type='submit']");
    private final By tableRows = By.cssSelector(".oxd-table-body .oxd-table-card");
    private final By noRecordsFoundText = By.xpath("//span[text()='No Records Found']");
    private final By loadingSpinner = By.cssSelector(".oxd-loading-spinner");
    private final By firstNameInput = By.name("firstName");
    private final By lastNameInput = By.name("lastName");
    private final By saveButton = By.cssSelector("button[type='submit']");
    private final By firstNameRequiredError = By.xpath("//input[@name='firstName']/ancestor::div[contains(@class,'oxd-input-group')]//span[text()='Required']");
    private final By personalDetailsHeader = By.xpath("//h6[text()='Personal Details']");

    public PimPage(WebDriver driver) {
        super(driver);
    }

    private void waitForSpinnerToDisappear() {
        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(loadingSpinner));
        } catch (Exception ignored) {
        }
    }

    @Step("Open Employee List tab")
    public void openEmployeeList() {
        logger.info("Clicking Employee List tab");
        click(employeeListTab);
        waitForSpinnerToDisappear();
    }

    @Step("Open Add Employee tab")
    public void openAddEmployee() {
        logger.info("Clicking Add Employee tab");
        click(addEmployeeTab);
        waitForVisibility(firstNameInput);
    }

    @Step("Search for employee by name: {employeeName}")
    public void searchEmployeeByName(String employeeName) {
        logger.info("Searching employee by name: {}", employeeName);
        waitForSpinnerToDisappear();
        type(employeeNameSearchInput, employeeName);
        click(searchButton);
        waitForSpinnerToDisappear();
    }

    @Step("Check if results table contains employee: {expectedName}")
    public boolean isEmployeePresentInResults(String expectedName) {
        List<WebElement> rows = waitForAllVisible(tableRows);
        for (WebElement row : rows) {
            if (row.getText().toLowerCase().contains(expectedName.toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    @Step("Check if 'No Records Found' message is displayed")
    public boolean isNoRecordsFoundDisplayed() {
        return isDisplayed(noRecordsFoundText);
    }

    @Step("Check if First Name and Last Name inputs are displayed")
    public boolean areNameFieldsDisplayed() {
        return isDisplayed(firstNameInput) && isDisplayed(lastNameInput);
    }

    @Step("Enter First Name: {firstName} and Last Name: {lastName}")
    public void enterEmployeeDetails(String firstName, String lastName) {
        logger.info("Entering First Name: '{}', Last Name: '{}'", firstName, lastName);
        if (firstName != null && !firstName.isEmpty()) {
            type(firstNameInput, firstName);
        }
        if (lastName != null && !lastName.isEmpty()) {
            type(lastNameInput, lastName);
        }
    }

    @Step("Click Save button on Add Employee form")
    public void clickSave() {
        logger.info("Clicking Save button");
        click(saveButton);
    }

    @Step("Check if First Name 'Required' validation error is displayed")
    public boolean isFirstNameRequiredErrorDisplayed() {
        return isDisplayed(firstNameRequiredError);
    }

    @Step("Verify Personal Details page opened")
    public boolean isPersonalDetailsDisplayed() {
        waitForSpinnerToDisappear();
        return isDisplayed(personalDetailsHeader);
    }
}