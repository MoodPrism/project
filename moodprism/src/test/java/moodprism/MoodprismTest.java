package moodprism;

import java.io.IOException;

import org.junit.ClassRule;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.context.EmbeddedKafka;
import org.springframework.kafka.test.rule.EmbeddedKafkaRule;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = MoodprismTest.class)
public class MoodprismTest {
    
    
    @Test
    public void contextLoads() throws IOException {
        System.out.println("HELLOOOOOOOOOOOO");
    }
  
}
