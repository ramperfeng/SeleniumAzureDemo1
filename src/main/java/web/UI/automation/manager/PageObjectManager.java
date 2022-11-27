package web.UI.automation.manager;


import web.UI.automation.cucumber.TestContext;
import web.UI.automation.pages.*;
import web.UI.automation.webelement.FindWebElement;

public class PageObjectManager {

    CommonComponents commonComponent;


    khanAcademyHomePage khanAcademyhomePage;
    dropBoxHomePage dropboxhomepage;

    private FindWebElement findWebElement;
    private TestContext testContext;

    public PageObjectManager(TestContext context) {
        testContext = context;
    }

    public FindWebElement getFindWebElement() {
        return (findWebElement == null) ? findWebElement = new FindWebElement(testContext) : findWebElement;
    }


    public dropBoxHomePage getdropboxhomepage() {
        return (dropboxhomepage == null) ? dropboxhomepage = new dropBoxHomePage(testContext) : dropboxhomepage;
    }
    public khanAcademyHomePage getkhanAcademyhomePage() {
        return (khanAcademyhomePage == null) ? khanAcademyhomePage = new khanAcademyHomePage(testContext) : khanAcademyhomePage;
    }
    public CommonComponents getCommonComponent() {
        return (commonComponent == null) ? commonComponent = new CommonComponents(testContext) : commonComponent;
    }


}
