package es.labproj.clientapp;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class TestingConsumer
{

	private static final String TOPIC_NAME = "moodprismTopic";
	private static Properties properties;

    public static void main(String[] args) throws Exception
    {		
		properties = new Properties();
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
        properties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 1000);
		
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(properties);
		kafkaConsumer.subscribe(Collections.singletonList(TOPIC_NAME));
        try
        {
            while (true)
            {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(5000));
                System.out.println("Fetched " + records.count() + " records");
                for(ConsumerRecord<String, String> record : records) {System.out.println("Received: " + record.key() + ":" + record.value());}
                kafkaConsumer.commitSync();
                TimeUnit.MILLISECONDS.sleep(100);
            }
        }
        catch(Exception e) {System.out.println(e);}
        finally {kafkaConsumer.close();}
	}
}