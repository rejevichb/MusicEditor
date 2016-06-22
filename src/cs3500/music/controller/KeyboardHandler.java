package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Map;

/**
 * Concrete KeyboardHandler
 */
public class KeyboardHandler implements KeyListener {

    Map<Character, Runnable> keyTyped;
    Map<Integer, Runnable> keyPressed;
    Map<Integer, Runnable> keyReleased;


//    public KeyboardHandler() {
//        keyTyped = new HashMap<>();
//        keyPressed = new HashMap<>();
//        keyReleased = new HashMap<>();
//    }

    /**
     * Set the map for key typed events. Key typed events in Java Swing are characters
     */

    public void setKeyTypedMap(Map<Character, Runnable> map) {
        keyTyped = map;
    }

    /**
     * Set the map for key pressed events. Key pressed events in Java Swing are integer codes
     */

    public void setKeyPressedMap(Map<Integer, Runnable> map) {
        keyPressed = map;
    }

    /**
     * Set the map for key released events. Key released events in Java Swing are integer codes
     */

    public void setKeyReleasedMap(Map<Integer, Runnable> map) {
        keyReleased = map;
    }

    /**
     * This is called when the view detects that a key has been typed. Find if anything has been
     * mapped to this key character and if so, execute it
     */

    @Override
    public void keyTyped(KeyEvent e) {
        if (keyTyped.containsKey(e.getKeyChar()))
            keyTyped.get(e.getKeyChar()).run();
    }

    /**
     * This is called when the view detects that a key has been pressed. Find if anything has been
     * mapped to this key code and if so, execute it
     */
    @Override
    public void keyPressed(KeyEvent e) {
        if (keyPressed.containsKey(e.getKeyCode()))
            keyPressed.get(e.getKeyCode()).run();
    }

    /**
     * This is called when the view detects that a key has been released. Find if anything has been
     * mapped to this key code and if so, execute it
     */
    @Override
    public void keyReleased(KeyEvent e) {
        if (keyReleased.containsKey(e.getKeyCode()))
            keyReleased.get(e.getKeyCode()).run();
    }
}


