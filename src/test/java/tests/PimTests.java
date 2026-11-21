package tests;

import base.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.DashboardPage;
import pages.LoginPage;
import pages.PimPage;
import utils.JsonDataReader;

@Feature("PIM - Employee Management")
public class PimTests extends BaseTest {

    private PimPage loginAndGoToPim() {
        LoginPage loginPage = new LoginPage(getDriver());
        String username = JsonDataReader.getNestedString("validLogin", "username");
        String password = JsonDataReader.getNestedString("validLogin", "password");
        DashboardPage dashboardPage = loginPage.login(username, password);
        return dashboardPage.navigateToPim();
    }

    @Test(priority = 1, description = "Test Case #4: Search for an employee (PIM module)")
    @Description("Verify searching for an existing employee displays matching records in the results table")
    public void testSearchExistingEmployee() {
        PimPage pimPage = loginAndGoToPim();
        pimPage.openEmployeeList();

        String knownEmployee = JsonDataReader.getString("existingEmployeeSearch");
        pimPage.searchEmployeeByName(knownEmployee);

        Assert.assertTrue(pimPage.isEmployeePresentInResults(knownEmployee),
                "Results table should contain employee: " + knownEmployee);
    }

    @Test(priority = 2, description = "Test Case #5: Search for a non-existing employee")
    @Description("Verify searching for a non-existing employee displays 'No Records Found'")
    public void testSearchNonExistingEmployee() {
        PimPage pimPage = loginAndGoToPim();
        pimPage.openEmployeeList();

        String nonExistingEmployee = JsonDataReader.getString("nonExistingEmployeeSearch");
        pimPage.searchEmployeeByName(nonExistingEmployee);

        Assert.assertTrue(pimPage.isNoRecordsFoundDisplayed(),
                "'No Records Found' message should be displayed");
    }

    @Test(priority = 3, description = "Test Case #6: Open Add Employee page")
    @Description("Verify navigating to Add Employee opens /pim/addEmployee with First Name and Last Name inputs")
    public void testOpenAddEmployeePage() {
        PimPage pimPage = loginAndGoToPim();
        pimPage.openAddEmployee();

        Assert.assertTrue(pimPage.waitForUrlContains("/pim/addEmployee"),
                "URL should contain /pim/addEmployee");
        Assert.assertTrue(pimPage.areNameFieldsDisplayed(),
                "First Name and Last Name fields should be displayed");
    }

    @Test(priority = 4, description = "Test Case #7: Add a new employee with an empty required field")
    @Description("Verify leaving First Name empty triggers 'Required' validation error")
    public void testAddEmployeeEmptyFirstName() {
        PimPage pimPage = loginAndGoToPim();
        pimPage.openAddEmployee();

        String lastName = JsonDataReader.getNestedString("newEmployee", "lastName");
        pimPage.enterEmployeeDetails("", lastName);
        pimPage.clickSave();

        Assert.assertTrue(pimPage.isFirstNameRequiredErrorDisplayed(),
                "'Required' validation error should appear under First Name");
    }

    @Test(priority = 5, description = "Test Case #8: End-to-end - Add a new employee successfully")
    @Description("Verify creating a new employee opens Personal Details and employee appears in Employee List search")
    public void testAddNewEmployeeEndToEnd() {
        PimPage pimPage = loginAndGoToPim();
        pimPage.openAddEmployee();

        String firstName = JsonDataReader.getNestedString("newEmployee", "firstName") + System.currentTimeMillis() % 10000;
        String lastName = JsonDataReader.getNestedString("newEmployee", "lastName");

        pimPage.enterEmployeeDetails(firstName, lastName);
        pimPage.clickSave();

        Assert.assertTrue(pimPage.isPersonalDetailsDisplayed(),
                "Personal Details page should open after saving new employee");

        pimPage.openEmployeeList();
        pimPage.searchEmployeeByName(firstName);

        Assert.assertTrue(pimPage.isEmployeePresentInResults(firstName),
                "Newly created employee '" + firstName + "' should appear in Employee List search results");
    }
}