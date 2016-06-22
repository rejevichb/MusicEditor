package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyEvent.*;
import java.awt.event.KeyListener;
import java.util.Map;

import static java.awt.event.KeyEvent.VK_DOWN;
import static java.awt.event.KeyEvent.VK_KP_DOWN;
import static java.awt.event.KeyEvent.VK_LEFT;
import static java.awt.event.KeyEvent.VK_RIGHT;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_UP;

/**
 * Concrete KeyboardHandler
 */
public class KeyboardHandler implements KeyListener {

    Map<Character, Runnable> keyTyped;
    Map<Integer, Runnable> keyPressed;
    Map<Integer, Runnable> keyReleased;

    public KeyboardHandler() {
        keyTyped = new HashMap<>();
        keyPressed = new HashMap<>();
        keyReleased = new HashMap<>();

        Runnable pressDown = new Runnable() {
            @Override
            public void run() {
                //
            }
        };
        Runnable pressUp = new Runnable() {
            @Override
            public void run() {
                //
            }
        };
        Runnable pressLeft = new Runnable() {
            @Override
            public void run() {
                //
            }
        };
        Runnable pressRight = new Runnable() {
            @Override
            public void run() {
                //
            }
        };
        Runnable releaseDown = new Runnable() {
            @Override
            public void run() {
                //
            }
        };
        Runnable releaseUp = new Runnable() {
            @Override
            public void run() {
                //
            }
        };
        Runnable releaseLeft = new Runnable() {
            @Override
            public void run() {
                //
            }
        };
        Runnable releaseRight = new Runnable() {
            @Override
            public void run() {
                //
            }
        };

        keyPressed.put(VK_DOWN, pressDown);
        keyPressed.put(VK_UP, pressUp);
        keyPressed.put(VK_LEFT, pressLeft);
        keyPressed.put(VK_RIGHT, pressRight);

        keyReleased.put(VK_DOWN, releaseDown);
        keyReleased.put(VK_UP, releaseUp);
        keyReleased.put(VK_LEFT, releaseLeft);
        keyReleased.put(VK_RIGHT, releaseRight);

    }

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


