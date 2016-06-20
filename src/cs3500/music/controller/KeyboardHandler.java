package cs3500.music.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Concrete KeyboardHandler
 */
public class KeyboardHandler implements KeyListener {

    Map<Integer, Runnable> typed;
    Map<Integer, Runnable> pressed;
    Map<Integer, Runnable> released;

    public KeyboardHandler() {
        this.typed = new HashMap<>();
        this.pressed = new HashMap<>();
        this.released = new HashMap<>();
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
