package org.example;

import java.util.ArrayList;
import java.util.List;

public class ManualObjectsList {
    private List<ManualObject> objectList;

    // Konstruktor inicjalizujący listę
    public ManualObjectsList() {
        objectList = new ArrayList<>();
    }

    // Dodanie obiektu do listy
    public void addObject(ManualObject manualObject) {
        objectList.add(manualObject);
    }

    // Pobranie całej listy obiektów
    public List<ManualObject> getObjectList() {
        return objectList;
    }

    // Usunięcie obiektu z listy
    public void removeObject(ManualObject manualObject) {
        objectList.remove(manualObject);
    }

    // Wyczyszczenie całej listy
    public void clearList() {
        objectList.clear();
    }

    @Override
    public String toString() {
        return "ManualObjectsList{" +
                "objectList=" + objectList +
                '}';
    }
}
