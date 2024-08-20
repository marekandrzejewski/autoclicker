package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClickListener implements NativeMouseListener {
    private Interface ui;

    public ClickListener(Interface ui) {
        this.ui = ui;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        String coordinates = "X: " + x + ", Y: " + y;
        ui.addCoordinates(coordinates);  // Dodanie współrzędnych do listy
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        // Nieużywane
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        // Nieużywane
    }

    // Metoda do włączania nasłuchu
    public void startListening() {
        try {
            // Usuwanie poprzednich nasłuchiwaczy (jeśli istnieją), by uniknąć wielokrotnego rejestrowania
            GlobalScreen.removeNativeMouseListener(this);

            // Rejestracja hooka do nasłuchiwania zdarzeń myszy
            GlobalScreen.registerNativeHook();
            GlobalScreen.addNativeMouseListener(this);

            // Wyłączenie logów z biblioteki jnativehook
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
    }

    // Metoda do wyłączania nasłuchu
    public void stopListening() {
        try {
            // Odrejestrowanie hooka oraz usunięcie nasłuchiwacza
            GlobalScreen.removeNativeMouseListener(this);
            GlobalScreen.unregisterNativeHook();
        } catch (NativeHookException ex) {
            ex.printStackTrace();
        }
    }
}
