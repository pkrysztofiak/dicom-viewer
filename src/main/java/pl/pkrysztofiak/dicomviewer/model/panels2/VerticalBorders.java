package pl.pkrysztofiak.dicomviewer.model.panels2;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VerticalBorders {

    public final ObservableList<VerticalBorder> borders = FXCollections.observableArrayList();
    public final Observable<VerticalBorder> borderAddedObservable = JavaFxObservable.additionsOf(borders);
    
    public VerticalBorders() {
        borderAddedObservable.subscribe(this::onBorderAdded);
    }
    
    public Optional<VerticalBorder> get(double x) {
        return borders.stream().filter(border -> Double.compare(x, border.getX()) == 0).findFirst();
    }
    
    private void onBorderAdded(VerticalBorder addedBorder) {
        
    }
}