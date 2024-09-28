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
                // Pobranie wartości preDelay z ManualObject
                int preDelay = obj.getPreDelay(); // Pobiera wartość preDelay dla danego obiektu
                Thread.sleep(preDelay); // Używa preDelay przed wykonaniem akcji

                // Sprawdzenie, czy obiekt ma pole clickCoords i wykonanie kliknięcia
                if (obj.getClickCoords() != null) {
                    int[] coords = obj.getClickCoords();
                    if (coords.length == 2) {
                        int x = coords[0];
                        int y = coords[1];
                        robot.mouseMove(x, y);
                        Thread.sleep(1000);
                        robot.mousePress(KeyEvent.BUTTON1_DOWN_MASK);
                        robot.mouseRelease(KeyEvent.BUTTON1_DOWN_MASK);
                        Thread.sleep(1000);
                    }
                }

                // Sprawdzenie, czy obiekt ma pole keys i wykonanie uderzeń w klawisze
                if (obj.getKeys() != null) {
                    for (String key : obj.getKeys()) {
                        int keyCode = getKeyCode(key);
                        if (keyCode != -1) {
                            robot.keyPress(keyCode);
                            robot.keyRelease(keyCode);
                            Thread.sleep(1000);
                        }
                    }
                }

                // Pobranie wartości postDelay z ManualObject
                int postDelay = obj.getPostDelay(); // Pobiera wartość postDelay dla danego obiektu
                Thread.sleep(postDelay); // Używa postDelay po wykonaniu akcji
            }

            // Opcjonalne ogólne opóźnienie między pętlami, jeśli jest potrzebne
            try {
                Thread.sleep(3000);
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
            case "D": return KeyEvent.VK_D;
            case "E": return KeyEvent.VK_E;
            case "F": return KeyEvent.VK_F;
            case "G": return KeyEvent.VK_G;
            case "H": return KeyEvent.VK_H;
            case "I": return KeyEvent.VK_I;
            case "J": return KeyEvent.VK_J;
            case "K": return KeyEvent.VK_K;
            case "L": return KeyEvent.VK_L;
            case "M": return KeyEvent.VK_M;
            case "N": return KeyEvent.VK_N;
            case "O": return KeyEvent.VK_O;
            case "P": return KeyEvent.VK_P;
            case "Q": return KeyEvent.VK_Q;
            case "R": return KeyEvent.VK_R;
            case "S": return KeyEvent.VK_S;
            case "T": return KeyEvent.VK_T;
            case "U": return KeyEvent.VK_U;
            case "V": return KeyEvent.VK_V;
            case "W": return KeyEvent.VK_W;
            case "X": return KeyEvent.VK_X;
            case "Y": return KeyEvent.VK_Y;
            case "Z": return KeyEvent.VK_Z;

            case "1": return KeyEvent.VK_1;
            case "2": return KeyEvent.VK_2;
            case "3": return KeyEvent.VK_3;
            case "4": return KeyEvent.VK_4;
            case "5": return KeyEvent.VK_5;
            case "6": return KeyEvent.VK_6;
            case "7": return KeyEvent.VK_7;
            case "8": return KeyEvent.VK_8;
            case "9": return KeyEvent.VK_9;
            case "0": return KeyEvent.VK_0;

            case "SPACE": return KeyEvent.VK_SPACE;
            case "ENTER": return KeyEvent.VK_ENTER;
            case "SHIFT": return KeyEvent.VK_SHIFT;
            case "CTRL": return KeyEvent.VK_CONTROL;
            case "ALT": return KeyEvent.VK_ALT;
            case "TAB": return KeyEvent.VK_TAB;
            case "BACKSPACE": return KeyEvent.VK_BACK_SPACE;
            case "ESC": return KeyEvent.VK_ESCAPE;

            default: return -1; // Zwraca -1, jeśli klawisz nie jest obsługiwany
        }
    }

}
