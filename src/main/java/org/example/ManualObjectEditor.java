package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.List;

public class ManualObjectEditor extends JPanel {
    private JList<String> keyList;       // Lista klawiszy dostępnych do dodania
    private JList<String> addedKeyList;  // Lista dodanych klawiszy
    private JButton addButton;           // Przycisk "Add"
    private JButton deleteButton;        // Przycisk "Delete"
    private DefaultListModel<String> keyListModel; // Model dla keyList
    private DefaultListModel<String> addedKeyListModel; // Model dla addedKeyList

    public ManualObjectEditor() {
        // Ustawienie layoutu na GridBagLayout dla bardziej elastycznego rozmieszczenia elementów
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Inicjalizacja modeli list
        keyListModel = new DefaultListModel<>();
        addedKeyListModel = new DefaultListModel<>();

        // Inicjalizacja list z użyciem modeli
        keyList = new JList<>(keyListModel);
        addedKeyList = new JList<>(addedKeyListModel);

        // Inicjalizacja przycisków
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");

        // Wypełnienie keyListModel klawiszami QWERTY
        populateKeyList();

        // Ustawienie keyList w layout
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JScrollPane(keyList), gbc);

        // Ustawienie addedKeyList w layout
        gbc.gridx = 2;
        add(new JScrollPane(addedKeyList), gbc);

        // Ustawienie przycisków w layout
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        add(addButton, gbc);

        gbc.gridy = 1;
        add(deleteButton, gbc);

        // Dodanie nasłuchiwaczy do przycisków
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSelectedKey();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedKey();
            }
        });
    }

    // Metoda do wypełnienia keyListModel klawiszami QWERTY
    private void populateKeyList() {
        List<String> keys = Arrays.asList(
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                "A", "S", "D", "F", "G", "H", "J", "K", "L",
                "Z", "X", "C", "V", "B", "N", "M",
                "1", "2", "3", "4", "5", "6", "7", "8", "9", "0",
                "SPACE", "ENTER", "SHIFT", "CTRL", "ALT", "TAB", "BACKSPACE", "ESC"
        );

        for (String key : keys) {
            keyListModel.addElement(key);
        }
    }

    // Metoda do dodawania wybranego klawisza z keyList do addedKeyList
    private void addSelectedKey() {
        String selectedKey = keyList.getSelectedValue();
        if (selectedKey != null) {
            // Usunięto warunek sprawdzający, czy klawisz już istnieje
            // Teraz ten sam klawisz może być dodawany wielokrotnie
            addedKeyListModel.addElement(selectedKey);
        }
    }

    // Metoda do usuwania wybranego klawisza z addedKeyList
    private void deleteSelectedKey() {
        String selectedKey = addedKeyList.getSelectedValue();
        if (selectedKey != null) {
            addedKeyListModel.removeElement(selectedKey);
        }
    }

    // Gettery do uzyskania dostępu do list i przycisków spoza klasy
    public JList<String> getKeyList() {
        return keyList;
    }

    public JList<String> getAddedKeyList() {
        return addedKeyList;
    }

    public JButton getAddButton() {
        return addButton;
    }

    public JButton getDeleteButton() {
        return deleteButton;
    }
}
