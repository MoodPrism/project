package es.labproj.moodprism;

import java.util.HashMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MoodprismController {
    private static final Logger log = LoggerFactory.getLogger(MoodprismController.class);
    private HashMap<String, String> userMap = new HashMap<>();

    @Autowired
    kafkaConsumer consumer;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public @ResponseBody String index() throws ParseException
    {
        log.info("Returning request!");
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(consumer.getMessages());
        userMap.put(json.get("name").toString(), json.get("keys").toString());
        JSONObject obj = new JSONObject();
        obj.putAll(userMap);
        return obj.toJSONString();
    }

}