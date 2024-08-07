package org.example;

import javax.swing.*;

public class Auto {
    private DefaultListModel<String> clickListModel;
    private JList<String> clickList;
    private boolean isListening;
    private JButton saveButton;

    public Auto() {
        clickListModel = new DefaultListModel<>();
        clickList = new JList<>(clickListModel);
    }

    public void addClickCoordinates(String coordinates) {
        SwingUtilities.invokeLater(() -> clickListModel.addElement(coordinates));
    }

    public JList<String> getClickList() {
        return clickList;
    }

    public void startListening() {
        if (!isListening) {
            ClickListen.initialize();
            isListening = true;
        }
    }

    public void stopListening() {
        if (isListening) {
            ClickListen.deinitialize();
            isListening = false;
            removeLastClick();
        }
    }

    public void clearList() {
        SwingUtilities.invokeLater(() -> clickListModel.clear());
    }

    public JButton getSaveButton() {
        if (saveButton == null) {
            saveButton = new JButton("Save");
            saveButton.addActionListener(e -> saveClickListToManualList());
        }
        return saveButton;
    }

    private void saveClickListToManualList() {
        Manual manualInstance = Manual.getInstance();
        DefaultListModel<String> manualListModel = manualInstance.getManualListModel();
        for (int i = 0; i < clickListModel.size(); i++) {
            String clickCoordinates = clickListModel.getElementAt(i);
            String newAction = "Action: " + clickCoordinates;
            manualListModel.addElement(newAction);
        }
    }

    private void removeLastClick() {
        if (!clickListModel.isEmpty()) {
            SwingUtilities.invokeLater(() -> clickListModel.remove(clickListModel.getSize() - 1));
        }
    }

    public static Auto getInstance() {
        return AutoHolder.INSTANCE;
    }

    private static class AutoHolder {
        private static final Auto INSTANCE = new Auto();
    }
}
