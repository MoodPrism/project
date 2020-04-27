package es.labproj.moodprism;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;

public class GlobalKeyListenerExample {

    public void nativeKeyPressed(NativeKeyEvent e) throws NativeHookException {
		System.out.println("Key Pressed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));

		if (e.getKeyCode() == NativeKeyEvent.VC_ESCAPE) {
            GlobalScreen.unregisterNativeHook();
		}
	}

	public void nativeKeyReleased(NativeKeyEvent e) {
		System.out.println("Key Released: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
	}

	public void nativeKeyTyped(NativeKeyEvent e) {
        System.out.println("Key Typed: " + NativeKeyEvent.getKeyText(e.getKeyCode()));
        
    }
    
}


