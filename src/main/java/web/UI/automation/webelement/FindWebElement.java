package web.UI.automation.webelement;

import web.UI.automation.cucumber.TestContext;
import web.UI.automation.enums.Wait;
import web.UI.automation.helper.ThreadSleep;
import web.UI.automation.manager.TestDriverManager;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.List;


public class FindWebElement {

    private static final By subFrame = By.xpath("//iframe[contains(@name, 'uxtabiframe')]");
    private static final Logger logger = LoggerFactory.getLogger(FindWebElement.class);
    private final By loadingIndicator = By.xpath("//div[@class='LoadingIndicator']");
    private FluentWait<WebDriver> wait;
    private WebDriverWait busyIndicatorWait;
    private WebDriverWait extendedWait;
    private TestContext testContext;
    private TestDriverManager testDriverManager;
    private WebDriver driver;

    public FindWebElement(TestContext context) {
        this.testContext = context;
        this.testDriverManager = testContext.getTestDriverManager();
        this.driver = testDriverManager.getDriver();

        wait = new FluentWait<>(driver)
                .withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(100))
                .ignoring(NoSuchElementException.class)
                .ignoring(StaleElementReferenceException.class)
                .ignoring(NullPointerException.class);
    }

    public WebElement webElement(By elemLocator) {

        logger.info("Trying to find element " + elemLocator);

        WebElement element = wait.until(d -> d.findElement(elemLocator));

        highlightElement(element);

        logger.info("Found element " + elemLocator);

        return element;
    }

    public WebElement webElement(By elemLocator, Wait waitOptions) {

        wait.until(d -> ((JavascriptExecutor) d).executeScript("return document.readyState").equals("complete"));

        WebElement element = null;

        logger.info("Trying to find element " + elemLocator);

        if (waitOptions.equals(Wait.TILLNOTBUSY)) {

            element = wait.until(d -> d.findElement(elemLocator));

        } else {
            if (waitOptions.equals(Wait.TOBEVISIBLE)) {

                wait.until(d -> d.findElement(elemLocator).isDisplayed());
                wait.until(d -> d.findElement(elemLocator).isEnabled());
                element = wait.until(d -> d.findElement(elemLocator));

            } else {
                if (waitOptions.equals(Wait.TOBELNINDICATOR)) {
                    wait.until(d ->
                    {
                        logger.info("Busy Indicator Found");
                        return driver.findElements(loadingIndicator).size() <= 0;
                    });

                    wait.until(ExpectedConditions.visibilityOfElementLocated(elemLocator));
                    wait.until(d -> d.findElement(elemLocator).isEnabled());
                    element = wait.until(d -> d.findElement(elemLocator));

                }
            }
        }

        String jsHighLighter = "arguments[0].style.border='1px dotted green'";

        if (driver != null) {
            ((JavascriptExecutor) driver).executeScript(jsHighLighter, element);
        }

        logger.info("Found element " + elemLocator);

        return element;

    }

    public WebElement displayedElement(By elemLocator) {

        List<WebElement> elements = webElements(elemLocator);

        if (elements.size() > 0) {
            for (WebElement element : elements) {
                if (element.isDisplayed()) {
                    return element;
                }
            }
        }

        elements.forEach(element -> System.out.println(element.getText()));

        throw new ElementNotVisibleException("No Visible element has been located");
    }

    public WebElement findElementByXpath(String xpath) {
        WebElement element = null;
        for (int i = 0; i <= 2; i++) {
            try {
                wait.withMessage(xpath);
                wait.until((ExpectedCondition<Boolean>) d -> (driver.findElement(By.xpath(xpath)).isDisplayed()));
                element = driver.findElement(By.xpath(xpath));
                highlightElement(element);
                break;
            } catch (StaleElementReferenceException se) {
                System.out.println(se.getMessage());
            }
        }
        return element;
    }

    public List<WebElement> findElementsByXpath(String xpath) {
        wait.withMessage(xpath);
        return driver.findElements(By.xpath(xpath));
    }

    public List<WebElement> webElements(By elemLocator) {


        logger.info("Trying to find elements " + elemLocator);

        List<WebElement> elements = wait.until(d -> d.findElements(elemLocator));

        if (elements.size() > 0) {
            logger.info("Elements found " + elemLocator + " is [" + elements.size() + "]");
        } else {
            logger.info("No elements found " + elemLocator);
        }
        return elements;
    }


    private void highlightElement(WebElement element) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].style.border='3px solid red'", element);
    }

    /**********************************************************************************
     **WAIT METHODS
     **********************************************************************************/
    public boolean WaitUntilWebElementIsVisible(WebElement element) {
        //try {
        this.wait.until(ExpectedConditions.visibilityOf(element));
        return true;
       /* } catch (Exception e) {
            System.out.println("WebElement is NOT visible, using locator: " + "<" + element.toString() + ">");
            Assert.fail("WebElement is NOT visible, Exception: " + e.getMessage());
            return false;
        }*/
    }

    public void waitForAllElementVisible(List<WebElement> webElement, int timeOutSeconds) throws Error {
        new WebDriverWait(driver, timeOutSeconds).until(ExpectedConditions.visibilityOfAllElements(webElement));
    }

    public void waitForTextToAppear(WebElement element, String textToAppear) {
        new WebDriverWait(driver, 30).until(ExpectedConditions.textToBePresentInElement(element, textToAppear));
    }

    public void waitForAllPageElementsLoaded() {
        new WebDriverWait(driver, 30).until(
                webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
    }

    public void scrollToAllElementByWebElementLocator(List<WebElement> element) {
        try {
            // ((JavascriptExecutor) webDriver).executeScript("window.scrollTo(0, document.body.scrollHeight)");
            this.wait.until(ExpectedConditions.visibilityOfAllElements(element));
            ((JavascriptExecutor) driver).executeScript("window.scrollBy(0, -400)");
            System.out.println("Succesfully scrolled to the WebElement, using locator: " + "<" + element.toString() + ">");
        } catch (Exception e) {
            System.out.println("Unable to scroll to the WebElement, using locator: " + "<" + element.toString() + ">");
            Assert.fail("Unable to scroll to the WebElement, Exception: " + e.getMessage());
        }
    }

    public void waitForElementsCount(By by, Integer number) {
        new WebDriverWait(driver, 30).until(ExpectedConditions.numberOfElementsToBe(by, number));
    }

    /**********************************************************************************
     **CLICK METHODS
     **********************************************************************************/
    public void waitAndClickElement(WebElement element) {
        boolean clicked = false;
        int attempts = 0;
        while (!clicked && attempts < 10) {
            try {
                this.wait.until(ExpectedConditions.elementToBeClickable(element)).click();
                //System.out.println("Successfully clicked on the WebElement: " + "<" + element.toString() + ">");
                clicked = true;
                return;
            } catch (Exception e) {
                System.out.println("Unable to wait and click on WebElement, Exception: " + e.getMessage());
            }
            attempts++;
        }
        Assert.fail("Unable to wait and click on the WebElement, using locator: " + "<" + element.toString() + ">");
    }

    public void waitAndClickElements(WebElement webElement) {
        boolean clicked = false;
        int attempts = 0;
        while (!clicked && attempts < 10) {
            try {
                this.wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
                System.out.println("Successfully clicked on the element: " + webElement);
                clicked = true;
            } catch (Exception e) {
                System.out.println("Unable to wait and click on the element using the By locator, Exception: " + e.getMessage());
                Assert.fail("Unable to wait and click on the element: " + webElement);
            }
            attempts++;
        }
    }

    public void waitAndSubmitElements(WebElement webElement) {
        try {
            this.wait.until(ExpectedConditions.elementToBeClickable(webElement)).click();
            System.out.println("Successfully clicked on the element: " + webElement);
            webElement.submit();
            ThreadSleep.For(4);
        } catch (Exception e) {
            System.out.println("Unable to wait and submit the element using the locator, Exception: " + e.getMessage());
            Assert.fail("Unable to wait and submit the element: " + webElement);
        }
    }

    public void clickOnTextFromDropdownList(WebElement list, String textToSearchFor) {
        org.openqa.selenium.support.ui.Wait<WebDriver> tempWait = new WebDriverWait(driver, 30);
        try {
            tempWait.until(ExpectedConditions.elementToBeClickable(list)).click();
            list.sendKeys(textToSearchFor);
            list.sendKeys(Keys.ENTER);
            System.out.println("Successfully sent the following keys: " + textToSearchFor + ", to the following WebElement: " + "<" + list + ">");
        } catch (Exception e) {
            System.out.println("Unable to send the following keys: " + textToSearchFor + ", to the following WebElement: " + "<" + list + ">");
            Assert.fail("Unable to select the required text from the dropdown menu, Exception: " + e.getMessage());
        }
    }


    public void clickOnElementUsingCustomTimeout(WebElement locator, WebDriver webDriver, int timeout) {
        try {
            final WebDriverWait customWait = new WebDriverWait(webDriver, timeout);
            customWait.until(ExpectedConditions.refreshed(ExpectedConditions.elementToBeClickable(locator)));
            locator.click();
            System.out.println("Successfully clicked on the WebElement, using locator: " + "<" + locator + ">" + ", using a custom Timeout of: " + timeout);
        } catch (Exception e) {
            System.out.println("Unable to click on the WebElement, using locator: " + "<" + locator + ">" + ", using a custom Timeout of: " + timeout);
            Assert.fail("Unable to click on the WebElement, Exception: " + e.getMessage());
        }
    }

    public boolean isElementClickable(WebElement element) {
        try {
            this.wait.until(ExpectedConditions.elementToBeClickable(element));
            //System.out.println("WebElement is clickable using locator: " + "<" + element.toString() + ">");
            return true;
        } catch (Exception e) {
            System.out.println("WebElement is NOT clickable using locator: " + "<" + element.toString() + ">");
            return false;
        }
    }

    public boolean isElementVisible(WebElement element) {
        try {
            this.wait.until(ExpectedConditions.visibilityOf(element));
            //System.out.println("WebElement is clickable using locator: " + "<" + element.toString() + ">");
            return true;
        } catch (Exception e) {
            System.out.println(element + ": WebElement is NOT visible");
            return false;
        }
    }

    public boolean isElementsVisible(List<WebElement> element) {
        try {
            this.wait.until(ExpectedConditions.visibilityOf(element.get(0)));
            return true;
        } catch (Exception e) {
            System.out.println(element + ": WebElement is NOT visible");
            return false;
        }
    }

    public boolean waitUntilElementDissapears(List<WebElement> webElement) {
        return this.wait.until(ExpectedConditions.invisibilityOfAllElements(webElement));
    }

    public void waitForElementToBeDisplayedNew(WebElement element, int specifiedTimeout) {
        //WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(specifiedTimeout));
        WebDriverWait wait = new WebDriverWait(driver, specifiedTimeout);
        ExpectedCondition<Boolean> elementIsDisplayed = arg0 -> element.isDisplayed();
        wait.until(elementIsDisplayed);
    }

    public void scrollToElementAndClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].click()", element);
    }
}
