package pl.pkrysztofiak.dicomviewer.view.center.panels.panel;

import java.net.URL;
import java.util.ResourceBundle;

import io.reactivex.Observable;
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

        topPaneClickedObservable.subscribe(this::onTopPaneClicked);
        rightPaneClickedObservable.subscribe(this::onRightPaneClicked);
        bottomPaneClickedObservable.subscribe(this::onBottomPaneClicked);
        leftPaneClickedObservable.subscribe(this::onLeftPaneClicked);
        
        rightBorderPaneMousePressedObservable
        .map(MouseEvent::getSceneX).switchMap(pressedX -> 
            Observable.combineLatest(rightBorderPaneMouseDraggedObservable.map(MouseEvent::getSceneX), Observable.just(widthProperty().get()), Observable.just(panelModel.getMaxX()), (draggedX, width, startMaxX) -> {
                double delta = (draggedX - pressedX) / width;
                return startMaxX + (startMaxX - panelModel.getMinX()) * delta;
            }))
        .subscribe(this::onRightBorderDragged);
        
        
    }
    
    private void onRightBorderDragged(double maxX) {
        panelModel.setMaxX(maxX);
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
}