package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Manual {
    private DefaultListModel<String> manualListModel;
    private JList<String> manualList;
    private JPanel manualPanel;
    private JButton addButton;
    private JButton deleteButton;

    public Manual() {
        manualListModel = new DefaultListModel<>();
        manualList = new JList<>(manualListModel);

        manualList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    int index = manualList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        new ManualObject(index); // Open ManualObject options
                    }
                }
            }
        });

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manualListModel.addElement("Action");
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedIndex = manualList.getSelectedIndex();
                if (selectedIndex != -1) {
                    manualListModel.remove(selectedIndex);
                }
            }
        });

        manualPanel = new JPanel();
        manualPanel.setLayout(new BorderLayout());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, 4));
        headerPanel.add(new JLabel("Name"));
        headerPanel.add(new JLabel("Perform"));
        headerPanel.add(new JLabel("PreDelay"));
        headerPanel.add(new JLabel("PostDelay"));

        manualPanel.add(headerPanel, BorderLayout.NORTH);
        manualPanel.add(new JScrollPane(manualList), BorderLayout.CENTER);
        manualPanel.add(buttonPanel, BorderLayout.SOUTH);
    }

    public DefaultListModel<String> getManualListModel() {
        return manualListModel;
    }

    public JPanel getManualPanel() {
        return manualPanel;
    }

    public void updateManualListItem(int index, DefaultListModel<String> keyListModel) {
        StringBuilder newContent = new StringBuilder("Action: ");
        for (int i = 0; i < keyListModel.getSize(); i++) {
            newContent.append(keyListModel.getElementAt(i)).append(", ");
        }
        manualListModel.set(index, newContent.toString());
    }

    public static Manual getInstance() {
        return ManualHolder.INSTANCE;
    }

    private static class ManualHolder {
        private static final Manual INSTANCE = new Manual();
    }
}
