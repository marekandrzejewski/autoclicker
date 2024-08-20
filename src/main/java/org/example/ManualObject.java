package org.example;

import java.util.List;

public class ManualObject {
    private int[] clickCoords;  // Przechowuje współrzędne kliknięcia (2 liczby)
    private List<String> keys;  // Lista klawiszy w formacie String
    private String name;        // Nazwa obiektu
    private int preDelay;       // Czas przed kliknięciem
    private int postDelay;      // Czas po kliknięciu

    // Konstruktor
    public ManualObject(int[] clickCoords, List<String> keys, String name, int preDelay, int postDelay) {
        this.clickCoords = clickCoords;
        this.keys = keys;
        this.name = name;
        this.preDelay = preDelay;
        this.postDelay = postDelay;
    }

    // Gettery i settery
    public int[] getClickCoords() {
        return clickCoords;
    }

    public void setClickCoords(int[] clickCoords) {
        this.clickCoords = clickCoords;
    }

    public List<String> getKeys() {
        return keys;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPreDelay() {
        return preDelay;
    }

    public void setPreDelay(int preDelay) {
        this.preDelay = preDelay;
    }

    public int getPostDelay() {
        return postDelay;
    }

    public void setPostDelay(int postDelay) {
        this.postDelay = postDelay;
    }

    @Override
    public String toString() {
        return "ManualObject{" +
                "clickCoords=" + clickCoords[0] + ", " + clickCoords[1] +
                ", keys=" + keys +
                ", name='" + name + '\'' +
                ", preDelay=" + preDelay +
                ", postDelay=" + postDelay +
                '}';
    }
}
