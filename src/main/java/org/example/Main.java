package org.example;

public class Main {
    public static void main(String[] args) {
        // Tworzenie instancji klasy Interface
        Interface ui = new Interface();
        ui.setVisible(true);

        // Tworzenie instancji klasy ClickListen
        ClickListen clickListen = new ClickListen(ui);

        // Rejestracja nasłuchiwacza zdarzeń myszy
        clickListen.startListening();
    }
}
