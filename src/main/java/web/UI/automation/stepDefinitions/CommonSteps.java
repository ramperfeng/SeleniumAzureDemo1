package web.UI.automation.stepDefinitions;

import web.UI.automation.cucumber.TestContext;
import web.UI.automation.pages.CommonComponents;
import web.UI.automation.webelement.FindWebElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.When;

import static org.junit.Assert.fail;

public class CommonSteps {

    TestContext testContext;
    FindWebElement find;
    CommonComponents commonComponents;

    public CommonSteps(TestContext context) {
        testContext = context;
        this.commonComponents = testContext.getPageObjectManager().getCommonComponent();
        this.find = testContext.getPageObjectManager().getFindWebElement();
    }

    @When("I navigate to {string} page")
    public void iNavigateToPage(String page) {
        switch (page.toLowerCase()) {
            case "product range":
                commonComponents.navigateToPage(page);
                break;
            default:
                fail("CommonSteps: Invalid page name passed. -- " + page);
        }
    }

    @And("title is {string}")
    public void titleIs(String title) {
        commonComponents.validateTitle(title);
    }
}
