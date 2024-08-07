package org.example;

import java.util.List;
import java.util.ArrayList;

public class ManualItem {
    private String name;
    private int preDelay;
    private int postDelay;
    private List<String> keys;
    private String clickCoordinates;

    public ManualItem() {
        this("", 0, 0, new ArrayList<>(), null);
    }

    public ManualItem(String name, int preDelay, int postDelay, List<String> keys, String clickCoordinates) {
        this.name = name;
        this.preDelay = preDelay;
        this.postDelay = postDelay;
        this.keys = keys;
        this.clickCoordinates = clickCoordinates;
    }

    public String getName() {
        return name;
    }

    public int getPreDelay() {
        return preDelay;
    }

    public int getPostDelay() {
        return postDelay;
    }

    public List<String> getKeys() {
        return keys;
    }

    public String getClickCoordinates() {
        return clickCoordinates;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPreDelay(int preDelay) {
        this.preDelay = preDelay;
    }

    public void setPostDelay(int postDelay) {
        this.postDelay = postDelay;
    }

    public void setKeys(List<String> keys) {
        this.keys = keys;
    }

    public void setClickCoordinates(String clickCoordinates) {
        this.clickCoordinates = clickCoordinates;
    }

    @Override
    public String toString() {
        return name; // Or any representation you prefer
    }
}
