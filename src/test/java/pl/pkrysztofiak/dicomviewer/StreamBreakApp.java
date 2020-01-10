package pl.pkrysztofiak.dicomviewer;

import javafx.collections.FXCollections;

public class StreamBreakApp {

    public static void main(String[] args) {
        
        FXCollections.observableArrayList("one", "two", "three").forEach(next -> {
            System.out.println("next=" + next);
            if (next.equals("two")) {
                return;
            }
        });
    }
}