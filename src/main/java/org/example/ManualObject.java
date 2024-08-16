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
    private Run runInstance;

    public ManualObject(int manualListIndex) {
        this.manualListIndex = manualListIndex;
        this.runInstance = new Run(); // Utwórz instancję klasy Run

        // Inicjalizacja przycisków radiowych
        keyButton = new JRadioButton("Key");
        mouseButton = new JRadioButton("Mouse");
        buttonGroup = new ButtonGroup();
        buttonGroup.add(keyButton);
        buttonGroup.add(mouseButton);

        // Inicjalizacja keyComboBox
        keyComboBox = new JComboBox<>(generateKeyList());

        // Panel dla combo boxa
        comboBoxPanel = new JPanel();
        comboBoxPanel.add(keyComboBox);
        comboBoxPanel.setVisible(false); // Początkowo ukryty

        // Inicjalizacja addButton
        addButton = new JButton("Add");

        // Inicjalizacja saveButton
        saveButton = new JButton("Save");

        // Inicjalizacja keyList
        keyListModel = new DefaultListModel<>();
        keyList = new JList<>(keyListModel);

        // Inicjalizacja pól tekstowych dla opóźnień
        preDelayField = new JTextField();
        preDelayField.setPreferredSize(new Dimension(50, 25));
        postDelayField = new JTextField();
        postDelayField.setPreferredSize(new Dimension(50, 25));

        // Inicjalizacja ramki
        JFrame frame = new JFrame("Manual Object Options");
        frame.setLayout(null);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panel dla przycisków radiowych
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(keyButton);
        radioPanel.add(mouseButton);
        radioPanel.setBounds(10, 10, 580, 50);
        frame.add(radioPanel);

        // Panel dla opóźnień
        JPanel delayPanel = new JPanel();
        delayPanel.setLayout(null);
        delayPanel.setBounds(10, 70, 580, 40);

        JLabel preDelayLabel = new JLabel("Pre Delay (ms):");
        preDelayLabel.setBounds(10, 10, 100, 25);
        preDelayField.setBounds(120, 10, 50, 25);

        JLabel postDelayLabel = new JLabel("Post Delay (ms):");
        postDelayLabel.setBounds(180, 10, 100, 25);
        postDelayField.setBounds(290, 10, 50, 25);

        delayPanel.add(preDelayLabel);
        delayPanel.add(preDelayField);
        delayPanel.add(postDelayLabel);
        delayPanel.add(postDelayField);
        frame.add(delayPanel);

        // Panel dla comboBox i addButton
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new BorderLayout());
        controlPanel.setBounds(10, 120, 580, 100);
        controlPanel.add(comboBoxPanel, BorderLayout.CENTER);
        controlPanel.add(addButton, BorderLayout.SOUTH);
        frame.add(controlPanel);

        // Panel dla keyList i saveButton
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.setBounds(10, 230, 580, 130);
        listPanel.add(new JScrollPane(keyList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(saveButton);
        listPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Dodanie przycisku "Run Autoclicker" do panelu
        runInstance.getRunButton().setBounds(10, 370, 580, 40);
        frame.add(runInstance.getRunButton());

        frame.add(listPanel);
        frame.setVisible(true);

        // Action listener dla przycisków radiowych
        keyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxPanel.setVisible(true);
                runInstance.setRunButtonVisible(true); // Pokazuje przycisk "Run Autoclicker"
            }
        });

        mouseButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                comboBoxPanel.setVisible(false);
                runInstance.setRunButtonVisible(false); // Ukrywa przycisk "Run Autoclicker"
            }
        });

        // Action listener dla addButton
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedKey = (String) keyComboBox.getSelectedItem();
                if (selectedKey != null && keyButton.isSelected()) {
                    keyListModel.addElement(selectedKey);
                }
            }
        });

        // Action listener dla saveButton
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Manual manualInstance = Manual.getInstance();
                DefaultListModel<String> manualListModel = manualInstance.getManualListModel();

                int preDelay;
                int postDelay;
                try {
                    preDelay = Integer.parseInt(preDelayField.getText());
                } catch (NumberFormatException ex) {
                    preDelay = 0;
                }
                try {
                    postDelay = Integer.parseInt(postDelayField.getText());
                } catch (NumberFormatException ex) {
                    postDelay = 0;
                }

                StringBuilder newContent = new StringBuilder();
                for (int i = 0; i < keyListModel.getSize(); i++) {
                    newContent.append(keyListModel.getElementAt(i)).append(", ");
                }
                newContent.append(preDelay).append(" ms, ");
                newContent.append(postDelay).append(" ms");

                manualListModel.set(manualListIndex, newContent.toString());
                frame.dispose();
            }
        });
    }

    private String[] generateKeyList() {
        return new String[] {
                "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9",
                "SPACE", "ENTER", "TAB", "CAPS LOCK", "ESC", "SHIFT", "CTRL", "ALT", "BACKSPACE", "DELETE",
                "INSERT", "PAGE UP", "PAGE DOWN", "HOME", "END", "ARROW UP", "ARROW DOWN", "ARROW LEFT", "ARROW RIGHT",
                ",", ".", ";", ":", "[", "]", "/", "\\", "'", "\"", "`", "~"
        };
    }
}
