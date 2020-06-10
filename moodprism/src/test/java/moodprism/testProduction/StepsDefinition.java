package moodprism.testProduction;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import cucumber.api.PendingException;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import es.labproj.moodprism.kafkaConsumer;
import es.labproj.moodprism.kafkaProducer;
import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.json.JSONObject;

//TESTES DE PRODUÇÃO
/*
public class StepsDefinition {
    WebDriver driver;
    private static Properties properties;
    private static Producer<String, String> producer;
    private static final String TOPIC_NAME = "moodprismTopic"; 
    
 
    @Given("^I have the application running$")
    public void i_have_the_application_running() throws Throwable {
        System.setProperty("webdriver.gecko.driver", "/home/mariana/geckodriver");
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get("http://localhost:8080");
    }

    @When("^I press any key \"([^\"]*)\"$")
    public void i_press_any(String word) throws Throwable {
        String kafkaAddress = "localhost:9092";
        properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        producer = new KafkaProducer<>(properties);
        JSONObject obj = new JSONObject();
        obj.put("name", "Test Cucumber");
        obj.put("keys", word);
        String recordValue = obj.toString();
        ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, null, recordValue);
        producer.send(record);
        producer.flush();
    }


    @And("^the page refreshes$")
    public void the_page_refreshes() throws Throwable {
        try{
            Thread.sleep(10*1000);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        for(int i=0; i<15; i++){
            driver.get(driver.getCurrentUrl());
        }
    }

    @Then("^The key should be on the screen$")
    public void the_key_should_be_on_the_screen() throws Throwable {
       /* properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);

        KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
        kafkaConsumer.subscribe(Collections.singletonList(TOPIC_NAME));
        
        try{
            ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(5000));
            System.out.println("Fetched " + records.count() + " records");
            for(ConsumerRecord<String, String> record : records) {System.out.println("Received: " + record.key() + ":" + record.value());}
            kafkaConsumer.commitSync();
            TimeUnit.MILLISECONDS.sleep(100);
        }
        catch(Exception e) {System.out.println(e);}
        finally {kafkaConsumer.close();}*/
   /* }
    
    //***OUTRA FEATURE****/

  /*  @Given("^I write as I usually do$")
    public void i_write_as_I_usually_do() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }

    @Then("^the result on the screen should indicate normal state of mind$")
    public void the_result_on_the_screen_should_indicate_normal_state_of_mind() throws Throwable {
        // Write code here that turns the phrase above into concrete actions
        throw new PendingException();
    }
    
  /*  private String getConsumerRecords(){
        return consumer.getMessages();
    }*/
/*
}
*/