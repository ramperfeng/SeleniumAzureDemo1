package web.UI.automation.manager;

import web.UI.automation.cucumber.TestContext;
import web.UI.automation.enums.DriverType;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.safari.SafariDriver;

public class TestDriverManager {

    private WebDriver driver;
    private DriverType driverType;

    private TestContext testContext;

    public TestDriverManager(TestContext context) {
        this.testContext = context;
    }

    public WebDriver getDriver() {
        if (driver == null) {
            driver = createDriver();
        }
        return driver;
    }

    private WebDriver createDriver() {
        driverType = getDeviceType();
        switch (driverType) {
            case CHROME:
                driver = createChromeDriver();
                driver.manage().window().maximize();
                break;
            case SAFARI:
                driver = createSafariDriver();
                driver.manage().window().maximize();
                break;
            default:
                break;
        }

        if (driver == null) {
            Assert.fail("Unable to initiate driver");
        }

        return driver;
    }

    private WebDriver createSafariDriver() {
        driver = new SafariDriver();
        return driver;
    }

    public DriverType getDeviceType() {
        String platformName = System.getProperty("properties.browser").toLowerCase();
        switch (platformName) {
            case "chrome":
                return DriverType.CHROME;
            case "safari":
                return DriverType.SAFARI;
            case "android":
                return DriverType.ANDROID;
            case "ios":
                return DriverType.IOS;
            default:
                throw new RuntimeException("Platform Name Key value in System.properties is not matching : " + platformName);
        }
    }

    public void closeDriver() {
        if (driver != null) {
            driver.manage().deleteAllCookies();
            driver.close();
            driver.quit();
        }
    }

    private WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions desiredCapabilities = new ChromeOptions();
        desiredCapabilities.addArguments("headless", "window-size=1920,1080");
        desiredCapabilities.addArguments("--no-sandbox");
        desiredCapabilities.addArguments("--disable-dev-shm-usage");
        desiredCapabilities.addArguments("--incognito");
        //driver = new ChromeDriver(desiredCapabilities);
        driver = new ChromeDriver();
       // driver=driver.manage().window().maximize();
        return driver;
    }

    public DriverType getDriverType() {
        return driverType;
    }

}