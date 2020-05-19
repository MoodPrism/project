package moodprism;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.labproj.moodprism.kafkaConsumer;
import es.labproj.moodprism.kafkaProducer;

public class StepsDefinition {
    WebDriver driver;
    private final kafkaProducer producer = new kafkaProducer();
    private final kafkaConsumer consumer = new kafkaConsumer();
    
 
    @Given("^I have the application running$")
    public void i_have_the_application_running() throws Throwable {
        System.setProperty("webdriver.gecko.driver", "/home/mariana/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080");
    }

    @When("^I press any key \"([^\"]*)\"$")
    public void i_press_any(String arg1) throws Throwable {
        producer.sendMsg(arg1);
    }


    @And("^the page refreshes$")
    public void the_page_refreshes() throws Throwable {
        System.out.println("Refresh page");
    }

    @Then("^The key should be on the screen$")
    public void the_key_should_be_on_the_screen() throws Throwable {
        System.out.println("I press the key: "+getConsumerRecords());
    }
    
    //***OUTRA FEATURE****/

    @Given("^I write as I usually do$")
    public void i_write_as_I_usually_do() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the result on the screen should indicate normal state of mind$")
    public void the_result_on_the_screen_should_indicate_normal_state_of_mind() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    
    private String getConsumerRecords(){
        return consumer.getMessages();
    }

}
