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
    private HashMap<String, HashMap<String, String>> userMap = new HashMap<>();

    @Autowired
    kafkaConsumer consumer;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/")
    public @ResponseBody String index() throws ParseException
    {
        JSONParser parser = new JSONParser();
        JSONObject json = (JSONObject) parser.parse(consumer.getMessages());
        if(json.containsKey("keys")) {updateMap(json.get("name").toString(), "keys", json.get("keys").toString());}
        else if(json.containsKey("mouse")) {updateMap(json.get("name").toString(), "mouse", json.get("mouse").toString());}
        else if(json.containsKey("mood")) {updateMap(json.get("name").toString(), "mood", json.get("mood").toString());}
        JSONObject obj = new JSONObject();
        obj.putAll(userMap);
        return obj.toJSONString();
    }

    private void updateMap(String name, String key, String value)
    {
        HashMap<String, String> temp = new HashMap<>();
        if(!userMap.containsKey(name)) {userMap.put(name, new HashMap<String, String>());}
        temp = userMap.get(name);
        temp.putIfAbsent("keys", "NA");
        temp.putIfAbsent("mouse", "NA");
        temp.putIfAbsent("mood", "NA");
        temp.put(key, value);
        userMap.put(name, temp);
    }
}