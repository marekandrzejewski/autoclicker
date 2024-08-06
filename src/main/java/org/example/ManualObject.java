package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManualObject {
    private JRadioButton keyButton;
    private JRadioButton mouseButton;
    private ButtonGroup buttonGroup;
    private JComboBox<String> keyComboBox;
    private JPanel comboBoxPanel;
    private JButton addButton;
    private DefaultListModel<String> keyListModel;
    private JList<String> keyList;

    public ManualObject() {
        // Initialize radio buttons
        keyButton = new JRadioButton("Key");
        mouseButton = new JRadioButton("Mouse");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(keyButton);
        buttonGroup.add(mouseButton);

        // Initialize keyComboBox
        keyComboBox = new JComboBox<>(generateKeyList());
        keyComboBox.setVisible(false); // Initially hidden

        // Panel for the combo box
        comboBoxPanel = new JPanel();
        comboBoxPanel.add(keyComboBox);
        comboBoxPanel.setVisible(false); // Initially hidden

        // Initialize addButton
        addButton = new JButton("Add");

        // Initialize keyList
        keyListModel = new DefaultListModel<>();
        keyList = new JList<>(keyListModel);

        // Initialize and configure the frame
        JFrame frame = new JFrame("Manual Object Options");
        frame.setLayout(new BorderLayout());
        frame.setSize(600, 400); // Set size to match main window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add components to the frame
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new BoxLayout(radioPanel, BoxLayout.Y_AXIS));
        radioPanel.add(keyButton);
        radioPanel.add(mouseButton);
        frame.add(radioPanel, BorderLayout.NORTH);
        frame.add(comboBoxPanel, BorderLayout.CENTER);

        // Add addButton and keyList to a panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.add(addButton, BorderLayout.NORTH);
        listPanel.add(new JScrollPane(keyList), BorderLayout.CENTER);

        frame.add(listPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Action listener for radio buttons
        keyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyComboBox.setVisible(true); // Show the combo box when "Key" is selected
                comboBoxPanel.setVisible(true);
            }
        });

        mouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                keyComboBox.setVisible(false); // Hide the combo box when "Mouse" is selected
                comboBoxPanel.setVisible(false);
            }
        });

        // Action listener for addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Placeholder action for addButton
                String selectedKey = (String) keyComboBox.getSelectedItem();
                if (selectedKey != null && keyButton.isSelected()) {
                    keyListModel.addElement(selectedKey);
                }
            }
        });
    }

    private String[] generateKeyList() {
        // List of keys to be added to the combo box
        String[] keys = new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
                "A", "S", "D", "F", "G", "H", "J", "K", "L",
                "Z", "X", "C", "V", "B", "N", "M",
                "SPACE", "ENTER", "TAB", "CAPS LOCK", "ESC", "SHIFT", "CTRL", "ALT", "BACKSPACE", "DELETE",
                "INSERT", "PAGE UP", "PAGE DOWN", "HOME", "END", "ARROW UP", "ARROW DOWN", "ARROW LEFT", "ARROW RIGHT",
                ",", ".", ";", ":", "[", "]", "/", "\\", "'", "\"", "`", "~"
        };
        return keys;
    }

    public JRadioButton getKeyButton() {
        return keyButton;
    }

    public JRadioButton getMouseButton() {
        return mouseButton;
    }
}
