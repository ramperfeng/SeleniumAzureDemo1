package web.UI.automation.pages;

import web.UI.automation.cucumber.ScenarioContext;
import web.UI.automation.cucumber.TestContext;
import web.UI.automation.helper.Eyess;
import web.UI.automation.helper.ThreadSleep;
import web.UI.automation.webelement.FindWebElement;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class dropBoxHomePage {

    TestContext testContext;
    ScenarioContext scenarioContext;

    private FindWebElement find;
    private WebDriver webDriver;
    private Eyess eyes;
    @FindBy(xpath = "//*[@id=\"nav-item-horizontal-2\"]/span")
    private WebElement solutionLink;
    @FindBy(xpath = "//*[@id=\"nav-item-horizontal-1\"]/span")
    private WebElement productsLink;
    @FindBy(xpath = "//*[@id=\"nav-item-horizontal-2\"]/div/div/div/div/ul/li[5]/a/div/span/span")
    private WebElement ITSolutionLink;
    @FindBy(xpath ="//*[@id=\"warp-metadata\"]/nav/div[1]/div[1]/div/ul/li[4]/a/span")
    private WebElement pricing;



    public dropBoxHomePage(TestContext context) {
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

    public void titleAssertion() {
        Assert.assertTrue("Dropbox.com", testContext.getTestDriverManager().getDriver().getTitle().contains("Dropbox.com"));

    }

    public void ITSolutionLink() {

        find.waitAndClickElement(ITSolutionLink);
    }

    public String getPageTitle() {

       String title=testContext.getTestDriverManager().getDriver().getTitle();
       return title;
    }
    public void mouseHover() {
        Actions action = new Actions(webDriver);
       // WebElement element = driver.findElement(By.id("elementId"));
        action.moveToElement(solutionLink).perform();
    }
}
