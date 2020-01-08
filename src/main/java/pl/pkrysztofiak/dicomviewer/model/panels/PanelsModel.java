package pl.pkrysztofiak.dicomviewer.model.panels;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

public class PanelsModel {

    private final ObservableList<PanelModel> panels = FXCollections.observableArrayList();
    private final ObservableList<PanelModel> unmodifiablePanles = FXCollections.unmodifiableObservableList(panels);
    private final Observable<PanelModel> panelAddedObservable = JavaFxObservable.additionsOf(panels);

    private final Map<Point2D, ObservableList<PanelModel>> pointToPanels = new HashMap<>();
    
    private final Map<Double, ObservableList<PanelModel>> xToPanels = new HashMap<>();
    private final Map<Double, ObservableList<PanelModel>> yToPanels = new HashMap<>();
//    private final Map<Double, ObservableList<PanelModel>> minXToPanels = new HashMap<>();
    
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
                        adjacentPanels.add(panelModel);
                    }
                }
                
                if (panelModelToRefresh.getMaxY() == panelModel.getMinY()) {
                    if (!(panelModelToRefresh.getMinX() > panelModel.getMaxX()) || !(panelModelToRefresh.getMaxX() < panelModel.getMinX())) {
                        adjacentPanels.add(panelModel);
                    }
                }

                if (panelModelToRefresh.getMinX() == panelModel.getMaxX()) {
                    if (!(panelModelToRefresh.getMinY() > panelModel.getMaxY()) || !(panelModelToRefresh.getMaxY() < panelModel.getMinY())) {
                        adjacentPanels.add(panelModel);
                    }
                }
                
                if (panelModelToRefresh.getMinY() == panelModel.getMaxY()) {
                    if (!(panelModelToRefresh.getMinX() > panelModel.getMaxX()) || !(panelModelToRefresh.getMaxX() < panelModel.getMinX())) {
                        adjacentPanels.add(panelModel);
                    }
                }
            }
        }

        panelModelToRefresh.adjacentPanels.setAll(adjacentPanels);
    }
    
    private void fillPoints() {
        for (PanelModel panel : panels) {
            Stream.of(panel.getMinX(), panel.getMaxX()).forEach(x -> {
                if (!xToPanels.containsKey(x)) {
                    xToPanels.put(x, FXCollections.observableArrayList(panel));
                }
                xToPanels.get(x).add(panel);
            });

            Stream.of(panel.getMinY(), panel.getMaxY()).forEach(y -> {
                if (!yToPanels.containsKey(y)) {
                    yToPanels.put(y, FXCollections.observableArrayList(panel));
                }
                yToPanels.get(y).add(panel);
            });
        }
        
        for (Entry<Double, ObservableList<PanelModel>> entry : xToPanels.entrySet()) {
            
        }
    }
}