package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClickListen implements NativeMouseListener {
    private Interface ui;

    public ClickListen(Interface ui) {
        this.ui = ui;
    }

    @Override
    public void nativeMouseClicked(NativeMouseEvent e) {
        // Pobieranie współrzędnych kliknięcia
        int x = e.getX();
        int y = e.getY();
        String coordinates = "Współrzędne kliknięcia: (" + x + ", " + y + ")";

        // Dodawanie współrzędnych do listy w UI
        ui.addCoordinates(coordinates);
    }

    @Override
    public void nativeMousePressed(NativeMouseEvent e) {
        // Niepotrzebne dla tego przypadku, możemy pozostawić puste
    }

    @Override
    public void nativeMouseReleased(NativeMouseEvent e) {
        // Niepotrzebne dla tego przypadku, możemy pozostawić puste
    }

    public void startListening() {
        // Wyłączenie zbędnych komunikatów logowania z biblioteki NativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Rejestracja nasłuchiwacza zdarzeń myszy
        GlobalScreen.addNativeMouseListener(this);

        // Inicjalizacja NativeHook
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("Nie można zarejestrować natywnego hooka");
            System.exit(1);
        }
    }
}
