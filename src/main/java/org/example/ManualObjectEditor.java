package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class ManualObjectEditor extends JPanel {
    private JList<String> keyList;
    private JList<String> addedKeyList;
    private JButton addButton;
    private JButton deleteButton;
    private JButton okButton;
    private DefaultListModel<String> keyListModel;
    private DefaultListModel<String> addedKeyListModel;
    private int selectedIndex;
    private List<ManualObject> manualList;
    private Manual manualPanel;

    // Nowe pola tekstowe
    private JTextField userXField;
    private JTextField userYField;
    private JTextField preDelayField;
    private JTextField postDelayField;

    public ManualObjectEditor(int selectedIndex, List<ManualObject> manualList, Manual manualPanel) {
        this.selectedIndex = selectedIndex;
        this.manualList = manualList;
        this.manualPanel = manualPanel;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Lista klawiszy
        keyListModel = new DefaultListModel<>();
        addedKeyListModel = new DefaultListModel<>();
        keyList = new JList<>(keyListModel);
        addedKeyList = new JList<>(addedKeyListModel);

        // Przyciski
        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        okButton = new JButton("OK");

        populateKeyList();

        // Nowe etykiety i pola tekstowe dla współrzędnych i opóźnień
        JLabel userXLabel = new JLabel("User X:");
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(userXLabel, gbc);

        userXField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 0;
        add(userXField, gbc);

        JLabel userYLabel = new JLabel("User Y:");
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(userYLabel, gbc);

        userYField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 1;
        add(userYField, gbc);

        JLabel preDelayLabel = new JLabel("Pre-Delay:");
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(preDelayLabel, gbc);

        preDelayField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 2;
        add(preDelayField, gbc);

        JLabel postDelayLabel = new JLabel("Post-Delay:");
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(postDelayLabel, gbc);

        postDelayField = new JTextField(10);
        gbc.gridx = 1;
        gbc.gridy = 3;
        add(postDelayField, gbc);

        // Listy klawiszy i przyciski
        gbc.gridx = 0;
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JScrollPane(keyList), gbc);

        gbc.gridx = 2;
        add(new JScrollPane(addedKeyList), gbc);

        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        add(addButton, gbc);

        gbc.gridy = 5;
        add(deleteButton, gbc);

        gbc.gridy = 6;
        add(okButton, gbc);

        // Obsługa przycisków
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

        okButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateExistingObject();
            }
        });

        // Inicjalizacja pól wartościami bieżącymi wybranego obiektu
        initializeFields();
    }

    private void populateKeyList() {
        List<String> keys = List.of(
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

    private void addSelectedKey() {
        String selectedKey = keyList.getSelectedValue();
        if (selectedKey != null) {
            addedKeyListModel.addElement(selectedKey);
        }
    }

    private void deleteSelectedKey() {
        String selectedKey = addedKeyList.getSelectedValue();
        if (selectedKey != null) {
            addedKeyListModel.removeElement(selectedKey);
        }
    }

    private void updateExistingObject() {
        if (selectedIndex >= 0 && selectedIndex < manualList.size()) {
            ManualObject obj = manualList.get(selectedIndex);

            // Pobierz nowe wartości z pól tekstowych
            int newX = Integer.parseInt(userXField.getText());
            int newY = Integer.parseInt(userYField.getText());
            int[] newCoords = new int[]{newX, newY};

            List<String> newKeys = new ArrayList<>();
            for (int i = 0; i < addedKeyListModel.getSize(); i++) {
                newKeys.add(addedKeyListModel.getElementAt(i));
            }

            // Pobierz opóźnienia z pól tekstowych
            int newPreDelay = Integer.parseInt(preDelayField.getText());
            int newPostDelay = Integer.parseInt(postDelayField.getText());

            String newName = "Updated Name"; // Przykładowa wartość

            // Zaktualizuj obiekt
            manualList.set(selectedIndex, new ManualObject(newCoords, newKeys, newName, newPreDelay, newPostDelay));

            // Odśwież listę w głównym panelu
            manualPanel.refreshDisplayList();

            // Zamknij okno
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
    }

    private void initializeFields() {
        if (selectedIndex >= 0 && selectedIndex < manualList.size()) {
            ManualObject obj = manualList.get(selectedIndex);

            // Inicjalizuj pola tekstowe wartościami z wybranego obiektu
            userXField.setText(String.valueOf(obj.getClickCoords()[0]));
            userYField.setText(String.valueOf(obj.getClickCoords()[1]));
            preDelayField.setText(String.valueOf(obj.getPreDelay()));
            postDelayField.setText(String.valueOf(obj.getPostDelay()));

            // Wypełnij listę dodanych klawiszy
            addedKeyListModel.clear();
            for (String key : obj.getKeys()) {
                addedKeyListModel.addElement(key);
            }
        }
    }
}
