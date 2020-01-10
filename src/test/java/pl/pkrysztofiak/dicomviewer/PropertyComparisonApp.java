package pl.pkrysztofiak.dicomviewer;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public class PropertyComparisonApp {

    public static void main(String[] args) {
        ObjectProperty<Double> double1 = new SimpleObjectProperty<>(0.); 
        ObjectProperty<Double> double2 = new SimpleObjectProperty<>(0.);
        DoubleProperty double3 = new SimpleDoubleProperty(0); 
        DoubleProperty double4 = new SimpleDoubleProperty(0);
        
        System.out.println(double3.equals(double4));
    }
}
