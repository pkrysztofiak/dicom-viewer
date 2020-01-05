package pl.pkrysztofiak.dicomviewer.model.panels;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;

public abstract class PanelModel {

    protected final DoubleProperty minXProperty = new SimpleDoubleProperty(0.);
    protected final DoubleProperty minYProperty = new SimpleDoubleProperty(0.);
    protected final DoubleProperty maxXProperty = new SimpleDoubleProperty(1.);
    protected final DoubleProperty maxYProperty = new SimpleDoubleProperty(1.);

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
}