package runner;



import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;

import com.vimalselvam.cucumber.listener.Reporter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.io.InputStreamResource;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.Objects;


@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = "web.UI.automation.stepDefinitions"
    //plugin = { "summary", "json:target/cucumber-json.json",
   // "tech.grasshopper.AllureCucumberMappingPlugin:target/cucumber-allure.json" }
   //plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/cucumber-reports/report.html"}
    //plugin = {"com.cucumber.listener.ExtentCucumberFormatter:target/html/ExtentReport.html"}
    ,plugin={"com.aventstack.extentreports.cucumber.adapter.ExtentCucumberAdapter:"}
    //plugin = {"pretty","com.vimalselvam.cucumber.listener.ExtentCucumberFormatter:path/report.html","html:test-output","json:json_output/cucumber.json","junit:junit_output/cucumber.xml"} //to generate diff types of reporting
    ,monochrome =true //display the console output in a proper readable format
    ,strict=true //it will check if any step is not defined in step definition file
    ,dryRun = false,

    tags = "@dropBox"

)

public class RunTest {

    private static final Logger logger = LoggerFactory.getLogger(RunTest.class);

    @BeforeClass
    public static void setUp() {
        try {
            loadConfiguration(System.getProperty("environ", "qa"));
        } catch (Exception e) {
            logger.debug(e.getMessage());
            throw new RuntimeException(e);
        }

    }

    private static void loadConfiguration(final String environ) throws IOException {

        logger.info("Logs Initiated for test environment:" + environ);
        final String propertySourceName = "basic";
        new YamlPropertySourceLoader().load(propertySourceName, new InputStreamResource(
                        Objects.requireNonNull(RunTest.class.getClassLoader().getResourceAsStream("environ//config-" + environ + ".yml"))))
                .stream()
                .filter(propertySource -> propertySource instanceof OriginTrackedMapPropertySource)
                .map(propertySource -> (OriginTrackedMapPropertySource) propertySource)
                .filter(originTrackedMapPropertySource -> originTrackedMapPropertySource.getName().equals(propertySourceName))
                .forEach(
                        originTrackedMapPropertySource -> {
                            for (Map.Entry<String, Object> props : originTrackedMapPropertySource.getSource().entrySet()) {
//                        logger.debug("property `{}` = `{}`", props.getKey(), props.getValue().toString());
                                System.setProperty(props.getKey(), props.getValue().toString());
                            }
                        }
                );
    }

    @AfterClass
    public static void setup()
    {
        Reporter.loadXMLConfig(new File("src/test/resources/extent-config.xml"));
        //Reporter.setSystemInfo("Test User", System.getProperty("user.name"));
        Reporter.setSystemInfo("User Name", "AJ");
        Reporter.setSystemInfo("Application Name", "Test App ");
        Reporter.setSystemInfo("Operating System Type", System.getProperty("os.name").toString());
        Reporter.setSystemInfo("Environment", "Production");
        Reporter.setTestRunnerOutput("Test Execution Cucumber Report");
    }

}
