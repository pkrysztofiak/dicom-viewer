package pl.pkrysztofiak.dicomviewer.view.center;

import javafx.scene.layout.StackPane;
import pl.pkrysztofiak.dicomviewer.model.Model;
import pl.pkrysztofiak.dicomviewer.view.center.panels.PanelsView;

public class CenterView extends StackPane {

    public CenterView(Model model) {

        model.panelsObservable().subscribe(panelsModel -> {
            System.out.println("panelsModel");
            getChildren().add(new PanelsView(panelsModel));
        });
    }
}