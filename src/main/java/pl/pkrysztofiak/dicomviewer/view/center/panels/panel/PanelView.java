package pl.pkrysztofiak.dicomviewer.view.center.panels.panel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.scene.input.MouseEvent;
import pl.pkrysztofiak.dicomviewer.model.panels.PanelModel;
import pl.pkrysztofiak.dicomviewer.model.panels.image.ImagePanel;

public class PanelView extends PanelViewFxml {

    private final PanelModel panelModel;
    
    public PanelView(PanelModel panelModel) {
        super();
        this.panelModel = panelModel;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("PanelView.initialize()");
        super.initialize(location, resources);

        rightPaneClickedObservable.subscribe(this::onRightPaneClicked);
    }
    
    private void onRightPaneClicked(MouseEvent event) {
        panelModel.addToRight(new ImagePanel());
    }
}