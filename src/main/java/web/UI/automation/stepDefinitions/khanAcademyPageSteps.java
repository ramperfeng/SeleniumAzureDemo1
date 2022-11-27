package web.UI.automation.stepDefinitions;

import web.UI.automation.cucumber.TestContext;

import web.UI.automation.pages.khanAcademyHomePage;
import web.UI.automation.webelement.FindWebElement;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class khanAcademyPageSteps {

    TestContext testContext;
    FindWebElement find;
    khanAcademyHomePage khanAcademyhomePage;

    public khanAcademyPageSteps(TestContext context) {

        testContext = context;
        this.khanAcademyhomePage = testContext.getPageObjectManager().getkhanAcademyhomePage();
        this.find = testContext.getPageObjectManager().getFindWebElement();
    }

    @Given("I am on khanAcademy home page")
    public void IamOnHomePage() {

        khanAcademyhomePage.homePageLoad();
    }

    @Then("verify the user in kahanAcademy home")
    public void khanAcademyIsDisplayed() {
       // khanAcademyhomePage.isLogoDisplayed();
    }

    @When("user clicks on course link")
    public void userClicksOnSearchIcon() {
        khanAcademyhomePage.clickcourseLinkButton();
    }

    @And("user click on computer science course link")
    public void selectFromDate() {
        khanAcademyhomePage.clickComputerSciencecourseLink();
    }
    @And("user landed in to the computer science course")
    public void selecttoDate() {
        khanAcademyhomePage.computerSciencePage();
    }
    @And("user select Algorithms link")
    public void userEntertoPlace() {
        khanAcademyhomePage.clickAlgorithmsLink();
    }
    @And("user able to view Algorithms details")
    public void userEnterfromPlace() {
        String pageTile=khanAcademyhomePage.getPageTitle();
        System.out.println("We are in Algorithms page link:+>>>>>> " +pageTile);
    }

}
