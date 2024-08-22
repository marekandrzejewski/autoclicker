package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Interface extends JFrame {
    private DefaultListModel<String> listModel;
    private JList<String> clickList;
    private JRadioButton manualRadioButton;
    private JRadioButton autoRadioButton;
    private ButtonGroup modeGroup;
    private JScrollPane scrollPane;
    private JButton runAutoclickerButton;
    private Auto autoPanel; // Panel z przyciskami
    private Manual manualPanel; // Panel z listą manualList

    public Interface() {
        // Ustawienia okna
        setTitle("Lista kliknięć");
        setSize(500, 500);  // Ustawienie domyślnego rozmiaru okna na 500x500 pikseli
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Ustawienie układu na GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;  // Wypełnienie całej przestrzeni
        gbc.insets = new Insets(5, 5, 5, 5);

        // Tworzenie radiobuttonów
        manualRadioButton = new JRadioButton("Manual");
        autoRadioButton = new JRadioButton("Auto");

        // Grupa radiobuttonów (aby tylko jeden mógł być zaznaczony naraz)
        modeGroup = new ButtonGroup();
        modeGroup.add(manualRadioButton);
        modeGroup.add(autoRadioButton);

        // Nasłuchiwacz akcji dla radiobuttonów
        manualRadioButton.addActionListener(new RadioListener());
        autoRadioButton.addActionListener(new RadioListener());

        // Panel na radiobuttony
        JPanel radioPanel = new JPanel();
        radioPanel.setLayout(new FlowLayout());
        radioPanel.add(manualRadioButton);
        radioPanel.add(autoRadioButton);

        // Dodanie radiobuttonów do okna
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.weighty = 0.1;  // Mały ciężar dla radiobuttonów
        add(radioPanel, gbc);

        // Tworzenie listy i modelu listy
        listModel = new DefaultListModel<>();
        clickList = new JList<>(listModel);
        scrollPane = new JScrollPane(clickList);
        scrollPane.setVisible(false);  // Domyślnie ukrywamy listę

        // Dodanie listy do okna
        gbc.gridy = 1;
        gbc.weighty = 0.8;  // Ustawienie większego ciężaru dla listy
        add(scrollPane, gbc);

        // Tworzenie przycisku "Run Autoclicker" (na razie bez akcji)
        runAutoclickerButton = new JButton("Run Autoclicker");
        runAutoclickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Na razie nic nie robi
                System.out.println("Run Autoclicker clicked (placeholder action).");
            }
        });

        // Dodanie przycisku na dole okna
        gbc.gridy = 2;
        gbc.weighty = 0.1;  // Mały ciężar dla przycisku
        gbc.fill = GridBagConstraints.NONE;
        add(runAutoclickerButton, gbc);

        // Tworzenie panelu Auto z przyciskami (w osobnym panelu)
        autoPanel = new Auto(this);
        autoPanel.setVisible(false);  // Domyślnie ukrywamy panel z przyciskami

        // Dodanie panelu Auto do okna
        gbc.gridy = 3;
        add(autoPanel, gbc);

        // Tworzenie panelu Manual z listą manualList
        manualPanel = new Manual();
        manualPanel.setVisible(true);  // Ustawiamy widoczność panelu Manual na true

        // Dodanie panelu Manual do okna
        gbc.gridy = 1;  // Używamy tego samego miejsca co lista (y = 1)
        gbc.weighty = 0.8;  // Ustawienie większego ciężaru dla panelu Manual
        add(manualPanel, gbc);

        // Ustawienie domyślnego wybranego radiobuttona
        manualRadioButton.setSelected(true);
        scrollPane.setVisible(false);
        runAutoclickerButton.setVisible(true);

        // Wywołanie pack() i odświeżenie GUI
        pack();
        manualPanel.revalidate();
        manualPanel.repaint();

        // Ustawienie pożądanego rozmiaru okna
        setSize(500, 500);
    }

    // Metoda dodająca współrzędne do listy
    public void addCoordinates(String coordinates) {
        listModel.addElement(coordinates);
    }

    // Metoda czyszcząca listę kliknięć
    public void clearClickList() {
        listModel.clear();  // Czyści listę kliknięć
    }

    // Wewnętrzna klasa nasłuchująca zmiany stanu radiobuttonów
    private class RadioListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (autoRadioButton.isSelected()) {
                // Jeśli wybrano tryb "Auto", pokaż listę i panel z przyciskami, ukryj panel Manual
                scrollPane.setVisible(true);
                autoPanel.setVisible(true);
                manualPanel.setVisible(false);
                pack();  // Dopasowanie rozmiaru okna
            } else {
                // Jeśli wybrano tryb "Manual", pokaż panel Manual, ukryj panel Auto
                scrollPane.setVisible(false);
                autoPanel.setVisible(false);
                manualPanel.setVisible(true);
                pack();  // Dopasowanie rozmiaru okna
            }
        }
    }
}
