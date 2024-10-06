package org.example;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

public class ManualObject implements Serializable {
    private static final long serialVersionUID = 1L;  // Zalecane do wersjonowania klasy

    private int[] clickCoords;
    private List<String> keys;
    private String name;
    private int preDelay;
    private int postDelay;

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
        return "Name: " + name +
                ", Coordinates: " + Arrays.toString(clickCoords) +
                ", Keys: " + keys.toString() +
                ", Pre-Delay: " + preDelay + "ms" +
                ", Post-Delay: " + postDelay + "ms";
    }
}
