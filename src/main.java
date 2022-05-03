import com.sun.media.jfxmedia.Media;
import com.sun.media.jfxmedia.MediaPlayer;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.util.ArrayList;

public class main extends Application {

    int height = 1080;
    int width = 1920;

    GridPane pane;
    Scene scene;



    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        pane = new GridPane();
        scene = new Scene(pane,width,height);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            menu(primaryStage);
        });
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, (key) -> {
            menu(primaryStage);
        });

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

    public void menu(Stage primaryStage){


        VBox box = new VBox();
        box.setAlignment(Pos.CENTER);
        Button newGame = new Button("new Game");
        newGame.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                startGame();
            }
        });

        Button quit = new Button("Quit");
        quit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.exit(0);
            }
        });
        pane= new GridPane();

        box.getChildren().add(newGame);
        box.getChildren().add(quit);


        pane.add(box,0,0);
        scene = new Scene(pane,width,height);

        System.out.println("a");
        primaryStage.setFullScreen(false);
        primaryStage.setScene(scene);
        primaryStage.setFullScreen(true);
    }

    public void startGame(){

    }
}
