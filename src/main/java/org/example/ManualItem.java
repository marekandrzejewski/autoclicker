package org.example;

public class ManualItem {
    private String name;
    private String perform;
    private int preDelay;
    private int postDelay;

    public ManualItem(String name, String perform, int preDelay, int postDelay) {
        this.name = name;
        this.perform = perform;
        this.preDelay = preDelay;
        this.postDelay = postDelay;
    }

    public String getName() {
        return name;
    }

    public String getPerform() {
        return perform;
    }

    public int getPreDelay() {
        return preDelay;
    }

    public int getPostDelay() {
        return postDelay;
    }

    @Override
    public String toString() {
        return String.format("%s - %s (PreDelay: %d, PostDelay: %d)", name, perform, preDelay, postDelay);
    }
}
