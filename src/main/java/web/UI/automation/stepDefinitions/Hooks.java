package web.UI.automation.stepDefinitions;


import web.UI.automation.cucumber.ScenarioContext;
import web.UI.automation.cucumber.TestContext;
import web.UI.automation.helper.Eyess;
import web.UI.automation.manager.TestDriverManager;
import com.applitools.eyes.BatchInfo;
import com.applitools.eyes.TestResults;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.junit.Assert;
import org.openqa.selenium.*;

import java.time.LocalDateTime;


public class Hooks {

    WebDriver driver;
    TestContext testContext;
    ScenarioContext scenarioContext;
    TestDriverManager testDriverManager;
    Eyess eyes;
    static LocalDateTime lDateTime = LocalDateTime.now();
    private TestResults testResults;

    public Hooks(TestContext context) {
        testContext = context;
        testDriverManager = testContext.getTestDriverManager();
        eyes = testContext.getEyess();
        scenarioContext = testContext.getScenarioContext();
    }

    @Before
    public void beforeScenario(Scenario scenario) {
        driver = testDriverManager.getDriver();
        BatchInfo batchInfo = new BatchInfo("PAMS_" + System.getProperty("properties.environment").toUpperCase() + "_" + lDateTime.toString().substring(0, 19));
        eyes.initializeEyes(scenario.getName(), batchInfo, lDateTime);
    }

    @After()
    public void after(Scenario scenario) {
        if (System.getProperty("properties.applitools").equalsIgnoreCase("true")) {
            testResults = eyes.getEyes().close(false);
            if (!testResults.getStatus().toString().equalsIgnoreCase("Passed")) {
                Assert.fail("Applitools comparison failure, refer link :" + testResults.getUrl());
            }
        }
    }

    @After()
    public void afterScenario(Scenario scenario) {
        if (scenario.isFailed()) {
            System.out.println("failed scenarios =======      " + scenario.getName());
            ((JavascriptExecutor) driver).executeScript("document.body.style.zoom='80%';");
            driver.manage().window().setSize(new Dimension(700, 900));
            byte[] imageBytes = ((TakesScreenshot) testDriverManager.getDriver()).getScreenshotAs(OutputType.BYTES);
            scenario.attach(imageBytes, "image/png", "image");
        }
        testDriverManager.closeDriver();
    }
}
