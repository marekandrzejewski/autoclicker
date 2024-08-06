package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Manual {
    private static final String[] COLUMN_NAMES = {"Name", "Perform", "PreDelay", "PostDelay"};
    private DefaultListModel<ManualItem> manualListModel;
    private JList<ManualItem> manualList;
    private JButton addButton;
    private JButton deleteButton;

    public Manual() {
        manualListModel = new DefaultListModel<>();
        manualList = new JList<>(manualListModel);
        manualList.setCellRenderer(new ManualListCellRenderer());

        addButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        deleteButton.setEnabled(false); // Disable initially

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addItem(new ManualItem("Action", "", 0, 0));
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteSelectedItem();
            }
        });

        manualList.addListSelectionListener(e -> {
            boolean isItemSelected = !manualList.isSelectionEmpty();
            deleteButton.setEnabled(isItemSelected);
        });

        // Mouse listener to allow item selection with left-click
        manualList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1 && e.getClickCount() == 2) {
                    // Open ManualObject window on double click
                    new ManualObject();
                } else if (e.getButton() == MouseEvent.BUTTON1) {
                    int index = manualList.locationToIndex(e.getPoint());
                    if (index >= 0) {
                        manualList.setSelectedIndex(index);
                    }
                }
            }
        });
    }

    public JPanel getManualPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Panel for headers
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new GridLayout(1, COLUMN_NAMES.length));
        for (String columnName : COLUMN_NAMES) {
            JLabel headerLabel = new JLabel(columnName);
            headerLabel.setHorizontalAlignment(SwingConstants.CENTER);
            headerPanel.add(headerLabel);
        }

        // Panel for list and buttons
        JPanel listPanel = new JPanel();
        listPanel.setLayout(new BorderLayout());
        listPanel.add(headerPanel, BorderLayout.NORTH);
        listPanel.add(new JScrollPane(manualList), BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout());
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);

        listPanel.add(buttonPanel, BorderLayout.SOUTH);
        panel.add(listPanel, BorderLayout.CENTER);

        return panel;
    }

    private void addItem(ManualItem item) {
        SwingUtilities.invokeLater(() -> manualListModel.addElement(item));
    }

    private void deleteSelectedItem() {
        int selectedIndex = manualList.getSelectedIndex();
        if (selectedIndex >= 0) {
            SwingUtilities.invokeLater(() -> manualListModel.remove(selectedIndex));
        }
    }

    // Custom list cell renderer to display columns
    private static class ManualListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            ManualItem item = (ManualItem) value;
            JLabel label = (JLabel) super.getListCellRendererComponent(list, item.getName(), index, isSelected, cellHasFocus);
            if (isSelected) {
                label.setBackground(Color.BLUE);
                label.setForeground(Color.WHITE);
            } else {
                label.setBackground(Color.WHITE);
                label.setForeground(Color.BLACK);
            }
            label.setText(item.toString());
            return label;
        }
    }

    // Define ManualItem class with necessary fields and methods
    public static class ManualItem {
        private String name;
        private String perform;
        private int preDelay;
        private int postDelay;

        public ManualItem(String name, String perform, int preDelay, int postDelay) {
            this.name = name;
            this.perform = perform;
            this.preDelay = preDelay;
            this.postDelay = postDelay;
        }

        public String getName() {
            return name;
        }

        @Override
        public String toString() {
            return String.format("%s    %s    %d    %d", name, perform, preDelay, postDelay);
        }
    }

    public static Manual getInstance() {
        return ManualHolder.INSTANCE;
    }

    private static class ManualHolder {
        private static final Manual INSTANCE = new Manual();
    }
}
