package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.AdminPage;
import pages.DashboardPage;
import pages.LoginPage;
import utils.JsonDataReader;

import java.util.Arrays;
import java.util.List;

@Feature("Admin & UI Verification")
public class AdminAndUiTests extends BaseTest {

    @Test(priority = 1, description = "Test Case #9: Verify Admin > Add User page")
    @Description("Verify Add User form contains User Role, Employee Name, Username, and Password fields")
    public void testAdminAddUserPage() {
        LoginPage loginPage = new LoginPage(getDriver());
        String username = JsonDataReader.getNestedString("validLogin", "username");
        String password = JsonDataReader.getNestedString("validLogin", "password");

        DashboardPage dashboardPage = loginPage.login(username, password);
        AdminPage adminPage = dashboardPage.navigateToAdmin();
        adminPage.clickAddUser();

        Assert.assertTrue(adminPage.areAddUserFormFieldsDisplayed(),
                "Add User form must display User Role, Employee Name, Username, and Password fields");
    }

    @Test(priority = 2, description = "Test Case #10: Verify the OrangeHRM footer/branding link")
    @Description("Verify footer contains 'OrangeHRM, Inc' and clicking it opens a tab with orangehrm.com")
    public void testFooterBrandingLink() {
        LoginPage loginPage = new LoginPage(getDriver());
        String originalHandle = getDriver().getWindowHandle();

        String footerText = loginPage.getFooterText();
        Assert.assertTrue(footerText.contains("OrangeHRM, Inc"),
                "Footer text should contain 'OrangeHRM, Inc', but found: " + footerText);

        loginPage.clickFooterLink();
        loginPage.switchToNewTab(originalHandle);

        Assert.assertTrue(loginPage.waitForUrlContains("orangehrm.com"),
                "New tab URL should contain 'orangehrm.com'");
    }

    @Test(priority = 3, description = "Test Case #11: Verify sidebar menu UI")
    @Description("Verify sidebar menu contains all expected modules after login")
    public void testSidebarMenuItems() {
        LoginPage loginPage = new LoginPage(getDriver());
        String username = JsonDataReader.getNestedString("validLogin", "username");
        String password = JsonDataReader.getNestedString("validLogin", "password");

        DashboardPage dashboardPage = loginPage.login(username, password);
        List<String> actualItems = dashboardPage.getSidebarMenuNames();

        List<String> expectedItems = Arrays.asList(
                "Admin", "PIM", "Leave", "Time", "Recruitment",
                "My Info", "Performance", "Dashboard", "Directory"
        );

        Assert.assertTrue(actualItems.containsAll(expectedItems),
                "Sidebar menu missing expected items. Expected: " + expectedItems + " | Actual: " + actualItems);
    }
}