package pl.pkrysztofiak.dicomviewer.model.panels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Stream;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.collections.ObservableSet;
import javafx.geometry.Point2D;

public class PanelsModel {

    private final ObservableList<PanelModel> panels = FXCollections.observableArrayList();
    private final ObservableList<PanelModel> unmodifiablePanles = FXCollections.unmodifiableObservableList(panels);
    private final Observable<PanelModel> panelAddedObservable = JavaFxObservable.additionsOf(panels);

    private final Map<Point2D, ObservableList<PanelModel>> pointToPanels = new HashMap<>();
    
    private final Map<Double, ObservableList<PanelModel>> xToPanels = new HashMap<>();
    private final Map<Double, ObservableList<PanelModel>> yToPanels = new HashMap<>();
//    private final Map<Double, ObservableList<PanelModel>> minXToPanels = new HashMap<>();
    
    private final ObservableList<BorderModel> borders = FXCollections.observableArrayList();
    
    private final ObservableMap<Point2D, ObservableSet<PanelModel>> vertexToPanels = FXCollections.observableHashMap();
    
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
                    xToPanels.put(x, FXCollections.observableArrayList());
                }
                xToPanels.get(x).add(panel);
            });

            Stream.of(panel.getMinY(), panel.getMaxY()).forEach(y -> {
                if (!yToPanels.containsKey(y)) {
                    yToPanels.put(y, FXCollections.observableArrayList());
                }
                yToPanels.get(y).add(panel);
            });
        }
        
        for (Entry<Double, ObservableList<PanelModel>> xEntry : xToPanels.entrySet()) {
            Double x = xEntry.getKey();
            ObservableList<PanelModel> xPanels = xEntry.getValue();
            xPanels.forEach(currentPanel -> {
                xPanels.stream().filter(currentPanel::equals).forEach(nextPanel -> {
                    double currentMinY = currentPanel.getMinY();
                    double currentMaxY = currentPanel.getMaxY();
                    Stream.of(new Point2D(x, currentMinY), new Point2D(x, currentMaxY)).forEach(currentVertex -> {
                        if (!vertexToPanels.containsKey(currentVertex)) {
                            vertexToPanels.put(currentVertex, FXCollections.observableSet());
                        }
                        vertexToPanels.get(currentVertex).add(currentPanel);
                    });
                    
                    Stream.of(nextPanel.getMinY(), nextPanel.getMaxY()).forEach(y -> {
                        if (y > currentMinY && y < currentMaxY) {
                            Point2D point = new Point2D(x, y);
                            if (!vertexToPanels.containsKey(point)) {
                                vertexToPanels.put(point, FXCollections.observableSet());
                            }
                            vertexToPanels.get(point).add(currentPanel);
                        }
                    });
                });
            });
        }
        
        //TODO dodać obsługe yToPanels

        ObservableMap<Double, ObservableList<Point2D>> xToVertices = FXCollections.observableHashMap(); 
        ObservableMap<Double, ObservableList<Point2D>> yToVertices = FXCollections.observableHashMap(); 
        
        vertexToPanels.entrySet().forEach(entry -> {
            Point2D vertex = entry.getKey();
            double vertexX = vertex.getX();
            double vertexY = vertex.getY();
            ObservableSet<PanelModel> vertexPanels = entry.getValue();

            if (!xToVertices.containsKey(vertexX)) {
                xToVertices.put(vertexX, FXCollections.observableArrayList());
            }
            xToVertices.get(vertexX).add(vertex);
            
            if (!yToVertices.containsKey(vertexY)) {
                yToVertices.put(vertexY, FXCollections.observableArrayList());
            }
            yToVertices.get(vertexY).add(vertex);
        });
        
        for (Entry<Double, ObservableList<Point2D>> entry : xToVertices.entrySet()) {
            Double x = entry.getKey();
            List<Point2D> list = new ArrayList<>(entry.getValue());
            list.sort((point1, point2) -> Double.compare(point1.getY(), point1.getY()));
            for (int i = 0, j = 1; j < list.size(); i++, j++) {
                Point2D currentPoint = list.get(i);
                Point2D nextPoint = list.get(j);
                
                Set<PanelModel> set = new HashSet<>(vertexToPanels.get(currentPoint));
                set.retainAll(vertexToPanels.get(nextPoint));
                
                List<PanelModel> borderPanels = new ArrayList<>();
                
                for (PanelModel panel : set) {
                    if (panel.getMinX() == x || panel.getMaxX() == x) {
                        borderPanels.add(panel);
                    }
                }

                if (!borderPanels.isEmpty()) {
                    borders.add(new BorderModel(currentPoint, nextPoint, borderPanels));
                }
            }
        }
    }
}