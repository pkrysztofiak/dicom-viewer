package pl.pkrysztofiak.dicomviewer.view.center.panels;

import javafx.scene.layout.StackPane;
import pl.pkrysztofiak.dicomviewer.model.panels.PanelModel;

public class PanelView extends StackPane {

    {
        setStyle("-fx-background-color: red, blue; -fx-background-insets: 0, 1;");
    }
    
    public PanelView(PanelModel panelModel) {
        
    }
}