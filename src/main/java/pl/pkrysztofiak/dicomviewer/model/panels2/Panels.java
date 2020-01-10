package pl.pkrysztofiak.dicomviewer.model.panels2;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;

public class Panels {

    private final ObservableList<Panel> panels = FXCollections.observableArrayList();
    private final Observable<Panel> panelAddedObservable = JavaFxObservable.additionsOf(panels);
    
    private final ObservableMap<Panel, Border> panelToTopBorder = FXCollections.observableHashMap();
    private final ObservableMap<Panel, Border> panelToRightBorder = FXCollections.observableHashMap();
    private final ObservableMap<Panel, Border> panelToBottomBorder = FXCollections.observableHashMap();
    private final ObservableMap<Panel, Border> panelToLeftBorder = FXCollections.observableHashMap();
    
    private final VerticalBorders verticalBorders = new VerticalBorders();
    
    public Panels() {
        panelAddedObservable.subscribe(this::onPanelAdded);
        
    }
    
    private void onPanelAdded(Panel addedPanel) {
        
        Optional<VerticalBorder> optionalVerticalBorder = verticalBorders.get(addedPanel.getMinX());
        if (optionalVerticalBorder.isPresent()) {
            VerticalBorder verticalBorder = optionalVerticalBorder.get();
            verticalBorder.panels.add(addedPanel);
        } else {
            VerticalBorder verticalBorder = new VerticalBorder(addedPanel.getMinX());
            verticalBorder.panels.add(addedPanel);
            verticalBorders.borders.add(verticalBorder);
        }
//        verticalBorders.get(addedPanel.getMinX())
        
        
//        verticalBorders.find(addedPanel.getMinX()).ifPresent(verticalBorder -> {
//            
//        });
        
//        double minX = addedPanel.getMinX();
//        if (!xToVerticalBorder.containsKey(minX)) {
//            xToVerticalBorder.put(minX, new VerticalBorder(minX));
//        }
//        xToVerticalBorder.get(minX).panels.add(addedPanel);
        
//        addedPanel.minXChangeObservable.subscribe(change -> {
//            Double oldMinX = change.getOldVal();
//            Double minX = change.getNewVal();
//            if (xToBorder.containsKey(oldMinX)) {
//                Border border = xToBorder.get(oldMinX);
//                border.panels.remove()
//            }
//            
//        });
    }
    
    private List<Panel> findTopAdjacentPanels(Panel panel) {
        List<Panel> adjacentPanels = new ArrayList<>();
        for (Panel nextPanel : panels) {
            if (!nextPanel.equals(panel)) {
                if (nextPanel.getMaxY() == panel.getMinY()) {
                    boolean unconnected = nextPanel.getMaxX() < panel.getMinX() || nextPanel.getMinX() > panel.getMaxX();
                    if (!unconnected) {
                        adjacentPanels.add(nextPanel);
                    }
                }
            }
        }
        return adjacentPanels;
    }
}