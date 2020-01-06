package pl.pkrysztofiak.dicomviewer.view.center.panels.panel;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.geometry.Point2D;
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

        rightBorderPaneMousePressedObservable.subscribe(this::onRightBorderPaneMousePressed);
        rightBorderPaneMouseDraggedObservable.subscribe(this::onRightBorderPaneMouseDragged);
        
        topPaneClickedObservable.subscribe(this::onTopPaneClicked);
        rightPaneClickedObservable.subscribe(this::onRightPaneClicked);
        bottomPaneClickedObservable.subscribe(this::onBottomPaneClicked);
        leftPaneClickedObservable.subscribe(this::onLeftPaneClicked);
    }
    
    private void onTopPaneClicked(MouseEvent event) {
        panelModel.addTop(new ImagePanel());
    }
     
    private void onRightPaneClicked(MouseEvent event) {
        panelModel.addRight(new ImagePanel());
    }
    
    private void onBottomPaneClicked(MouseEvent event) {
        panelModel.addBottom(new ImagePanel());
    }
    
    private void onLeftPaneClicked(MouseEvent event) {
        panelModel.addLeft(new ImagePanel());
    }
    
    private void onRightBorderPaneMousePressed(MouseEvent event) {
        System.out.println("right border mouse pressed");
        panelModel.setWidth(getWidth());
        panelModel.setPressedPoint(new Point2D(event.getScreenX(), event.getScreenY()));
    }
    
    private void onRightBorderPaneMouseDragged(MouseEvent event) {
        System.out.println("rightBorder MouseDragged");
        panelModel.setDraggedPoint(new Point2D(event.getScreenX(), event.getScreenY()));
    }
}