package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import utils.JsonDataReader;

import java.util.List;

@Feature("Authentication")
public class LoginTests extends BaseTest {

    @DataProvider(name = "invalidLoginData")
    public Object[][] getInvalidLoginData() {
        return JsonDataReader.getInvalidLoginsData();
    }

    @Test(priority = 1, description = "Test Case #1: Login with valid credentials")
    @Description("Verify user can log in with valid Admin credentials and reach the Dashboard")
    public void testValidLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        String username = JsonDataReader.getNestedString("validLogin", "username");
        String password = JsonDataReader.getNestedString("validLogin", "password");

        DashboardPage dashboardPage = loginPage.login(username, password);

        Assert.assertTrue(dashboardPage.waitForUrlContains("/dashboard/index"),
                "URL should contain /dashboard/index after valid login");
        Assert.assertTrue(dashboardPage.isDashboardHeaderDisplayed(),
                "Dashboard header should be displayed");
        Assert.assertEquals(dashboardPage.getDashboardHeaderText(), "Dashboard",
                "Dashboard header text mismatch");
    }

    @Test(priority = 2, dataProvider = "invalidLoginData", description = "Test Case #2: Login with invalid credentials")
    @Description("Verify 'Invalid credentials' error message appears on invalid login attempts")
    public void testInvalidLogin(String invalidUsername, String invalidPassword) {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.login(invalidUsername, invalidPassword);

        Assert.assertEquals(loginPage.getInvalidCredentialsMessage(), "Invalid credentials",
                "Expected 'Invalid credentials' error message");
    }

    @Test(priority = 3, description = "Test Case #3: Login with empty fields")
    @Description("Verify 'Required' validation messages appear under both username and password fields")
    public void testEmptyFieldsLogin() {
        LoginPage loginPage = new LoginPage(getDriver());
        loginPage.clickLogin();

        List<WebElement> errors = loginPage.getRequiredValidationMessages();
        Assert.assertEquals(errors.size(), 2, "Expected 2 'Required' validation messages");
        for (WebElement error : errors) {
            Assert.assertEquals(error.getText(), "Required", "Validation message should read 'Required'");
        }
    }
}