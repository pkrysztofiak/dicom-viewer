package pl.pkrysztofiak.dicomviewer;

import java.util.List;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CopyList {

    public static void main(String[] args) {
        ObservableList<String> numbers = FXCollections.observableArrayList("one", "two", "three");
        
        List<String> numbersCopy = numbers.stream().collect(Collectors.toList());
        numbersCopy.remove("one");

        System.out.println("numbers");
        numbers.stream().forEach(System.out::println);
        System.out.println("numbersCopy");
        numbersCopy.stream().forEach(System.out::println);
    }    
}