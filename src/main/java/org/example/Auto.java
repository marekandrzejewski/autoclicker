package org.example;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Auto extends JPanel {
    private JButton startListeningButton;
    private JButton stopListeningButton;
    private JButton saveButton;
    private JButton clearListButton;
    private ClickListener clickListener;

    public Auto(Interface ui) {
        // Tworzenie przycisków
        startListeningButton = new JButton("Start Listening");
        stopListeningButton = new JButton("Stop Listening");
        saveButton = new JButton("Save");
        clearListButton = new JButton("Clear List");

        // Początkowo wyłączamy przycisk stopListening, bo nasłuch nie jest aktywny
        stopListeningButton.setEnabled(false);
        clearListButton.setEnabled(true);  // Clear List aktywny na starcie

        // Inicjalizujemy ClickListener z referencją do interfejsu UI
        clickListener = new ClickListener(ui);

        // Akcja dla przycisku "Start Listening"
        startListeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Rozpoczęcie nasłuchiwania kliknięć
                clickListener.startListening();

                // Dezaktywacja przycisku Start Listening, aktywacja Stop Listening
                startListeningButton.setEnabled(false);
                stopListeningButton.setEnabled(true);
                clearListButton.setEnabled(false);  // Wyłączamy Clear List podczas nasłuchiwania
            }
        });

        // Akcja dla przycisku "Stop Listening"
        stopListeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Zatrzymanie nasłuchiwania kliknięć
                clickListener.stopListening();

                // Aktywacja przycisku Start Listening, dezaktywacja Stop Listening
                startListeningButton.setEnabled(true);
                stopListeningButton.setEnabled(false);
                clearListButton.setEnabled(true);  // Włączamy Clear List po zakończeniu nasłuchiwania
            }
        });

        // Akcja dla przycisku "Clear List"
        clearListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ui.clearClickList();  // Czyści listę kliknięć
            }
        });

        // Dodanie przycisków do panelu
        add(startListeningButton);
        add(stopListeningButton);
        add(saveButton);
        add(clearListButton);
    }
}
