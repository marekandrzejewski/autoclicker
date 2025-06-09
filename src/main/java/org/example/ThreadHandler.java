package org.example;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ThreadHandler extends JPanel {
    private DefaultListModel<ThreadListItem> threadListModel;
    private JList<ThreadListItem> threadList;
    private JButton createThreadButton;
    private JButton deleteThreadButton;  // Przycisk Delete Thread
    private Manual manualPanel;  // Referencja do klasy Manual w celu dostępu do manualList

    public ThreadHandler(Manual manualPanel) {
        this.manualPanel = manualPanel; // Inicjalizacja referencji do Manual

        // Tworzenie i konfiguracja listy wątków
        threadListModel = new DefaultListModel<>();
        threadList = new JList<>(threadListModel);
        threadList.setCellRenderer(new ThreadListRenderer()); // Ustawienie niestandardowego renderera
        JScrollPane threadScrollPane = new JScrollPane(threadList);

        // Dodanie listy wątków do panelu
        setLayout(new BorderLayout());
        add(threadScrollPane, BorderLayout.CENTER);

        // Tworzenie przycisku "Create Thread"
        createThreadButton = new JButton("Create Thread");
        createThreadButton.addActionListener(e -> createThread());

        // Tworzenie przycisku "Delete Thread"
        deleteThreadButton = new JButton("Delete Thread");
        deleteThreadButton.addActionListener(e -> deleteSelectedThread());

        // Panel przycisków
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(2, 1));
        buttonPanel.add(createThreadButton);
        buttonPanel.add(deleteThreadButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void createThread() {
        List<ManualObject> manualObjectsCopy = new ArrayList<>(manualPanel.getManualList()); // Kopia bieżących elementów z manualList
        ThreadListItem newItem = new ThreadListItem("Thread " + (threadListModel.getSize() + 1), manualObjectsCopy);
        threadListModel.addElement(newItem);
    }

    private void deleteSelectedThread() {
        int selectedIndex = threadList.getSelectedIndex();
        if (selectedIndex != -1) {
            threadListModel.remove(selectedIndex);
        }
    }

    public DefaultListModel<ThreadListItem> getThreadListModel() {
        return threadListModel;
    }

    // Klasa elementu listy do reprezentowania wątku z jego zagnieżdżonymi obiektami manualnymi
    private static class ThreadListItem {
        private String name;
        private List<ManualObject> manualObjects;

        public ThreadListItem(String name, List<ManualObject> manualObjects) {
            this.name = name;
            this.manualObjects = manualObjects;
        }

        public String getName() {
            return name;
        }

        public List<ManualObject> getManualObjects() {
            return manualObjects;
        }

        @Override
        public String toString() {
            return name + " (" + manualObjects.size() + " items)";
        }
    }

    // Renderer do wyświetlania elementów zagnieżdżonych w liście wątków
    private class ThreadListRenderer extends JLabel implements ListCellRenderer<ThreadListItem> {
        @Override
        public Component getListCellRendererComponent(
                JList<? extends ThreadListItem> list, ThreadListItem value, int index, boolean isSelected, boolean cellHasFocus) {
            setText("<html>" + value.getName() + "<br/>" + nestedManualObjectsToString(value.getManualObjects()) + "</html>");
            setOpaque(true);
            setBackground(isSelected ? list.getSelectionBackground() : list.getBackground());
            setForeground(isSelected ? list.getSelectionForeground() : list.getForeground());
            return this;
        }

        private String nestedManualObjectsToString(List<ManualObject> manualObjects) {
            StringBuilder sb = new StringBuilder("<ul>");
            for (ManualObject obj : manualObjects) {
                sb.append("<li>").append(obj.toString()).append("</li>");
            }
            sb.append("</ul>");
            return sb.toString();
        }
    }
}
