package es.labproj.clientapp;

import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;
import org.json.simple.JSONObject;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class DetectionModule implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener
{

	private static Producer<String, String> producer;
	private static Properties properties;
	private static String username;
	private static int count = 0;

    public static void main(String[] args) throws Exception
    {		
		String kafkaAddress = "192.168.160.103:9092";
		if(args.length == 2) {username = args[0]; kafkaAddress = args[1];}
		else if(args.length == 1) {username = args[0];}
		else if(args.length == 0) {username = "John";}

        properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaAddress);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		producer = new KafkaProducer<>(properties);
		
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);

		try {GlobalScreen.registerNativeHook();}
		catch(NativeHookException ex)
		{
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}

		GlobalScreen.addNativeKeyListener(new DetectionModule());
		GlobalScreen.addNativeMouseListener(new DetectionModule());
		GlobalScreen.addNativeMouseMotionListener(new DetectionModule());
		GlobalScreen.addNativeMouseWheelListener(new DetectionModule());
	}

    public void nativeKeyPressed(NativeKeyEvent key)
	{
		System.out.println("Sending key record");
		JSONObject obj = new JSONObject();
		obj.put("name", username);
		obj.put("keys", NativeKeyEvent.getKeyText(key.getKeyCode()));
		String recordValue = obj.toString();
		ProducerRecord<String, String> record = new ProducerRecord<>("moodprismTopic", null, recordValue);
		ProducerRecord<String, String> record2 = new ProducerRecord<>("moodTopic", null, recordValue);
		producer.send(record);
		producer.send(record2);
		producer.flush();
		if (key.getKeyCode() == NativeKeyEvent.VC_ESCAPE)
		{
			try {GlobalScreen.unregisterNativeHook();}
			catch(Exception e) {System.out.println(e);}
		}
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e)
	{
		count++;
		if(count == 100)
		{
			System.out.println("Sending mouse record");
			JSONObject obj = new JSONObject();
			obj.put("name", username);
			obj.put("mouse", "(" + e.getX() + ", " + e.getY() + ")");
			String recordValue = obj.toString();
			ProducerRecord<String, String> record = new ProducerRecord<>("moodprismTopic", null, recordValue);
			ProducerRecord<String, String> record2 = new ProducerRecord<>("moodTopic", null, recordValue);
			producer.send(record);
			producer.send(record2);
			producer.flush();
			count = 0;
		}
	}
	
	public void nativeKeyReleased(NativeKeyEvent e)	{}
	
	public void nativeKeyTyped(NativeKeyEvent e) {}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {}
	
	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {}	
}