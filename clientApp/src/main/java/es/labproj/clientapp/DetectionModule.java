package es.labproj.clientapp;

import java.util.Timer;
import java.util.TimerTask;
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

	private static final String TOPIC_NAME = "moodprismTopic";
	private static Producer<String, String> producer;
	private static boolean ticking = false;
	private static Properties properties;
	private static String username;
	private static int freq;
	private static int i;

    public static void main(String[] args) throws Exception
    {		
		String kafkaAddress = "localhost:9092";
		if(args.length == 2) {username = args[0]; kafkaAddress = args[1];}
		else if(args.length == 1) {username = args[0];}
		else if(args.length == 0) {username = "John";}

		i = 0;
		freq = 5000;
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
	
	private void sendEvent()
	{
		if(!ticking)
		{
			ticking = true;
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {@Override public synchronized void run() {timeOut();}};
			timer.schedule(task, freq);;
		}
	}

	private void timeOut()
	{
		System.out.println("Timer sending record");
		JSONObject obj = new JSONObject();
		obj.put("name", username);
		obj.put("keys", Integer.toString(i));
		String recordValue = obj.toString();
		ProducerRecord<String, String> record = new ProducerRecord<>(TOPIC_NAME, null, recordValue);
		producer.send(record);
		producer.flush();
		i = 0;
		ticking = false;
		sendEvent();
	}

    public void nativeKeyPressed(NativeKeyEvent key)
	{
		i++;
		sendEvent();
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(key.getKeyCode()));
		if (key.getKeyCode() == NativeKeyEvent.VC_ESCAPE)
		{
			try {GlobalScreen.unregisterNativeHook();}
			catch(Exception e) {System.out.println(e);}
		}
	}
	
	public void nativeKeyReleased(NativeKeyEvent e)	{}
	
	public void nativeKeyTyped(NativeKeyEvent e) {}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {System.out.println("Mouse Clicked: " + e.getClickCount());}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {System.out.println("Mouse Pressed: " + e.getButton());}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {System.out.println("Mouse Released: " + e.getButton());}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());}
	
	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {System.out.println("Mouse Wheel Moved: " + e.getWheelRotation());}	
}