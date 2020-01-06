package pl.pkrysztofiak.dicomviewer;

import io.reactivex.rxjavafx.observables.JavaFxObservable;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class AnchorMoveApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }
    
    @Override
    public void start(Stage stage) throws Exception {
        
        Panel panel = new Panel();
        
        AnchorPane anchorPane = new AnchorPane(panel);
        
        AnchorPane.setTopAnchor(panel, 100.);
        AnchorPane.setLeftAnchor(panel, 100.);
        AnchorPane.setRightAnchor(panel, 200.);
        AnchorPane.setBottomAnchor(panel, 200.);
        
        Scene scene = new Scene(anchorPane, 400., 400.);
        stage.setScene(scene);
        stage.show();
    }
    
    class Panel extends StackPane {
        
        private double pressedX;
        private double rightAnchor;

        {
            setStyle("-fx-background-color: yellow");
        }
        
        public Panel() {
            
            JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_PRESSED).subscribe(event -> {
                pressedX = event.getScreenX();
                rightAnchor = AnchorPane.getRightAnchor(this);
            });
            JavaFxObservable.eventsOf(this, MouseEvent.MOUSE_DRAGGED).subscribe(event -> {
                double screenX = event.getScreenX();
                double deltaX = screenX - pressedX;
                AnchorPane.setRightAnchor(this, rightAnchor - deltaX);
            });
            
        }
    }
}
