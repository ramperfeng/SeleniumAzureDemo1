package web.UI.automation.cucumber;


import web.UI.automation.helper.Eyess;
import web.UI.automation.manager.PageObjectManager;
import web.UI.automation.manager.TestDriverManager;

public class TestContext {
    private TestDriverManager testDriverManager;
    private PageObjectManager pageObjectManager;
    private Eyess eyess;
    public ScenarioContext scenarioContext;

    public TestContext() {
        testDriverManager = new TestDriverManager(this);
        pageObjectManager = new PageObjectManager(this);
        eyess = new Eyess(this);
        scenarioContext = new ScenarioContext();
    }

    public PageObjectManager getPageObjectManager() {
        return pageObjectManager;
    }

    public TestDriverManager getTestDriverManager() {
        return testDriverManager;
    }

    public ScenarioContext getScenarioContext() {
        return scenarioContext;
    }

    public Eyess getEyess() {
        return eyess;
    }

}
