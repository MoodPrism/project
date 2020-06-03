package es.labproj.moodapp;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Properties;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
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

public class MoodApp
{

	private static HashMap<String, HashMap<String, String>> userMap = new HashMap<>();
	private static Producer<String, String> producer;
	private static Properties consumerProperties;
	private static Properties producerProperties;

    public static void main(String[] args) throws Exception
    {		
		String kafkaAddress = "localhost:9092";
		if(args.length == 1) {kafkaAddress = args[0];}

		consumerProperties = new Properties();
		consumerProperties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
		consumerProperties.put(ConsumerConfig.GROUP_ID_CONFIG, "group_id");
        consumerProperties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        consumerProperties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        consumerProperties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);
		consumerProperties.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, 5);
		KafkaConsumer<String, String> kafkaConsumer = new KafkaConsumer<>(consumerProperties);
		kafkaConsumer.subscribe(Collections.singletonList("moodTopic"));
		
        producerProperties = new Properties();
        producerProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        producerProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producerProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producer = new KafkaProducer<>(producerProperties);
		
		try
        {
            while (true)
            {
                ConsumerRecords<String, String> records = kafkaConsumer.poll(Duration.ofMillis(10000));
                kafkaConsumer.commitSync();
                System.out.println("Fetched " + records.count() + " records");
				for(ConsumerRecord<String, String> record : records)
				{
					JSONParser parser = new JSONParser();
					JSONObject json = (JSONObject) parser.parse(record.value());
					System.out.println(record.value());
					if(json.containsKey("keys")) {updateMap(json.get("name").toString(), "keys", json.get("keys").toString());}
					else if(json.containsKey("mouse")) {updateMap(json.get("name").toString(), "mouse", json.get("mouse").toString());}
				}
                TimeUnit.MILLISECONDS.sleep(1000);
            }
        }
        catch(Exception e) {System.out.println(e);}
        finally {kafkaConsumer.close();}
	}

	private static void updateMap(String name, String key, String value)
    {
		Timer timer = new Timer();
		TimerTask task = new TimerTask() {@Override public synchronized void run() {updateMap(name, "mood", "slow");}};
		timer.schedule(task, 2000);
		System.out.println("updating mood");
        HashMap<String, String> temp = new HashMap<>();
        if(!userMap.containsKey(name)) {userMap.put(name, new HashMap<String, String>());}
        temp = userMap.get(name);
        temp.putIfAbsent("keys", "NA");
        temp.putIfAbsent("mouse", "NA");
		temp.putIfAbsent("mood", "NA");
		if(key.equals("keys"))
		{
			timer.cancel();
			if(temp.get("keys").equals(value)) {temp.put("mood", "stressed");}
		}
		if(key.equals("mouse"))
		{
			timer.cancel();
			temp.put("mood", "normal");
		}
		if(key.equals("mood"))
		{
			timer.cancel();
			temp.put("mood", "slow");
		}
        temp.put(key, value);
		userMap.put(name, temp);		
		JSONObject obj = new JSONObject();
		obj.put("name", name);
		obj.put("mood", temp.get("mood"));
		String recordValue = obj.toString();
		ProducerRecord<String, String> record = new ProducerRecord<>("moodprismTopic", null, recordValue);
		producer.send(record);
		producer.flush();
    }
}