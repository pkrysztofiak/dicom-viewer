package pl.pkrysztofiak.dicomviewer.model.panels;

import java.util.HashSet;
import java.util.Set;

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

        refresh(addedPanelModel);
        panels.stream().filter(panelModel -> !panelModel.equals(addedPanelModel)).forEach(this::refresh);
    }

    private void refresh(PanelModel panelModelToRefresh) {
        Set<PanelModel> adjacentPanels = new HashSet<>();
        
        for (PanelModel panelModel : panels) {
            if (!panelModel.equals(panelModelToRefresh)) {
                
                if (panelModelToRefresh.getMaxX() == panelModel.getMinX()) {
                    if (!(panelModelToRefresh.getMinY() > panelModel.getMaxY()) || !(panelModelToRefresh.getMaxY() < panelModel.getMinY())) {
                        //TODO dopisać takeUntil na panelModel.removedObservable();
//                        addedPanelModel.maxXObservable.subscribe(panelModel::setMinX);
//                        panelModel.minXObservable.subscribe(addedPanelModel::setMaxX);
                        adjacentPanels.add(panelModel);
//                        rightAdjacentPanels.add(panelModel);
                    }
                }
                
                if (panelModelToRefresh.getMaxY() == panelModel.getMinY()) {
                    if (!(panelModelToRefresh.getMinX() > panelModel.getMaxX()) || !(panelModelToRefresh.getMaxX() < panelModel.getMinX())) {
//                        addedPanelModel.maxYObservable.subscribe(panelModel::setMinY);
//                        panelModel.minYObservable.subscribe(addedPanelModel::setMaxY);
                        adjacentPanels.add(panelModel);
//                        bottomAdjacentPanels.add(panelModel);
                    }
                }

                if (panelModelToRefresh.getMinX() == panelModel.getMaxX()) {
                    if (!(panelModelToRefresh.getMinY() > panelModel.getMaxY()) || !(panelModelToRefresh.getMaxY() < panelModel.getMinY())) {
//                        addedPanelModel.minXObservable.subscribe(panelModel::setMaxX);
//                        panelModel.maxXObservable.subscribe(addedPanelModel::setMinX);
                        adjacentPanels.add(panelModel);
//                        leftAdjacentPanels.add(panelModel);
                    }
                }
                
                if (panelModelToRefresh.getMinY() == panelModel.getMaxY()) {
                    if (!(panelModelToRefresh.getMinX() > panelModel.getMaxX()) || !(panelModelToRefresh.getMaxX() < panelModel.getMinX())) {
//                        addedPanelModel.minYObservable.subscribe(panelModel::setMaxY);
//                        panelModel.maxYObservable.subscribe(addedPanelModel::setMinY);
                        adjacentPanels.add(panelModel);
//                        topAdjacentPanels.add(panelModel);
                    }
                }
            }
        }

        panelModelToRefresh.adjacentPanels.setAll(adjacentPanels);
        
        
//        panelModelToRefresh.topAdjacementPanels.setAll(topAdjacentPanels);
//        panelModelToRefresh.rightAdjacementPanels.setAll(rightAdjacentPanels);
//        panelModelToRefresh.bottomAdjacementPanels.setAll(bottomAdjacentPanels);
//        panelModelToRefresh.leftAdjacementPanels.setAll(leftAdjacentPanels);
    }
}