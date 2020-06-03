package es.labproj.moodprism;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class MoodprismScheduler
{
    @Autowired
    RestTemplate restTemplate;
    
    @Autowired
    kafkaProducer producer;

    private static final Logger log = LoggerFactory.getLogger(MoodprismScheduler.class);
    
    private int i = 0;

    @Scheduled(fixedRate = 500)
    public void updateDataRepository()
    {         
        //log.info("Generating test input...");
        JSONObject obj = new JSONObject();
        obj.put("name", "Mariana");
        Random r = new Random();
        char c = (char)(r.nextInt(26) + 'a');
        String t = "" + Character.toUpperCase(c);
        obj.put("keys", t);
        int randomNum = ThreadLocalRandom.current().nextInt(0, 2000);
        int randomNum2 = ThreadLocalRandom.current().nextInt(0, 2000);
        obj.put("mouse", "(" + Integer.toString(randomNum) + ", " + Integer.toString(randomNum2) + ")");
        obj.put("mood", "normal");
        producer.sendMsg(obj.toString());  
    }
}
