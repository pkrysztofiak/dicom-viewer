package pl.pkrysztofiak.dicomviewer.view.center.panels;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import pl.pkrysztofiak.dicomviewer.model.panels.PanelModel;
import pl.pkrysztofiak.dicomviewer.model.panels.PanelsModel;

public class PanelsView extends AnchorPane {

    private final ObservableList<PanelView> panelViews = FXCollections.observableArrayList();
    private final Observable<Double> widthObservable = JavaFxObservable.valuesOf(widthProperty()).map(Number::doubleValue);
    private final Observable<Double> heightObservable = JavaFxObservable.valuesOf(heightProperty()).map(Number::doubleValue);
    
    public PanelsView(PanelsModel panelsModel) {
        Bindings.bindContent(getChildren(), panelViews);
        
        panelsModel.getPanels().forEach(this::onAdd);
        panelsModel.panelAddedObservable().subscribe(this::onAdd);
    }
    
    private void onAdd(PanelModel panelModel) {
        PanelView panelView = new PanelView(panelModel);
        panelViews.add(panelView);

        AnchorPane.setLeftAnchor(panelView, panelModel.getMinX());
        AnchorPane.setTopAnchor(panelView, panelModel.getMinY());
        
        widthObservable.subscribe(width -> {
            AnchorPane.setRightAnchor(panelView, width - (width * panelModel.getMaxX()));
        });
        
        heightObservable.subscribe(height -> {
            AnchorPane.setBottomAnchor(panelView, height - (height * panelModel.getMaxY()));
        });
    }
}