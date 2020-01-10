package pl.pkrysztofiak.dicomviewer.model.panels2;

import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.subjects.PublishSubject;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class VerticalBorder {

    public final ObservableList<Panel> panels = FXCollections.observableArrayList();
    public final Observable<Panel> panelAddedObservable = JavaFxObservable.additionsOf(panels);
    public final Observable<Panel> panelRemovedObservable = JavaFxObservable.removalsOf(panels);
    
    private final ObjectProperty<Double> xPropety = new SimpleObjectProperty<Double>(0.);
    
    private final PublishSubject<Optional<Void>> removeObservable = PublishSubject.create();
    
    public VerticalBorder(double x) {
        xPropety.set(x);
        panelAddedObservable.subscribe(this::onPanelAdded);
    }
    
    public double getX() {
        return xPropety.get();
    }
    
    private void onPanelAdded(Panel panel) {
        if (panel.getMinX() == xPropety.get()) {
            panel.minXObservable.takeUntil(panelRemovedObservable.filter(panel::equals)).subscribe(minX -> onXChanged(panel));
        } else {
            panel.maxXObservable.takeUntil(panelRemovedObservable.filter(panel::equals)).subscribe(maxX -> onXChanged(panel));
        }
    }

    private void onXChanged(Panel panel) {
        panels.remove(panel);
        if (panels.isEmpty()) {
            removeObservable.onNext(Optional.empty());
        }
    }
}