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
    private JButton saveButton;
    private DefaultListModel<String> keyListModel;
    private JList<String> keyList;
    private JTextField preDelayField;
    private JTextField postDelayField;
    private int manualListIndex;

    public ManualObject(int manualListIndex) {
        this.manualListIndex = manualListIndex;

        // Initialize radio buttons
        keyButton = new JRadioButton("Key");
        mouseButton = new JRadioButton("Mouse");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(keyButton);
        buttonGroup.add(mouseButton);

        // Initialize keyComboBox
        keyComboBox = new JComboBox<>(generateKeyList());

        // Panel for the combo box
        comboBoxPanel = new JPanel();
        comboBoxPanel.add(keyComboBox);
        comboBoxPanel.setVisible(false); // Initially hidden

        // Initialize addButton
        addButton = new JButton("Add");

        // Initialize saveButton
        saveButton = new JButton("Save");

        // Initialize keyList
        keyListModel = new DefaultListModel<>();
        keyList = new JList<>(keyListModel);

        // Initialize text fields for delays with specified size
        preDelayField = new JTextField();
        preDelayField.setPreferredSize(new Dimension(50, 25)); // Width 50 pixels, Height 25 pixels
        postDelayField = new JTextField();
        postDelayField.setPreferredSize(new Dimension(50, 25)); // Width 50 pixels, Height 25 pixels

        // Initialize and configure the frame
        JFrame frame = new JFrame("Manual Object Options");
        frame.setLayout(null); // Set layout to null for manual positioning
        frame.setSize(600, 400); // Set size to match main window
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add components to the frame
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(keyButton);
        radioPanel.add(mouseButton);
        radioPanel.setBounds(10, 10, 580, 50); // Set bounds manually
        frame.add(radioPanel);

        JPanel delayPanel = new JPanel();
        delayPanel.setLayout(null); // Set layout to null for manual positioning
        delayPanel.setBounds(10, 70, 580, 40); // Set bounds manually

        JLabel preDelayLabel = new JLabel("Pre Delay (ms):");
        preDelayLabel.setBounds(10, 10, 100, 25); // Set bounds manually
        preDelayField.setBounds(120, 10, 50, 25); // Set bounds manually

        JLabel postDelayLabel = new JLabel("Post Delay (ms):");
        postDelayLabel.setBounds(180, 10, 100, 25); // Set bounds manually
        postDelayField.setBounds(290, 10, 50, 25); // Set bounds manually

        delayPanel.add(preDelayLabel);
        delayPanel.add(preDelayField);
        delayPanel.add(postDelayLabel);
        delayPanel.add(postDelayField);
        frame.add(delayPanel);

        // Panel for comboBox and addButton
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBounds(10, 120, 580, 100); // Set bounds manually
        controlPanel.add(comboBoxPanel, BorderLayout.CENTER);
        controlPanel.add(addButton, BorderLayout.SOUTH);
        frame.add(controlPanel);

        // Add keyList and saveButton to a panel
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.setBounds(10, 230, 580, 130); // Set bounds manually
        listPanel.add(new JScrollPane(keyList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(saveButton);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        frame.add(listPanel);

        frame.setVisible(true);

        // Action listener for radio buttons
        keyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxPanel.setVisible(true); // Show the combo box when "Key" is selected
            }
        });

        mouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxPanel.setVisible(false); // Hide the combo box when "Mouse" is selected
            }
        });

        // Action listener for addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedKey = (String) keyComboBox.getSelectedItem();
                if (selectedKey != null && keyButton.isSelected()) {
                    keyListModel.addElement(selectedKey);
                }
            }
        });

        // Action listener for saveButton
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manual manualInstance = Manual.getInstance();
                DefaultListModel<String> manualListModel = manualInstance.getManualListModel();

                // Read preDelay and postDelay values from text fields
                int preDelay;
                int postDelay;
                try {
                    preDelay = Integer.parseInt(preDelayField.getText());
                } catch (NumberFormatException ex) {
                    preDelay = 0; // Default value if parsing fails
                }
                try {
                    postDelay = Integer.parseInt(postDelayField.getText());
                } catch (NumberFormatException ex) {
                    postDelay = 0; // Default value if parsing fails
                }

                // Update the selected manual list item with only delay values
                StringBuilder newContent = new StringBuilder();
                for (int i = 0; i < keyListModel.getSize(); i++) {
                    newContent.append(keyListModel.getElementAt(i)).append(", ");
                }
                newContent.append(preDelay).append(" ms, ");
                newContent.append(postDelay).append(" ms");

                manualListModel.set(manualListIndex, newContent.toString());
                frame.dispose(); // Close the frame after saving
            }
        });
    }

    private String[] generateKeyList() {
        // List of keys to be added to the combo box
        String[] keys = new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "SPACE", "ENTER", "TAB", "CAPS LOCK", "ESC", "SHIFT", "CTRL", "ALT", "BACKSPACE", "DELETE",
                "INSERT", "PAGE UP", "PAGE DOWN", "HOME", "END", "ARROW UP", "ARROW DOWN", "ARROW LEFT", "ARROW RIGHT",
                ",", ".", ";", ":", "[", "]", "/", "\\", "'", "\"", "`", "~"
        };
        return keys;
    }
}
