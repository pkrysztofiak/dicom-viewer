package pl.pkrysztofiak.dicomviewer.view.center.panels.panel;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import io.reactivex.Observable;
import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

public class PanelViewFxml extends GridPane implements Initializable {
    
    @FXML
    protected StackPane topLeftBorderPane;
    
    @FXML
    protected StackPane topBorderPane;
    
    @FXML
    protected StackPane topRightBorderPane;
    
    @FXML
    protected StackPane rightBorderPane;
    protected Observable<MouseEvent> rightBorderPaneMousePressedObservable;
    protected Observable<MouseEvent> rightBorderPaneMouseDraggedObservable;
    
    @FXML
    protected StackPane bottomRightBorderPane;
    
    @FXML
    protected StackPane bottomBorderPane;
    
    @FXML
    protected StackPane bottomLeftBorderPane;
    
    @FXML
    protected StackPane leftBorderPane;
    
    @FXML
    protected StackPane topPane;
    protected Observable<MouseEvent> topPaneClickedObservable;
    
    @FXML
    protected StackPane rightPane;
    protected Observable<MouseEvent> rightPaneClickedObservable;
    
    @FXML
    protected StackPane bottomPane;
    protected Observable<MouseEvent> bottomPaneClickedObservable;
    
    @FXML
    protected StackPane leftPane;
    protected Observable<MouseEvent> leftPaneClickedObservable;
    
    @FXML
    protected StackPane centerPane;
    
    protected final ObjectProperty<Double> pressedXProperty = new SimpleObjectProperty<>();
    protected final Observable<Double> pressedXObservable = JavaFxObservable.valuesOf(pressedXProperty);
    protected final ObjectProperty<Double> pressedYProperty = new SimpleObjectProperty<>();
    protected final Observable<Double> pressedYObservable = JavaFxObservable.valuesOf(pressedYProperty);

    protected final ObjectProperty<Double> draggedXProperty = new SimpleObjectProperty<>();
    protected final Observable<Double> draggedXObservable = JavaFxObservable.valuesOf(draggedXProperty);
    protected final ObjectProperty<Double> draggedYProperty = new SimpleObjectProperty<>();
    protected final Observable<Double> draggedYObservable = JavaFxObservable.valuesOf(draggedYProperty);
    
    public PanelViewFxml() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/fxml/PanelView.fxml"));
        try {
            fxmlLoader.setController(this);
            fxmlLoader.setRoot(this);
            fxmlLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("PanelViewFxml.initialize()");
        rightBorderPaneMousePressedObservable = JavaFxObservable.eventsOf(rightBorderPane, MouseEvent.MOUSE_PRESSED);
        rightBorderPaneMouseDraggedObservable = JavaFxObservable.eventsOf(rightBorderPane, MouseEvent.MOUSE_DRAGGED);
        
        topPaneClickedObservable = JavaFxObservable.eventsOf(topPane, MouseEvent.MOUSE_CLICKED);
        rightPaneClickedObservable = JavaFxObservable.eventsOf(rightPane, MouseEvent.MOUSE_CLICKED);
        bottomPaneClickedObservable = JavaFxObservable.eventsOf(bottomPane, MouseEvent.MOUSE_CLICKED);
        leftPaneClickedObservable = JavaFxObservable.eventsOf(leftPane, MouseEvent.MOUSE_CLICKED);
        
        JavaFxObservable.valuesOf(topPane.hoverProperty()).subscribe(hover -> onHover(hover, topPane));
        JavaFxObservable.valuesOf(rightPane.hoverProperty()).subscribe(hover -> onHover(hover, rightPane));
        JavaFxObservable.valuesOf(bottomPane.hoverProperty()).subscribe(hover -> onHover(hover, bottomPane));
        JavaFxObservable.valuesOf(leftPane.hoverProperty()).subscribe(hover -> onHover(hover, leftPane));
        JavaFxObservable.valuesOf(centerPane.hoverProperty()).subscribe(hover -> onHover(hover, centerPane));
    }
    
    private void onHover(boolean hover, StackPane stackPane) {
        if (hover) {
            stackPane.toFront();
            stackPane.setStyle("-fx-background-color: blue;");
        } else {
            stackPane.setStyle("");
        }
    }
}