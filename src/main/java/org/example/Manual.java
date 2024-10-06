package org.example;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
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
        setLayout(new BorderLayout());  // Używamy BorderLayout dla automatycznego rozciągania komponentów

        JLabel label = new JLabel("Manual Mode");
        add(label, BorderLayout.NORTH);  // Label na górze

        // Tworzymy JList i osadzamy w JScrollPane
        displayList = new JList<>();
        displayList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // Tylko jeden element może być zaznaczony naraz
        JScrollPane scrollPane = new JScrollPane(displayList);

        // Dodajemy JScrollPane do głównego panelu, aby zajmował całą dostępną przestrzeń w środku
        add(scrollPane, BorderLayout.CENTER);

        // Nasłuchiwanie kliknięć myszy na liście - aktualizacja selectedIndex
        displayList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (displayList.getSelectedIndex() != -1) {
                    selectedIndex = displayList.getSelectedIndex(); // Ustawianie selectedIndex
                }

                if (e.getClickCount() == 2 && selectedIndex != -1) {
                    openActionsWindow(); // Otwieranie okienka akcji przy podwójnym kliknięciu
                }
            }
        });

        // Dodanie obsługi przeciągania i upuszczania elementów w liście
        displayList.setDragEnabled(true); // Włączenie przeciągania w liście
        displayList.setDropMode(DropMode.INSERT); // Wybór trybu upuszczania między elementami
        displayList.setTransferHandler(new ListItemTransferHandler()); // Ustawienie TransferHandler

        // Panel z przyciskami "Add Example Object", "Delete Object", i "Copy Element"
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Example Object");
        JButton deleteButton = new JButton("Delete Object");  // Nowy przycisk
        JButton copyButton = new JButton("Copy Element");  // Przycisk kopiowania

        // Dodawanie nowego obiektu do listy manualList
        addButton.addActionListener(e -> {
            manualList.add(new ManualObject(new int[]{0, 0}, List.of(), "Object", 500, 1000));
            updateDisplayList();
        });

        // Usuwanie zaznaczonego obiektu z listy manualList
        deleteButton.addActionListener(e -> {
            if (selectedIndex != -1 && selectedIndex < manualList.size()) {
                manualList.remove(selectedIndex);  // Usunięcie obiektu z listy
                updateDisplayList();  // Odświeżenie widoku listy
                selectedIndex = -1;  // Resetowanie wybranego indeksu
            } else {
                JOptionPane.showMessageDialog(null, "Please select an object to delete.");
            }
        });

        // Kopiowanie zaznaczonego obiektu z listy manualList
        copyButton.addActionListener(e -> {
            if (selectedIndex != -1 && selectedIndex < manualList.size()) {
                // Pobieranie oryginalnego obiektu i tworzenie jego kopii
                ManualObject original = manualList.get(selectedIndex);
                ManualObject copied = new ManualObject(
                        original.getClickCoords().clone(), // Kopiowanie tablicy współrzędnych
                        new ArrayList<>(original.getKeys()), // Kopiowanie listy klawiszy
                        original.getName(),
                        original.getPreDelay(),
                        original.getPostDelay()
                );
                manualList.add(copied);  // Dodanie kopii do listy
                updateDisplayList();  // Odświeżenie widoku listy
            } else {
                JOptionPane.showMessageDialog(null, "Please select an object to copy.");
            }
        });

        // Dodanie przycisków do panelu
        buttonPanel.add(addButton);
        buttonPanel.add(copyButton);  // Dodajemy przycisk kopiowania
        buttonPanel.add(deleteButton);

        // Dodanie panelu przycisków na dole
        add(buttonPanel, BorderLayout.SOUTH);

        updateDisplayList();  // Inicjalne odświeżenie listy
    }

    // Metoda aktualizująca wyświetlaną listę obiektów
    private void updateDisplayList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (ManualObject obj : manualList) {
            model.addElement(obj.toString());  // Assuming toString() method returns a string representation
        }
        displayList.setModel(model);
    }

    private void openActionsWindow() {
        JFrame actionsFrame = new JFrame("Actions");
        actionsFrame.setSize(400, 300);
        actionsFrame.setLocationRelativeTo(null);

        // Przekazujemy selectedIndex i manualList do edytora
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

    // Klasa wewnętrzna obsługująca przeciąganie i upuszczanie elementów w JList
    private class ListItemTransferHandler extends TransferHandler {
        private int draggedIndex = -1;

        @Override
        protected Transferable createTransferable(JComponent c) {
            draggedIndex = displayList.getSelectedIndex();
            return new StringSelection(displayList.getSelectedValue());
        }

        @Override
        public int getSourceActions(JComponent c) {
            return MOVE;
        }

        @Override
        public boolean canImport(TransferSupport support) {
            return support.isDataFlavorSupported(DataFlavor.stringFlavor);
        }

        @Override
        public boolean importData(TransferSupport support) {
            if (!canImport(support)) {
                return false;
            }

            JList.DropLocation dropLocation = (JList.DropLocation) support.getDropLocation();
            int dropIndex = dropLocation.getIndex();

            try {
                String draggedValue = (String) support.getTransferable().getTransferData(DataFlavor.stringFlavor);

                // Przesuwamy element w liście manualList
                ManualObject movedObject = manualList.remove(draggedIndex);
                manualList.add(dropIndex, movedObject);

                updateDisplayList(); // Odświeżenie listy po przesunięciu elementu
                displayList.setSelectedIndex(dropIndex); // Ustawienie nowo przesuniętego elementu jako zaznaczonego

                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
