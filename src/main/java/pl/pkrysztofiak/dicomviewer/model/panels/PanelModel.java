package pl.pkrysztofiak.dicomviewer.model.panels;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Point2D;

public abstract class PanelModel {

    public final ObservableList<PanelModel> adjacentPanels = FXCollections.observableArrayList();
    private final Observable<PanelModel> adjacentPanelAdded = JavaFxObservable.additionsOf(adjacentPanels);
    private final Observable<PanelModel> adjacentPanelRemoved = JavaFxObservable.removalsOf(adjacentPanels);
    
    public final ObservableList<PanelModel> topAdjacementPanels = FXCollections.observableArrayList();
    
    public final ObservableList<PanelModel> rightAdjacementPanels = FXCollections.observableArrayList();
    public final ObservableList<PanelModel> bottomAdjacementPanels = FXCollections.observableArrayList();
    public final ObservableList<PanelModel> leftAdjacementPanels = FXCollections.observableArrayList();
    
    protected final DoubleProperty minXProperty = new SimpleDoubleProperty(0.);
    public final Observable<Double> minXObservable = JavaFxObservable.valuesOf(minXProperty).map(Number::doubleValue);
    
    protected final DoubleProperty minYProperty = new SimpleDoubleProperty(0.);
    public final Observable<Double> minYObservable = JavaFxObservable.valuesOf(minYProperty).map(Number::doubleValue);
    
    protected final DoubleProperty maxXProperty = new SimpleDoubleProperty(1.);
    public final Observable<Double> maxXObservable = JavaFxObservable.valuesOf(maxXProperty).map(Number::doubleValue);
    
    protected final DoubleProperty maxYProperty = new SimpleDoubleProperty(1.);
    public final Observable<Double> maxYObservable = JavaFxObservable.valuesOf(maxYProperty).map(Number::doubleValue);

    private final ObjectProperty<PanelsModel> parentProperty = new SimpleObjectProperty<>();

    private final ObjectProperty<Point2D> pressedPointProperty = new SimpleObjectProperty<>();
    private final Observable<Point2D> pressedPointObservable = JavaFxObservable.valuesOf(pressedPointProperty);
    private final ObjectProperty<Point2D> draggedPointProperty = new SimpleObjectProperty<>();
    private final Observable<Point2D> draggedPoinObservable = JavaFxObservable.valuesOf(draggedPointProperty);
    
    private double width;
    private double startMaxX;
    
    public PanelModel() {
        pressedPointObservable.switchMap(pressed -> draggedPoinObservable.map(dragged -> dragged.getX() - pressed.getX())).subscribe(deltaX -> {
            double w = deltaX / width;
            System.out.println("w=" + w);
            
            double newMaxX = startMaxX + (startMaxX - minXProperty.get()) * w;
            System.out.println("newMax=" + newMaxX);
            maxXProperty.set(newMaxX);
        });
        
        adjacentPanelAdded.subscribe(this::onAdjacentAdded);
    }
    
    private void onAdjacentAdded(PanelModel adjacentPanelModel) {
        if (getMaxX() == adjacentPanelModel.getMinX()) {
            adjacentPanelModel.minXObservable.takeUntil(adjacentPanelRemoved.filter(adjacentPanelModel::equals)).subscribe(this::setMaxX);
        }
        if (getMinX() == adjacentPanelModel.getMaxX()) {
            adjacentPanelModel.maxXObservable.takeUntil(adjacentPanelRemoved.filter(adjacentPanelModel::equals)).subscribe(this::setMinX);
        }
        if (getMaxY() == adjacentPanelModel.getMinY()) {
            adjacentPanelModel.minYObservable.takeUntil(adjacentPanelRemoved.filter(adjacentPanelModel::equals)).subscribe(this::setMaxY);
        }
        if (getMinY() == adjacentPanelModel.getMaxY()) {
            adjacentPanelModel.maxYObservable.takeUntil(adjacentPanelRemoved.filter(adjacentPanelModel::equals)).subscribe(this::setMinY);
        }
    }
    
    public double getMinX() {
        return minXProperty.get();
    }
    
    public double getMinY() {
        return minYProperty.get();
    }
    
    public double getMaxX() {
        return maxXProperty.get();
    }
    
    public double getMaxY() {
        return maxYProperty.get();
    }

    public void setMinX(double value) {
        minXProperty.set(value);
    }
    
    public void setMinY(double value) {
        minYProperty.set(value);
    }
    
    public void setMaxX(double value) {
        maxXProperty.set(value);
    }
    
    public void setMaxY(double value) {
        maxYProperty.set(value);
    }
    
    public void addLeft(PanelModel newPanelModel) {
        adjacentPanels.clear();
        newPanelModel.setMinY(minYProperty.get());
        newPanelModel.setMaxY(maxYProperty.get());
        
        newPanelModel.setMinX(minXProperty.get());
        
        double halfWidth = (maxXProperty.get() - minXProperty.get()) / 2.;
        double x = minXProperty.get() + halfWidth;
        minXProperty.set(x);
        newPanelModel.setMaxX(x);
        
        parentProperty.get().add(newPanelModel);
    }
    
    public void addRight(PanelModel newPanelModel) {
        adjacentPanels.clear();
        newPanelModel.setMinY(minYProperty.get());
        newPanelModel.setMaxY(maxYProperty.get());
        
        newPanelModel.setMaxX(maxXProperty.get());
        
        double halfWidth = (maxXProperty.get() - minXProperty.get()) / 2.;
        double x = minXProperty.get() + halfWidth;
        newPanelModel.setMinX(x);
        maxXProperty.set(x);
        
        parentProperty.get().add(newPanelModel);
    }
    
    public void addTop(PanelModel newPanelModel) {
        adjacentPanels.clear();
        newPanelModel.setMinX(minXProperty.get());
        newPanelModel.setMaxX(maxXProperty.get());
        
        newPanelModel.setMinY(minYProperty.get());
        
        double halfHeight = (maxYProperty.get() - minYProperty.get()) / 2.;
        double y = minYProperty.get() + halfHeight;
        
        newPanelModel.setMaxY(y);
        minYProperty.set(y);
        
        parentProperty.get().add(newPanelModel);
    }
    
    public void addBottom(PanelModel newPanelModel) {
        adjacentPanels.clear();
        newPanelModel.setMinX(minXProperty.get());
        newPanelModel.setMaxX(maxXProperty.get());
        
        newPanelModel.setMaxY(maxYProperty.get());
        
        double halfHeight = (maxYProperty.get() - minYProperty.get()) / 2.;
        double y = minYProperty.get() + halfHeight;
        
        newPanelModel.setMinY(y);
        maxYProperty.set(y);
        
        parentProperty.get().add(newPanelModel);
    }
    
    public void setWidth(double widht) {
        this.width = widht;
    }
    
    public void setPressedPoint(Point2D point) {
        startMaxX = maxXProperty.get();
        pressedPointProperty.set(point);
    }
    
    public void setDraggedPoint(Point2D point) {
        draggedPointProperty.set(point);
    }
    
    public void setParent(PanelsModel panelsModel) {
        parentProperty.set(panelsModel);
    }

    @Override
    public String toString() {
        return "minX=" + minXProperty.get() + ", minY=" + minYProperty.get() + ", maxX=" + maxXProperty.get() + ", maxY=" + maxYProperty.get();
    }
    
    
}