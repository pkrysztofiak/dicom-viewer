package pl.pkrysztofiak.dicomviewer;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.stage.Stage;

public class BindingApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        IntegerProperty firstProperty = new SimpleIntegerProperty();
        IntegerProperty secondProperty = new SimpleIntegerProperty();
        IntegerProperty thirdProperty = new SimpleIntegerProperty();
        
        JavaFxObservable.valuesOf(firstProperty).map(Number::intValue).subscribe(next -> {
            secondProperty.set(next);
            thirdProperty.set(next);
        });
        
        JavaFxObservable.valuesOf(secondProperty).map(Number::intValue).subscribe(next -> {
            firstProperty.set(next);
            thirdProperty.set(next);
        });
        
        JavaFxObservable.valuesOf(thirdProperty).map(Number::intValue).subscribe(next -> {
            firstProperty.set(next);
            secondProperty.set(next);
        });
        
        
        
        JavaFxObservable.valuesOf(firstProperty).subscribe(next -> {
            System.out.println("first=" + next);
        });
        
        JavaFxObservable.valuesOf(secondProperty).subscribe(next -> {
            System.out.println("second=" + next);
        });
        
        JavaFxObservable.valuesOf(thirdProperty).subscribe(next -> {
            System.out.println("third=" + next);
        });
        
        firstProperty.set(1);
        secondProperty.set(2);
    }
}