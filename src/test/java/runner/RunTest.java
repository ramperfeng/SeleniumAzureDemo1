package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.env.OriginTrackedMapPropertySource;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.io.InputStreamResource;

import java.io.IOException;
import java.util.Map;
import java.util.Objects;


@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    glue = "web.UI.automation.stepDefinitions",
    plugin = {
        "html:target/reports.html", "json:target/cucumber-reports/Cucumber.json"
    }
    ,tags = "@dropBox"
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
}
