package web.UI.automation.stepDefinitions;

import web.UI.automation.cucumber.TestContext;
import web.UI.automation.pages.dropBoxHomePage;
import web.UI.automation.pages.khanAcademyHomePage;
import web.UI.automation.webelement.FindWebElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class dropBoxPageSteps {

    TestContext testContext;
    FindWebElement find;
    dropBoxHomePage dropboxhomepage;

    public dropBoxPageSteps(TestContext context) {

        testContext = context;
        this.dropboxhomepage = testContext.getPageObjectManager().getdropboxhomepage();
        this.find = testContext.getPageObjectManager().getFindWebElement();
    }

    @Given("I am on dropBox home page")
    public void IamOnDropBoxHomePage() {
        dropboxhomepage.homePageLoad();
    }

    @Then("verify the user in dropBox home")
    public void pageTitleAssertion() {
        dropboxhomepage.titleAssertion();
    }

    @When("user mouse hover on solution link")
    public void userMouseHoveronSloutionLink() {
        dropboxhomepage.mouseHover();
    }

    @And("user click on IT service link")
    public void userClickOnITLink() {
        dropboxhomepage.ITSolutionLink();
    }

    @Then("verify user landing into the IT service page")
    public void verifyITServicePageTitle() {
        String pageTile=dropboxhomepage.getPageTitle();
        System.out.println("We are in IT solution  page link:+>>>>>> " +pageTile);
    }

}
