package web.UI.automation.pages;

import web.UI.automation.cucumber.TestContext;
import web.UI.automation.helper.Eyess;
import web.UI.automation.webelement.FindWebElement;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;

public class CommonComponents {
    private final FindWebElement find;
    private final WebDriver webDriver;
    private final Eyess eyes;
    TestContext testContext;

    public CommonComponents(TestContext context) {
        testContext = context;
        this.find = testContext.getPageObjectManager().getFindWebElement();
        this.webDriver = testContext.getTestDriverManager().getDriver();
        this.eyes = testContext.getEyess();
        PageFactory.initElements(webDriver, this);
    }

    public void navigateToPage(String page) {
        find.waitAndClickElement(find.findElementByXpath("//a[@role='menuitem'][text()='" + page + "']"));
    }

    public void validateTitle(String title) {
        webDriver.getTitle().contains(title);
    }
}
