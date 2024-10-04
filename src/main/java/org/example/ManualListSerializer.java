package org.example;

import java.io.*;
import java.util.List;

public class ManualListSerializer {

    // Metoda do serializacji listy manualList
    public static void serializeManualList(List<ManualObject> manualList, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(manualList);
            System.out.println("manualList została zapisana do pliku.");
        } catch (IOException e) {
            System.err.println("Błąd podczas zapisu manualList: " + e.getMessage());
        }
    }

    // Metoda do deserializacji listy manualList
    public static List<ManualObject> deserializeManualList(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            List<ManualObject> manualList = (List<ManualObject>) ois.readObject();
            System.out.println("manualList została odczytana z pliku.");
            return manualList;
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Błąd podczas odczytu manualList: " + e.getMessage());
            return null;
        }
    }
}
