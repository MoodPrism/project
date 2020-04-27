package app;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;
import org.jnativehook.mouse.NativeMouseWheelEvent;
import org.jnativehook.mouse.NativeMouseWheelListener;

import java.util.logging.Level;
import java.util.logging.Logger;




public class App implements NativeKeyListener, NativeMouseInputListener, NativeMouseWheelListener
{
			
	public void nativeKeyPressed(NativeKeyEvent e)
	{
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE)
		{
			try {
				GlobalScreen.unregisterNativeHook();
			} catch (NativeHookException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void nativeKeyReleased(NativeKeyEvent e)
	{
		//Something
	}
	
	public void nativeKeyTyped(NativeKeyEvent e)
	{
		//Something
	}

	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		System.out.println("Mouse Clicked: " + e.getClickCount());
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		System.out.println("Mouse Pressed: " + e.getButton());
		
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		System.out.println("Mouse Released: " + e.getButton());
		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		System.out.println("Mouse Dragged: " + e.getX() + ", " + e.getY());
		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		System.out.println("Mouse Moved: " + e.getX() + ", " + e.getY());
		
	}
	
	@Override
	public void nativeMouseWheelMoved(NativeMouseWheelEvent e) {
		System.out.println("Mouse Wheel Moved: " + e.getWheelRotation());
		
	}
	
	public static void main(String[] args)
	{
		Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
		logger.setLevel(Level.WARNING);
		try
		{
			GlobalScreen.registerNativeHook();
		}
		catch (NativeHookException ex)
		{
			System.err.println("There was a problem registering the native hook.");
			System.err.println(ex.getMessage());
			System.exit(1);
		}
		GlobalScreen.addNativeKeyListener(new App());
		GlobalScreen.addNativeMouseListener(new App());
		GlobalScreen.addNativeMouseMotionListener(new App());
		GlobalScreen.addNativeMouseWheelListener(new App());
	}

	
}

