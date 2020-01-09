package pl.pkrysztofiak.dicomviewer;

import java.util.List;

import javafx.collections.FXCollections;

public class StreamCurrentNextApp {

    public static void main(String[] args) {
        List<String> numbers = FXCollections.observableArrayList("one", "two", "three");
        
        numbers.stream().reduce((current, next) -> {
            System.out.println("current=" + current + ", next=" + next);
            return next;
        });
    }
}