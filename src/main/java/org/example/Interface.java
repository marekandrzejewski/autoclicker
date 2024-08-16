package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface {
    private JFrame frame;
    private DefaultListModel<String> listModel;
    private JRadioButton manualButton;
    private JRadioButton autoButton;
    private ButtonGroup modeGroup;
    private JPanel autoPanel;
    private JPanel manualPanel;
    private JButton startListeningButton;
    private JButton stopListeningButton;
    private JButton clearListButton;
    private JScrollPane clickListScrollPane;

    public Interface() {
        frame = new JFrame("Kliknięcia Myszy");
        listModel = new DefaultListModel<>();

        manualButton = new JRadioButton("Manual");
        autoButton = new JRadioButton("Auto");
        modeGroup = new ButtonGroup();
        modeGroup.add(manualButton);
        modeGroup.add(autoButton);
        manualButton.setSelected(true);

        manualButton.addActionListener(new ModeChangeListener());
        autoButton.addActionListener(new ModeChangeListener());

        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(manualButton);
        radioPanel.add(autoButton);

        autoPanel = new JPanel();
        autoPanel.setLayout(new FlowLayout());
        startListeningButton = new JButton("Start Listening");
        stopListeningButton = new JButton("Stop Listening");
        stopListeningButton.setEnabled(false);
        clearListButton = new JButton("Clear List");
        clearListButton.setEnabled(false);

        JButton saveButton = Auto.getInstance().getSaveButton();
        saveButton.setEnabled(false);

        startListeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auto.getInstance().startListening();
                startListeningButton.setEnabled(false);
                stopListeningButton.setEnabled(true);
                manualButton.setEnabled(false);
                clearListButton.setEnabled(false);
                saveButton.setEnabled(false);
            }
        });

        stopListeningButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auto.getInstance().stopListening();
                startListeningButton.setEnabled(true);
                stopListeningButton.setEnabled(false);
                manualButton.setEnabled(true);
                clearListButton.setEnabled(true);
                saveButton.setEnabled(true);
            }
        });

        clearListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Auto.getInstance().clearList();
            }
        });

        autoPanel.add(startListeningButton);
        autoPanel.add(stopListeningButton);
        autoPanel.add(clearListButton);
        autoPanel.add(saveButton);
        autoPanel.setVisible(false);

        JList<String> clickList = Auto.getInstance().getClickList();
        clickListScrollPane = new JScrollPane(clickList);
        clickListScrollPane.setVisible(false);

        manualPanel = Manual.getInstance().getManualPanel();

        frame.setLayout(new BorderLayout());
        frame.add(radioPanel, BorderLayout.NORTH);
        frame.add(clickListScrollPane, BorderLayout.CENTER);
        frame.add(autoPanel, BorderLayout.SOUTH);
        frame.add(manualPanel, BorderLayout.EAST);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        // Ustawienie widoczności przycisku "Run Autoclicker" po uruchomieniu programu
        Manual.getInstance().setRunAutoclickerButtonVisible(true);
    }

    public void addItem(String item) {
        SwingUtilities.invokeLater(() -> listModel.addElement(item));
    }

    public boolean isAutoMode() {
        return autoButton.isSelected();
    }

    private class ModeChangeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            boolean autoSelected = autoButton.isSelected();
            autoPanel.setVisible(autoSelected);
            clickListScrollPane.setVisible(autoSelected);

            boolean manualSelected = manualButton.isSelected();
            manualPanel.setVisible(manualSelected);

            // Ustawienie widoczności przycisku "Run Autoclicker" w zależności od wyboru trybu
            Manual.getInstance().setRunAutoclickerButtonVisible(manualSelected);
        }
    }

    public static Interface getInstance() {
        return InterfaceHolder.INSTANCE;
    }

    private static class InterfaceHolder {
        private static final Interface INSTANCE = new Interface();
    }
}
