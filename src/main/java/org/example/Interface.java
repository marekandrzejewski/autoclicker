package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
    private Run runLoop; // Dodajemy pole Run
    private final String SAVE_FILE = "manualList.ser"; // Plik do zapisywania listy

    public Interface() {
        // Ustawienia okna
        setTitle("Lista kliknięć");
        setSize(500, 500);  // Ustawienie domyślnego rozmiaru okna
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tworzenie panelu Manual z listą manualList
        manualPanel = new Manual();
        manualPanel.setVisible(true);  // Ustawiamy widoczność panelu Manual na true

        // Teraz, gdy manualPanel jest zainicjalizowany, możemy załadować listę
        loadManualList();

        // Dodajemy nasłuchiwacz zamknięcia okna
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                saveManualList();  // Zapisz listę przy zamykaniu
            }
        });

        // Ustawienie układu na GridBagLayout
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;  // Ustawiamy, aby komponenty wypełniały zarówno w poziomie, jak i pionie
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
        gbc.weightx = 1.0;  // Dodajemy wagę dla osi X
        gbc.weighty = 0.0;  // Ustawiamy zero dla osi Y, żeby radiobuttony nie skalowały się
        add(radioPanel, gbc);

        // Tworzenie listy i modelu listy
        listModel = new DefaultListModel<>();
        clickList = new JList<>(listModel);
        scrollPane = new JScrollPane(clickList);
        scrollPane.setVisible(false);  // Domyślnie ukrywamy listę

        // Dodanie listy do okna
        gbc.gridy = 1;
        gbc.weightx = 1.0;  // Pełna szerokość
        gbc.weighty = 1.0;  // Pełna wysokość - lista będzie się skalować z oknem
        add(scrollPane, gbc);

        // Tworzenie przycisku "Run Autoclicker" z nową akcją
        runAutoclickerButton = new JButton("Run Autoclicker");
        runAutoclickerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Uruchamiamy pętlę w osobnym wątku, aby nie blokować GUI
                if (runLoop == null) {
                    runLoop = new Run(manualPanel);
                    new Thread(() -> {
                        try {
                            runLoop.start();
                        } catch (InterruptedException ex) {
                            throw new RuntimeException(ex);
                        }
                    }).start();
                }
                System.out.println("Autoclicker started.");
            }
        });

        // Dodanie przycisku na dole okna
        gbc.gridy = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.weightx = 0.0;  // Przyciski nie będą się skalować
        gbc.weighty = 0.0;
        add(runAutoclickerButton, gbc);

        // Tworzenie panelu Auto z przyciskami (w osobnym panelu)
        autoPanel = new Auto(this);
        autoPanel.setVisible(false);  // Domyślnie ukrywamy panel z przyciskami

        // Dodanie panelu Auto do okna
        gbc.gridy = 3;
        gbc.weightx = 1.0;  // Ustawiamy wagi na 1, aby panel mógł się skalować
        gbc.weighty = 1.0;
        add(autoPanel, gbc);

        // Dodanie panelu Manual do okna
        gbc.gridy = 4;
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;  // Panel Manual również skalowalny
        add(manualPanel, gbc);

        // Ustawienie domyślnego wybranego radiobuttona
        manualRadioButton.setSelected(true);
        scrollPane.setVisible(false);
        runAutoclickerButton.setVisible(true);

        // Wywołujemy pack, aby dynamicznie dopasowywać rozmiar okna w przyszłości
        pack();
    }


    // Metoda dodająca współrzędne do listy
    public void addCoordinates(String coordinates) {
        listModel.addElement(coordinates);
    }

    // Metoda czyszcząca listę kliknięć
    public void clearClickList() {
        listModel.clear();  // Czyści listę kliknięć
    }

    // Metoda zapisująca kliknięcia do listy manualList
    public void saveClickListToManualList(List<String> clickListData) {
        // Nie czyścimy listy manualList, dodajemy nowe elementy do istniejących
        for (String data : clickListData) {
            try {
                // Zakładamy, że dane kliknięcia mają format "X: 792, Y: 400"
                // Wyodrębniamy liczby po etykietach "X: " i "Y: "
                String[] parts = data.split(","); // Dzielimy na X i Y
                if (parts.length == 2) {
                    // Usuwamy tekst "X: " i "Y: " oraz ewentualne białe znaki
                    int x = Integer.parseInt(parts[0].replaceAll("[^0-9]", "").trim());
                    int y = Integer.parseInt(parts[1].replaceAll("[^0-9]", "").trim());

                    // Tworzenie obiektu ManualObject i dodanie go do listy manualList
                    ManualObject obj = new ManualObject(new int[]{x, y}, List.of(), "Object", 500, 1000);
                    manualPanel.getManualList().add(obj);
                }
            } catch (NumberFormatException e) {
                System.err.println("Błąd formatu liczby: " + e.getMessage());
            }
        }
        manualPanel.refreshDisplayList(); // Odświeżenie widoku listy
    }

    // Metoda zwracająca dane kliknięć jako List<String>
    public List<String> getClickListData() {
        return IntStream.range(0, listModel.size())
                .mapToObj(listModel::getElementAt)
                .collect(Collectors.toList());
    }

    public JList<String> getClickList() {
        return clickList;
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

    // Automatyczne zapisywanie listy manualList do pliku
    private void saveManualList() {
        ManualListSerializer.serializeManualList(manualPanel.getManualList(), SAVE_FILE);
    }

    // Automatyczne wczytywanie listy manualList z pliku przy starcie
    private void loadManualList() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            List<ManualObject> loadedList = ManualListSerializer.deserializeManualList(SAVE_FILE);
            if (loadedList != null) {
                manualPanel.getManualList().addAll(loadedList);
                manualPanel.refreshDisplayList(); // Odśwież widok po wczytaniu danych
            }
        }
    }
}
