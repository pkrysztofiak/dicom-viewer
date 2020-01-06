package pl.pkrysztofiak.dicomviewer.model.panels;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;

public abstract class PanelModel {

    protected final DoubleProperty minXProperty = new SimpleDoubleProperty(0.);
    public final Observable<Double> minXObservable = JavaFxObservable.valuesOf(minXProperty).map(Number::doubleValue);
    
    protected final DoubleProperty minYProperty = new SimpleDoubleProperty(0.);
    public final Observable<Double> minYObservable = JavaFxObservable.valuesOf(minYProperty).map(Number::doubleValue);
    
    protected final DoubleProperty maxXProperty = new SimpleDoubleProperty(1.);
    public final Observable<Double> maxXObservable = JavaFxObservable.valuesOf(maxXProperty).map(Number::doubleValue);
    
    protected final DoubleProperty maxYProperty = new SimpleDoubleProperty(1.);
    public final Observable<Double> maxYObservable = JavaFxObservable.valuesOf(maxYProperty).map(Number::doubleValue);

    private final ObjectProperty<PanelsModel> parentProperty = new SimpleObjectProperty<>();
    
    public PanelModel() {
        
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
    
    public void addLeft(PanelModel newPanelModel) {
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
        newPanelModel.setMinX(minXProperty.get());
        newPanelModel.setMaxX(maxXProperty.get());
        
        newPanelModel.setMaxY(maxYProperty.get());
        
        double halfHeight = (maxYProperty.get() - minYProperty.get()) / 2.;
        double y = minYProperty.get() + halfHeight;
        
        newPanelModel.setMinY(y);
        maxYProperty.set(y);
        
        parentProperty.get().add(newPanelModel);
    }
    
    public void setParent(PanelsModel panelsModel) {
        parentProperty.set(panelsModel);
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

    @Override
    public String toString() {
        return "minX=" + minXProperty.get() + ", minY=" + minYProperty.get() + ", maxX=" + maxXProperty.get() + ", maxY=" + maxYProperty.get();
    }
    
    
}