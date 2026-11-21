package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import java.util.List;
import java.util.stream.Collectors;

public class DashboardPage extends BasePage {
    private final By dashboardHeader = By.xpath("//h6[text()='Dashboard']");
    private final By sidebarMenuItems = By.cssSelector("ul.oxd-main-menu li span.oxd-main-menu-item--name");
    private final By pimMenuLink = By.xpath("//span[text()='PIM']/parent::a");
    private final By adminMenuLink = By.xpath("//span[text()='Admin']/parent::a");
    public DashboardPage(WebDriver driver) {
        super(driver);}
    @Step("Verify Dashboard header is displayed")
    public boolean isDashboardHeaderDisplayed() {
        logger.info("Checking if Dashboard header is visible");
        return isDisplayed(dashboardHeader);}
    @Step("Get Dashboard header text")
    public String getDashboardHeaderText() {
        return getText(dashboardHeader);}
    @Step("Get all sidebar menu item names")
    public List<String> getSidebarMenuNames() {
        logger.info("Fetching all sidebar menu item names");
        return waitForAllVisible(sidebarMenuItems)
                .stream()
                .map(WebElement::getText)
                .collect(Collectors.toList());}
    @Step("Navigate to PIM module")
    public PimPage navigateToPim() {
        logger.info("Navigating to PIM module");
        click(pimMenuLink);
        return new PimPage(driver);}
    @Step("Navigate to Admin module")
    public AdminPage navigateToAdmin() {
        logger.info("Navigating to Admin module");
        click(adminMenuLink);
        return new AdminPage(driver);
    }
}