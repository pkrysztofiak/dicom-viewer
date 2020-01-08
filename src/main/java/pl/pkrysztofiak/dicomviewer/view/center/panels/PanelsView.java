package pl.pkrysztofiak.dicomviewer.view.center.panels;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.AnchorPane;
import pl.pkrysztofiak.dicomviewer.model.panels.PanelModel;
import pl.pkrysztofiak.dicomviewer.model.panels.PanelsModel;
import pl.pkrysztofiak.dicomviewer.view.center.panels.panel.PanelView;

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

        Observable.combineLatest(panelModel.minXObservable, widthObservable, (minX, width) -> width * minX).subscribe(leftAnchorValue -> {
            AnchorPane.setLeftAnchor(panelView, leftAnchorValue);
        });
        
        Observable.combineLatest(panelModel.minYObservable, heightObservable, (minY, height) -> height * minY).subscribe(topAnchorValue -> {
            AnchorPane.setTopAnchor(panelView, topAnchorValue);
        });

        Observable.combineLatest(panelModel.maxXObservable, widthObservable, (maxX, width) -> width - (width * maxX)).subscribe(rightAnchorValue -> {
            AnchorPane.setRightAnchor(panelView, rightAnchorValue);
        });

        Observable.combineLatest(panelModel.maxYObservable, heightObservable, (maxY, height) -> height - (height * maxY)).subscribe(bottomAnchorValue -> {
            AnchorPane.setBottomAnchor(panelView, bottomAnchorValue);
        });
    }
}