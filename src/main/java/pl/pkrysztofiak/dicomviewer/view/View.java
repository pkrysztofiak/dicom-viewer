package pl.pkrysztofiak.dicomviewer.view;

import javafx.scene.layout.BorderPane;
import pl.pkrysztofiak.dicomviewer.model.Model;
import pl.pkrysztofiak.dicomviewer.view.center.CenterView;

public class View extends BorderPane {

    private final CenterView centerView;
    
    public View(Model model) {
        centerView = new CenterView(model);
        
        setCenter(centerView);
    }
}
