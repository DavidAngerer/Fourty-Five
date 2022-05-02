import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class main extends Application {

    int height = 1080;
    int width = 1920;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        GridPane pane = new GridPane();
        Scene scene = new Scene(pane,width,height);

        VBox box = new VBox();
        ImageView imageView = new ImageView();

        primaryStage.setFullScreen(true);
        box.getChildren().add(imageView);
        imageView.setImage(new Image(this.getClass().getResource("titlescreen.gif").toExternalForm()));
        pane.add(box,0,0);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);
        primaryStage.setScene(scene);
        primaryStage.show();
        System.out.println();
    }
}
