package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseListener;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ClickListen {
    private static boolean isInitialized = false;  // Track if initialized
    private static NativeMouseListener mouseListener;

    public static void initialize() {
        if (isInitialized) {
            return;  // Already initialized
        }

        // Wyłączenie zbędnych komunikatów logowania z biblioteki NativeHook
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        // Tworzenie nowego nasłuchiwacza myszy
        mouseListener = new NativeMouseListener() {
            @Override
            public void nativeMouseClicked(NativeMouseEvent e) {
                // Pobieranie współrzędnych kliknięcia
                int x = e.getX();
                int y = e.getY();

                // Dodanie współrzędnych kliknięcia do listy w klasie Auto
                Auto.getInstance().addClickCoordinates("(" + x + ", " + y + ")");
            }

            @Override
            public void nativeMousePressed(NativeMouseEvent e) {
                // Niepotrzebne dla tego przypadku, możemy pozostawić puste
            }

            @Override
            public void nativeMouseReleased(NativeMouseEvent e) {
                // Niepotrzebne dla tego przypadku, możemy pozostawić puste
            }
        };

        // Dodanie nasłuchiwacza zdarzeń myszy
        GlobalScreen.addNativeMouseListener(mouseListener);

        // Inicjalizacja NativeHook
        try {
            GlobalScreen.registerNativeHook();
            isInitialized = true;
        } catch (NativeHookException ex) {
            System.err.println("Nie można zarejestrować natywnego hooka");
            System.exit(1);
        }
    }

    public static void deinitialize() {
        if (!isInitialized) {
            return;  // Not initialized or already deinitialized
        }

        try {
            GlobalScreen.removeNativeMouseListener(mouseListener);
            GlobalScreen.unregisterNativeHook();
            isInitialized = false;
        } catch (NativeHookException ex) {
            System.err.println("Nie można odłączyć natywnego hooka");
        }
    }
}
