import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/java/alugarFilme.feature",
        plugin = {"pretty", "html:target/report-html", "json:target/report.json"}
                )
public class RunnerTest {
}
