package pl.pkrysztofiak.dicomviewer.view.center.panels;

import javafx.scene.Group;
import pl.pkrysztofiak.dicomviewer.model.panels2.VerticalBorder;

public class VerticalBorderView extends Group {

    public VerticalBorderView(VerticalBorder verticalBorder) {
        verticalBorder.panelAddedObservable.subscribe(panel -> {
//            Line line = new Line(verticalBorder.getX(), panel.getMinY(), verticalBorder.getX(), endY)
        });
    }
}