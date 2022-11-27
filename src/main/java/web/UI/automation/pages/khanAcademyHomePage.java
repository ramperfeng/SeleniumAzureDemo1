package web.UI.automation.pages;

import web.UI.automation.cucumber.ScenarioContext;
import web.UI.automation.cucumber.TestContext;
import web.UI.automation.enums.Context;
import web.UI.automation.helper.Eyess;
import web.UI.automation.helper.ThreadSleep;
import web.UI.automation.webelement.FindWebElement;
import org.junit.Assert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class khanAcademyHomePage {

    TestContext testContext;
    ScenarioContext scenarioContext;

    private FindWebElement find;
    private WebDriver webDriver;
    private Eyess eyes;
    @FindBy(xpath = "//*[@id=\"app-shell-root\"]/div/div[1]/nav/div/div[2]/a/svg")
    private WebElement khanAcademyLogo;
    @FindBy(xpath = "//*[@id=\"app-shell-root\"]/div/div[1]/nav/div/div[1]/div[1]/button/span")
    private WebElement courseLink;
    @FindBy(xpath = "//*[@id=\"header-dropdown\"]/ul/li[8]/div/ul[2]/li[2]/a/span")
    private WebElement computerScienceLink;
    @FindBy(xpath = "//*[@id=\"topic-progress\"]/span/div/div/div[3]/div/div[2]/div/div[1]/div[1]/div[3]/a/h3")
    private WebElement Algorithms;


    public khanAcademyHomePage(TestContext context) {
        testContext = context;
        this.find = testContext.getPageObjectManager().getFindWebElement();
        this.webDriver = testContext.getTestDriverManager().getDriver();
        this.eyes = testContext.getEyess();
        scenarioContext = testContext.getScenarioContext();
        PageFactory.initElements(webDriver, this);
    }

    public void homePageLoad() {
        webDriver.get(System.getProperty("properties.envURL"));
        find.waitForAllPageElementsLoaded();
        ThreadSleep.For(2);
    }

    public void isLogoDisplayed() {
        Assert.assertTrue("khanAcademy logo is displayed", find.isElementVisible(khanAcademyLogo));
        eyes.checkElement(khanAcademyLogo, "Logo");
    }
    public void computerSciencePage() {
      //  Assert.assertTrue("khanAcademy logo is displayed", find.isElementVisible(khanAcademyLogo));
       // eyes.checkElement(khanAcademyLogo, "Logo");
    }
    public void clickcourseLinkButton() {

        find.waitAndClickElement(courseLink);
    }
    public void clickComputerSciencecourseLink() {

        find.waitAndClickElement(computerScienceLink);
    }
    public void clickAlgorithmsLink() {

        find.waitAndClickElement(Algorithms);
    }
    public String getPageTitle() {

       String title=testContext.getTestDriverManager().getDriver().getTitle();
       return title;
    }

}
