package pl.pkrysztofiak.dicomviewer.model.panels;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class PanelsModel {

    private final ObservableList<PanelModel> panels = FXCollections.observableArrayList();
    private final ObservableList<PanelModel> unmodifiablePanles = FXCollections.unmodifiableObservableList(panels);
    private final Observable<PanelModel> panelAddedObservable = JavaFxObservable.additionsOf(panels);

    public PanelsModel() {
        panelAddedObservable.subscribe(this::onPanelAdded);
    }
    
    public void add(PanelModel panelModel) {
        panels.add(panelModel);
    }
    
    public ObservableList<PanelModel> getPanels() {
        return unmodifiablePanles;
    }
    
    public Observable<PanelModel> panelAddedObservable() {
        return panelAddedObservable;
    }
    
    private void onPanelAdded(PanelModel addedPanelModel) {
        addedPanelModel.setParent(this);

        for (PanelModel panelModel : panels) {
            if (!panelModel.equals(addedPanelModel)) {
                
                if (addedPanelModel.getMaxX() == panelModel.getMinX()) {
                    if (!(addedPanelModel.getMinY() > panelModel.getMaxY()) || !(addedPanelModel.getMaxY() < panelModel.getMinY())) {
                        //TODO dopisać takeUntil na panelModel.removedObservable();
                        addedPanelModel.maxXObservable.subscribe(panelModel::setMinX);
                        panelModel.minXObservable.subscribe(addedPanelModel::setMaxX);
                    }
                }
                
                if (addedPanelModel.getMaxY() == panelModel.getMinY()) {
                    if (!(addedPanelModel.getMinX() > panelModel.getMaxX()) || !(addedPanelModel.getMaxX() < panelModel.getMinX())) {
                        addedPanelModel.maxYObservable.subscribe(panelModel::setMinY);
                        panelModel.minYObservable.subscribe(addedPanelModel::setMaxY);
                    }
                }

                if (addedPanelModel.getMinX() == panelModel.getMaxX()) {
                    if (!(addedPanelModel.getMinY() > panelModel.getMaxY()) || !(addedPanelModel.getMaxY() < panelModel.getMinY())) {
                        addedPanelModel.minXObservable.subscribe(panelModel::setMaxX);
                        panelModel.maxXObservable.subscribe(addedPanelModel::setMinX);
                    }
                }
                
                if (addedPanelModel.getMinY() == panelModel.getMaxY()) {
                    if (!(addedPanelModel.getMinX() > panelModel.getMaxX()) || !(addedPanelModel.getMaxX() < panelModel.getMinX())) {
                        addedPanelModel.minYObservable.subscribe(panelModel::setMaxY);
                        panelModel.maxYObservable.subscribe(addedPanelModel::setMinY);
                    }
                }
            }
        }
    }
}