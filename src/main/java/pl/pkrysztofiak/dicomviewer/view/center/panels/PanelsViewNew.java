package pl.pkrysztofiak.dicomviewer.view.center.panels;

import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.layout.Pane;
import pl.pkrysztofiak.dicomviewer.model.panels2.Panels;
import pl.pkrysztofiak.dicomviewer.model.panels2.VerticalBorder;

public class PanelsViewNew extends Pane {

    private final ObservableList<VerticalBorderView> verticalBordersView = FXCollections.observableArrayList();
    
    public PanelsViewNew(Panels panels) {
        Bindings.bindContent(getChildren(), verticalBordersView);

        panels.verticalBorders.borders.forEach(this::onVerticalBorderAdded);
    }
    
    public void onVerticalBorderAdded(VerticalBorder verticalBorder) {
        verticalBordersView.add(new VerticalBorderView(verticalBorder));
    }
}