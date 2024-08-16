package org.example;

import javax.swing.*;

public class Run {
    private JButton runButton;

    public Run() {
        // Inicjalizacja przycisku "Run Autoclicker"
        runButton = new JButton("Run Autoclicker");
        runButton.setVisible(false); // Domyślnie ukryty

        // Dodanie akcji do przycisku
        runButton.addActionListener(e -> {
            // Kod uruchamiający Autoclicker (do zaimplementowania)
            System.out.println("Autoclicker started!");
        });
    }

    public JButton getRunButton() {
        return runButton;
    }

    public void setRunButtonVisible(boolean visible) {
        runButton.setVisible(visible);
    }
}
