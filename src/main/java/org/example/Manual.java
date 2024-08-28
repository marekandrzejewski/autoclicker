package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class Manual extends JPanel {
    private List<ManualObject> manualList;  // Lista obiektów typu ManualObject
    private JList<String> displayList;      // Lista do wyświetlania obiektów manualList

    public Manual() {
        // Inicjalizacja listy obiektów ManualObject
        manualList = new ArrayList<>();

        // Ustawienie layoutu
        setLayout(new BorderLayout());

        // Przykład interfejsu użytkownika dla panelu Manual
        JLabel label = new JLabel("Manual Mode");
        add(label, BorderLayout.NORTH);

        // JList do wyświetlania obiektów manualList
        displayList = new JList<>();

        // Tworzenie JScrollPane, który zawiera listę
        JScrollPane scrollPane = new JScrollPane(displayList);

        // Ustawienie stałej szerokości 500 pikseli dla JScrollPane
        Dimension fixedSize = new Dimension(500, scrollPane.getPreferredSize().height);
        scrollPane.setPreferredSize(fixedSize);
        scrollPane.setMinimumSize(fixedSize);
        scrollPane.setMaximumSize(fixedSize);

        add(scrollPane, BorderLayout.CENTER);

        // Dodanie nasłuchiwacza na podwójne kliknięcie elementu listy
        displayList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && displayList.getSelectedIndex() != -1) {
                    openActionsWindow();
                }
            }
        });

        // Przykład aktualizacji JList na podstawie manualList
        JButton addButton = new JButton("Add Example Object");
        addButton.addActionListener(e -> {
            // Dodawanie przykładowego obiektu do manualList
            int[] exampleCoords = {100, 200};
            List<String> exampleKeys = new ArrayList<>();
            exampleKeys.add("A");
            exampleKeys.add("B");
            manualList.add(new ManualObject(exampleCoords, exampleKeys, "Example Object", 500, 1000));

            // Aktualizacja JList, aby wyświetlać wszystkie szczegóły obiektów
            updateDisplayList();
        });

        add(addButton, BorderLayout.SOUTH);
    }

    // Metoda otwierająca okno "Actions" z elementami ManualObjectEditor
    private void openActionsWindow() {
        JFrame actionsFrame = new JFrame("Actions");
        actionsFrame.setSize(400, 300);
        actionsFrame.setLocationRelativeTo(null);

        ManualObjectEditor editorPanel = new ManualObjectEditor();
        actionsFrame.add(editorPanel);

        actionsFrame.setVisible(true);
    }

    // Metoda do aktualizacji JList na podstawie zawartości manualList
    private void updateDisplayList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (ManualObject obj : manualList) {
            String displayText = "<html>Name: " + obj.getName() +
                    " Coordinates: " + coordsToString(obj.getClickCoords()) +
                    " Keys: " + keysToString(obj.getKeys()) +
                    " Pre-Delay: " + obj.getPreDelay() + "ms" +
                    " Post-Delay: " + obj.getPostDelay() + "ms</html>";
            model.addElement(displayText);
        }
        displayList.setModel(model);
    }

    // Metoda do konwersji tablicy współrzędnych na string
    private String coordsToString(int[] coords) {
        return "[" + coords[0] + ", " + coords[1] + "]";
    }

    // Metoda do konwersji listy klawiszy na string
    private String keysToString(List<String> keys) {
        return String.join(", ", keys);
    }

    // Getter do manualList, jeśli chcesz uzyskać dostęp do tej listy z innych klas
    public List<ManualObject> getManualList() {
        return manualList;
    }
}
