package org.example;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.mouse.NativeMouseListener;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Auto extends JPanel {
    private JButton startListeningButton;
    private JButton stopListeningButton;
    private JButton saveButton;
    private JButton clearListButton;
    private boolean isListening = false;
    private Interface ui;

    public Auto(Interface ui) {
        this.ui = ui;

        setLayout(new FlowLayout());

        // Inicjalizacja przycisków
        startListeningButton = new JButton("Start Listening");
        stopListeningButton = new JButton("Stop Listening");
        saveButton = new JButton("Save");
        clearListButton = new JButton("Clear List");

        // Dodanie przycisków do panelu
        add(startListeningButton);
        add(stopListeningButton);
        add(saveButton);
        add(clearListButton);

        // Nasłuchiwacz dla przycisku "Start Listening"
        startListeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startListening();
            }
        });

        // Nasłuchiwacz dla przycisku "Stop Listening"
        stopListeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopListening();
            }
        });

        // Nasłuchiwacz dla przycisku "Clear List"
        clearListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clearClickList();
            }
        });

        // Ustawienie początkowego stanu przycisków
        stopListeningButton.setEnabled(false);  // Stop Listening wyłączony na start
        clearListButton.setEnabled(true);       // Clear List dostępny, gdy nie nasłuchujemy
    }

    // Metoda włączająca nasłuchiwanie kliknięć
    private void startListening() {
        if (!isListening) {
            // Wyłączanie zbędnych komunikatów logowania z biblioteki NativeHook
            Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
            logger.setLevel(Level.OFF);

            // Inicjalizacja nasłuchiwacza
            NativeMouseListener mouseListener = new ClickListen(ui);
            try {
                GlobalScreen.registerNativeHook();
                GlobalScreen.addNativeMouseListener(mouseListener);
                System.out.println("Nasłuchiwanie włączone.");
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }

            // Ustawienie stanu przycisków
            isListening = true;
            startListeningButton.setEnabled(false);  // Dezaktywuj Start Listening
            stopListeningButton.setEnabled(true);    // Aktywuj Stop Listening
            clearListButton.setEnabled(false);       // Dezaktywuj Clear List
        }
    }

    // Metoda wyłączająca nasłuchiwanie kliknięć
    private void stopListening() {
        if (isListening) {
            try {
                GlobalScreen.unregisterNativeHook();
                System.out.println("Nasłuchiwanie wyłączone.");
            } catch (NativeHookException ex) {
                ex.printStackTrace();
            }

            // Ustawienie stanu przycisków
            isListening = false;
            startListeningButton.setEnabled(true);   // Aktywuj Start Listening
            stopListeningButton.setEnabled(false);   // Dezaktywuj Stop Listening
            clearListButton.setEnabled(true);        // Aktywuj Clear List
        }
    }

    // Metoda czyszcząca listę kliknięć
    private void clearClickList() {
        ui.clearClickList();
        System.out.println("Lista kliknięć została wyczyszczona.");
    }
}
