package org.example;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Użycie wyniku z metody getInstance, nawet jeśli jest to tylko dla inicjalizacji
            Interface interfaceInstance = Interface.getInstance();
            // Można dodatkowo dodać kod, jeśli potrzebujesz odniesienia do tego obiektu
        });
    }
}
