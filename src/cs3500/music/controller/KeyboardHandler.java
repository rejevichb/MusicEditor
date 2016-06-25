package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Concrete KeyboardHandler
 */
public class KeyboardHandler implements KeyListener {

    Map<Character, Runnable> keyTyped;

    public KeyboardHandler() {
        keyTyped = new HashMap<>();
    }

    /**
     * Set the map for key typed events. Key typed events in Java Swing are characters
     */
    public void setKeyTypedMap(Map<Character, Runnable> map) {
        keyTyped = map;
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
        //void impl
    }

    /**
     * This is called when the view detects that a key has been released. Find if anything has been
     * mapped to this key code and if so, execute it
     */
    @Override
    public void keyReleased(KeyEvent e) {
        //void impl
    }
}
