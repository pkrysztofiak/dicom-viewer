package pl.pkrysztofiak.dicomviewer.model.panels;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PanelsModel {

    private final ObservableList<PanelModel> panels = FXCollections.observableArrayList();
    private final ObservableList<PanelModel> unmodifiablePanles = FXCollections.unmodifiableObservableList(panels);
    private final Observable<PanelModel> panelAddedObservable = JavaFxObservable.additionsOf(panels);

    public void add(PanelModel panelModel) {
        panels.add(panelModel);
    }
    
    public ObservableList<PanelModel> getPanels() {
        return unmodifiablePanles;
    }
    
    public Observable<PanelModel> panelAddedObservable() {
        return panelAddedObservable;
    }
}