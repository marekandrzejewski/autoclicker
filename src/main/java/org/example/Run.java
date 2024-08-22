package org.example;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.List;

public class Run {
    private Manual manualPanel;

    public Run(Manual manualPanel) {
        this.manualPanel = manualPanel;
    }

    public void start() throws InterruptedException {
        // Tworzymy obiekt klasy Robot, który pozwala na symulowanie akcji użytkownika
        Robot robot;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
            return;
        }

        // Nieskończona pętla obsługująca elementy listy manualList
        while (true) {
            List<ManualObject> manualList = manualPanel.getManualList();

            for (ManualObject obj : manualList) {
                // Sprawdzenie, czy obiekt ma pole clickCoords i wykonanie kliknięcia
                if (obj.getClickCoords() != null) {
                    int[] coords = obj.getClickCoords();
                    if (coords.length == 2) {
                        int x = coords[0];
                        int y = coords[1];
                        robot.mouseMove(x, y);
                        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
                    }
                }
                Thread.sleep(3000);
                // Sprawdzenie, czy obiekt ma pole keys i wykonanie uderzeń w klawisze
                if (obj.getKeys() != null) {
                    for (String key : obj.getKeys()) {
                        int keyCode = getKeyCode(key);
                        if (keyCode != -1) {
                            robot.keyPress(keyCode);
                            robot.keyRelease(keyCode);
                        }
                    }
                }
            }

            // Opcjonalnie, możemy dodać opóźnienie, aby zapobiec zbyt szybkiemu przechodzeniu przez listę
            try {
                Thread.sleep(3000); // 100 ms przerwy
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    // Metoda do mapowania stringów na kody klawiszy
    private int getKeyCode(String key) {
        switch (key.toUpperCase()) {
            case "A": return KeyEvent.VK_A;
            case "B": return KeyEvent.VK_B;
            case "C": return KeyEvent.VK_C;
            // Dodaj więcej przypadków w razie potrzeby
            // W ten sposób możemy obsłużyć różne klawisze
            default: return -1; // Zwraca -1, jeśli klawisz nie jest obsługiwany
        }
    }
}
