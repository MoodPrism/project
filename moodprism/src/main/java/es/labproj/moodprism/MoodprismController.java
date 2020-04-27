package es.labproj.moodprism;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MoodprismController
{
    private static final Logger log = LoggerFactory.getLogger(MoodprismController.class);
    
    @Autowired
    kafkaConsumer consumer;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public @ResponseBody String index()
    {
        log.info("Returning request!");
        String tmp = consumer.getMessages();
        return tmp;
    }

}