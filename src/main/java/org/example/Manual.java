package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Manual extends JPanel {
    private List<ManualObject> manualList;  // Lista obiektów typu ManualObject

    public Manual() {
        // Inicjalizacja listy obiektów ManualObject
        manualList = new ArrayList<>();

        // Ustawienie layoutu
        setLayout(new BorderLayout());

        // Przykład interfejsu użytkownika dla panelu Manual
        JLabel label = new JLabel("Manual Mode");
        add(label, BorderLayout.NORTH);

        // JList do wyświetlania obiektów manualList
        JList<String> displayList = new JList<>();
        add(new JScrollPane(displayList), BorderLayout.CENTER);

        // Przykład aktualizacji JList na podstawie manualList
        JButton addButton = new JButton("Add Example Object");
        addButton.addActionListener(e -> {
            // Dodawanie przykładowego obiektu do manualList
            int[] exampleCoords = {100, 200};
            List<String> exampleKeys = new ArrayList<>();
            exampleKeys.add("A");
            exampleKeys.add("B");
            manualList.add(new ManualObject(exampleCoords, exampleKeys, "Example Object", 500, 1000));

            // Aktualizacja JList, aby wyświetlać nazwy obiektów
            updateDisplayList(displayList);
        });

        add(addButton, BorderLayout.SOUTH);
    }

    // Metoda do aktualizacji JList na podstawie zawartości manualList
    private void updateDisplayList(JList<String> displayList) {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (ManualObject obj : manualList) {
            model.addElement(obj.getName());
        }
        displayList.setModel(model);
    }

    // Getter do manualList, jeśli chcesz uzyskać dostęp do tej listy z innych klas
    public List<ManualObject> getManualList() {
        return manualList;
    }
}
