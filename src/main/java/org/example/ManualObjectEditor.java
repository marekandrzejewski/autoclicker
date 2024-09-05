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

    public ManualObjectEditor(int selectedIndex, List<ManualObject> manualList, Manual manualPanel) {
        this.selectedIndex = selectedIndex;
        this.manualList = manualList;
        this.manualPanel = manualPanel;

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(5, 5, 5, 5);

        keyListModel = new DefaultListModel<>();
        addedKeyListModel = new DefaultListModel<>();

        keyList = new JList<>(keyListModel);
        addedKeyList = new JList<>(addedKeyListModel);

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        okButton = new JButton("OK");

        populateKeyList();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        add(new JScrollPane(keyList), gbc);

        gbc.gridx = 2;
        add(new JScrollPane(addedKeyList), gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        gbc.weighty = 0.0;
        gbc.gridheight = 1;
        add(addButton, gbc);

        gbc.gridy = 1;
        add(deleteButton, gbc);

        gbc.gridy = 2;
        add(okButton, gbc);

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

        // Initialize fields with the current values of the selected object
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
            int[] newCoords = obj.getClickCoords(); // Use existing coordinates or modify as needed
            List<String> newKeys = new ArrayList<>();
            for (int i = 0; i < addedKeyListModel.getSize(); i++) {
                newKeys.add(addedKeyListModel.getElementAt(i));
            }
            String newName = "Updated Name"; // Example value; you may want to use a text field for input
            int newPreDelay = 500; // Example value
            int newPostDelay = 1000; // Example value

            manualList.set(selectedIndex, new ManualObject(newCoords, newKeys, newName, newPreDelay, newPostDelay));
            manualPanel.refreshDisplayList(); // Refresh the display list in the main panel
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
    }

    private void initializeFields() {
        if (selectedIndex >= 0 && selectedIndex < manualList.size()) {
            ManualObject obj = manualList.get(selectedIndex);
            // Populate the JLists with the current values from the selected object
            addedKeyListModel.clear();
            for (String key : obj.getKeys()) {
                addedKeyListModel.addElement(key);
            }
        }
    }
}
