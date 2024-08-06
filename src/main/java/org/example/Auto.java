package org.example;

import javax.swing.*;

public class Auto {
    private DefaultListModel<String> clickListModel;
    private JList<String> clickList;
    private JButton clearListButton;
    private boolean isListening;

    public Auto() {
        clickListModel = new DefaultListModel<>();
        clickList = new JList<>(clickListModel);
        clearListButton = new JButton("Clear List");
        clearListButton.setEnabled(false);

        clearListButton.addActionListener(e -> clearClickList());
    }

    public void addClickCoordinates(String coordinates) {
        SwingUtilities.invokeLater(() -> clickListModel.addElement(coordinates));
    }

    public JList<String> getClickList() {
        return clickList;
    }

    public JButton getClearListButton() {
        return clearListButton;
    }

    public void startListening() {
        if (!isListening) {
            ClickListen.initialize();
            isListening = true;
            clearListButton.setEnabled(false); // Disable Clear List button when listening starts
        }
    }

    public void stopListening() {
        if (isListening) {
            ClickListen.deinitialize();
            isListening = false;
            clearListButton.setEnabled(true); // Enable Clear List button when listening stops
            removeLastClick();
        }
    }

    private void removeLastClick() {
        if (!clickListModel.isEmpty()) {
            SwingUtilities.invokeLater(() -> clickListModel.remove(clickListModel.getSize() - 1));
        }
    }

    private void clearClickList() {
        SwingUtilities.invokeLater(() -> clickListModel.clear());
    }

    public static Auto getInstance() {
        return AutoHolder.INSTANCE;
    }

    private static class AutoHolder {
        private static final Auto INSTANCE = new Auto();
    }
}
