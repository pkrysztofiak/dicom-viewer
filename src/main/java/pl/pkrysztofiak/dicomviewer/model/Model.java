package pl.pkrysztofiak.dicomviewer.model;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import pl.pkrysztofiak.dicomviewer.model.panels.PanelsModel;
import pl.pkrysztofiak.dicomviewer.model.panels.image.ImagePanel;

public class Model {

    private final ObjectProperty<PanelsModel> panelsProperty = new SimpleObjectProperty<>();
    private final Observable<PanelsModel> panelsObservable = JavaFxObservable.valuesOf(panelsProperty);
    
    public Observable<PanelsModel> panelsObservable() {
        return panelsObservable;
    }
    
    public void init() {
        PanelsModel panelsModel = new PanelsModel();
        ImagePanel imagePanel = new ImagePanel();
        panelsModel.add(imagePanel);
        panelsProperty.set(panelsModel);
    }
}