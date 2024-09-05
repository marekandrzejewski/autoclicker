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
    private int selectedIndex = -1;         // Indeks wybranego obiektu

    public Manual() {
        manualList = new ArrayList<>();
        setLayout(new BorderLayout());

        JLabel label = new JLabel("Manual Mode");
        add(label, BorderLayout.NORTH);

        displayList = new JList<>();
        JScrollPane scrollPane = new JScrollPane(displayList);

        Dimension fixedSize = new Dimension(500, scrollPane.getPreferredSize().height);
        scrollPane.setPreferredSize(fixedSize);
        scrollPane.setMinimumSize(fixedSize);
        scrollPane.setMaximumSize(fixedSize);

        add(scrollPane, BorderLayout.CENTER);

        displayList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && displayList.getSelectedIndex() != -1) {
                    selectedIndex = displayList.getSelectedIndex();
                    openActionsWindow();
                }
            }
        });

        JButton addButton = new JButton("Add Example Object");
        addButton.addActionListener(e -> {
            manualList.add(new ManualObject(new int[]{0,0}, List.of(), "Object", 500, 1000));
            updateDisplayList();
        });

        add(addButton, BorderLayout.SOUTH);
    }

    private void updateDisplayList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (ManualObject obj : manualList) {
            model.addElement(obj.toString()); // Assuming toString() method returns a string representation
        }
        displayList.setModel(model);
    }

    private void openActionsWindow() {
        JFrame actionsFrame = new JFrame("Actions");
        actionsFrame.setSize(400, 300);
        actionsFrame.setLocationRelativeTo(null);

        // Pass the selectedIndex and manualList to the editor
        ManualObjectEditor editorPanel = new ManualObjectEditor(selectedIndex, manualList, this);
        actionsFrame.add(editorPanel);

        actionsFrame.setVisible(true);
    }

    public List<ManualObject> getManualList() {
        return manualList;
    }

    public JList<String> getDisplayList() {
        return displayList;
    }

    public void refreshDisplayList() {
        updateDisplayList();
    }
}
