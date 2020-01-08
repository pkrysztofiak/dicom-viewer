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
        
        bottomBorderPaneMousePressedObservable
        .map(MouseEvent::getSceneY).switchMap(pressedY -> 
            Observable.combineLatest(bottomBorderPaneMouseDraggedObservable.map(MouseEvent::getSceneY), Observable.just(heightProperty().get()), Observable.just(panelModel.getMaxY()), (draggedY, height, startMaxY) -> {
                double delta = (draggedY - pressedY) / height;
                return startMaxY + (startMaxY - panelModel.getMinY()) * delta;
            }))
        .subscribe(this::onBottomBorderDragged);
        
        leftBorderPaneMousePressedObservable
        .map(MouseEvent::getSceneX).switchMap(pressedX -> 
        Observable.combineLatest(leftBorderPaneMouseDraggedObservable.map(MouseEvent::getSceneX), Observable.just(widthProperty().get()), Observable.just(panelModel.getMinX()), (draggedX, width, startMinX) -> {
            double delta = (draggedX - pressedX) / width;
            return startMinX + (panelModel.getMaxX() - startMinX) * delta;
        }))
        .subscribe(this::onLeftBorderDragged);
        
        topBorderPaneMousePressedObservable
        .map(MouseEvent::getSceneY).switchMap(pressedY -> 
        Observable.combineLatest(topBorderPaneMouseDraggedObservable.map(MouseEvent::getSceneY), Observable.just(heightProperty().get()), Observable.just(panelModel.getMaxY()), (draggedY, width, startMaxY) -> {
            double delta = (draggedY - pressedY) / width;
            return startMaxY + (panelModel.getMaxY() - startMaxY) * delta;
        }))
        .subscribe(this::onTopBorderDragged);
    }
    
    private void onTopBorderDragged(double y) {
        panelModel.setMinY(y);
    }
    
    private void onBottomBorderDragged(double y) {
        panelModel.setMaxY(y);
    }
    
    private void onRightBorderDragged(double x) {
        panelModel.setMaxX(x);
    }
    
    private void onLeftBorderDragged(double x) {
        panelModel.setMinX(x);
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