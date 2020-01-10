package pl.pkrysztofiak.dicomviewer.model.panels2;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import io.reactivex.rxjavafx.sources.Change;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class Panel {

    
    
    private final ObjectProperty<Double> minXProperty = new SimpleObjectProperty<>(0.);
    public final Observable<Change<Double>> minXChangeObservable = JavaFxObservable.changesOf(minXProperty);
    public final Observable<Double> minXObservable = JavaFxObservable.valuesOf(minXProperty);
    
    private final ObjectProperty<Double> minYProperty = new SimpleObjectProperty<>(0.);
    public final Observable<Change<Double>> minYChangeObservable = JavaFxObservable.changesOf(minYProperty);
    public final Observable<Double> minYObservable = JavaFxObservable.valuesOf(minYProperty);
    
    private final ObjectProperty<Double> maxXProperty = new SimpleObjectProperty<>(0.);
    public final Observable<Change<Double>> maxXChangeObservable = JavaFxObservable.changesOf(maxXProperty);
    public final Observable<Double> maxXObservable = JavaFxObservable.valuesOf(maxXProperty);
    
    private final ObjectProperty<Double> maxYProperty = new SimpleObjectProperty<>(0.);
    public final Observable<Change<Double>> maxYChangeObservable = JavaFxObservable.changesOf(maxYProperty);
    public final Observable<Double> maxYObservable = JavaFxObservable.valuesOf(maxYProperty);

    public final ObjectProperty<Double> widthProperty = new SimpleObjectProperty<>();
    public final ObjectProperty<Double> heightProperty = new SimpleObjectProperty<>();

    private Border topBorder = new Border();
    private Border rightBorder = new Border();
    private Border bottomBorder = new Border();
    private Border leftBorder = new Border();
    
    public Panel(double minX, double minY, double maxX, double maxY) {
        topBorder.panels.add(this);
        rightBorder.panels.add(this);
        bottomBorder.panels.add(this);
        leftBorder.panels.add(this);

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

    public Border getTopBorder() {
        return topBorder;
    }

    public void setTopBorder(Border topBorder) {
        this.topBorder = topBorder;
    }

    public Border getRightBorder() {
        return rightBorder;
    }

    public void setRightBorder(Border rightBorder) {
        this.rightBorder = rightBorder;
    }

    public Border getBottomBorder() {
        return bottomBorder;
    }

    public void setBottomBorder(Border bottomBorder) {
        this.bottomBorder = bottomBorder;
    }

    public Border getLeftBorder() {
        return leftBorder;
    }

    public void setLeftBorder(Border leftBorder) {
        this.leftBorder = leftBorder;
    }
}