package web.UI.automation.helper;

import web.UI.automation.cucumber.TestContext;
import web.UI.automation.manager.TestDriverManager;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.MatchLevel;
import com.applitools.eyes.selenium.Eyes;
import com.applitools.eyes.selenium.StitchMode;
import com.applitools.eyes.selenium.fluent.Target;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.time.LocalDateTime;

public class Eyess {

    private Eyes eyes;
    private TestContext testContext;
    private TestDriverManager testDriverManager;
    private WebDriver webDriver;


    public Eyess(TestContext context) {
        this.testContext = context;
        this.testDriverManager = testContext.getTestDriverManager();
        this.webDriver = testDriverManager.getDriver();
    }

    public void initializeEyes(String scenario, BatchInfo batchInfo, LocalDateTime lDateTime) {
        eyes = new Eyes();

        if (System.getProperty("properties.applitools").equalsIgnoreCase("true")) {
            eyes.setServerUrl("https://foodstuffseyesapi.applitools.com");
            eyes.setApiKey("W8syZk8NsA5ZesE97pTOGsPdVftJzhN7PPapZxeISBC0110");
            eyes.setBranchName("PAMS_" + System.getProperty("properties.environment").toUpperCase());
            batchInfo.setId(lDateTime.toString());
            eyes.setBatch(batchInfo);
            eyes.open(webDriver, "PAMS", scenario);
        }
    }

    public Eyes getEyes() {
        return eyes;
    }

    public void checkWindow(boolean takeFullPageScreenshot, boolean scrollPage) {
        if (System.getProperty("properties.applitools").equalsIgnoreCase("true")) {
            eyes.setStitchMode(StitchMode.CSS);
            eyes.setHideScrollbars(scrollPage);
            eyes.setForceFullPageScreenshot(takeFullPageScreenshot);
            eyes.setMatchLevel(MatchLevel.STRICT);
            eyes.checkWindow();
        }
    }

    public void checkElement(WebElement webElement, String screenName) {
        if (System.getProperty("properties.applitools").equalsIgnoreCase("true")) {
            webElement.getText();
            eyes.setMatchLevel(MatchLevel.CONTENT);
            eyes.setHideScrollbars(true);
            eyes.setForceFullPageScreenshot(true);
            try {
                eyes.checkElement(webElement, screenName);
            } catch (StaleElementReferenceException ex) {
                eyes.checkElement(webElement, screenName);
            }
        }
    }

    public void eyesIgnoreRegion(String screenName, By[] ignoreRegion, boolean takeFullPageScreenshot, boolean scrollPage) {
        if (System.getProperty("properties.applitools").equalsIgnoreCase("true")) {
            eyes.setStitchMode(StitchMode.CSS);
            eyes.setHideScrollbars(scrollPage);
            eyes.setForceFullPageScreenshot(takeFullPageScreenshot);
            eyes.setMatchLevel(MatchLevel.STRICT);
            eyes.check(screenName, Target.window().ignore(ignoreRegion));
        }
    }
}
