import com.sun.javafx.css.StyleCache;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;

public class main extends Application {

    int height = 1080;
    int width = 1920;

    static GridPane pane;
    static Scene scene;

    Controller controller;

    public static void main(String[] args) {
        //test commit
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        pane = new GridPane();
        scene = new Scene(pane, width, height);
        EventHandler eventHandlerMouse = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                menu(stage);
                scene.removeEventHandler(MouseEvent.MOUSE_CLICKED,this);
            }
        };

        EventHandler eventHandlerKey = new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent keyEvent) {
                menu(stage);
                scene.removeEventHandler(KeyEvent.KEY_PRESSED,this);
            }
        };
        scene.addEventHandler(KeyEvent.KEY_PRESSED,eventHandlerKey);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED,eventHandlerMouse);

        VBox box = new VBox();
        ImageView imageView = new ImageView();

        //stage.setFullScreen(true);
        box.getChildren().add(imageView);
        imageView.setImage(new Image(this.getClass().getResource("backgrounds/titlescreen.gif").toExternalForm()));
//        pane.add(box, 0, 0);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        try {
            BackgroundSize size = new BackgroundSize(-1d, -1d, true, true, true, true);
            Background back = new Background(new BackgroundImage(new Image(String.valueOf(Path.of("./res/backgrounds/titlescreen.gif").toUri().toURL())), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));
            pane.setBackground(back);
        } catch (MalformedURLException ignored) {
            System.out.println("not working");
        }

        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.show();

        System.out.println();
    }

    private void menu(Stage stage) {


        pane.setMaxWidth(scene.getWidth());
        pane.setMinWidth(scene.getWidth());
        pane.getChildren().clear();
        //picture button new game

        ColumnConstraints[] c = new ColumnConstraints[]{
                new ColumnConstraints(),
                new ColumnConstraints(),
                new ColumnConstraints(),
                new ColumnConstraints(),
                new ColumnConstraints()
        };
        c[0].setPercentWidth(10);
        c[1].setPercentWidth(10);
        c[2].setPercentWidth(20);
        c[3].setPercentWidth(60);
        c[4].setPercentWidth(40);

        RowConstraints r = new RowConstraints();
        r.setPercentHeight(30);
        pane.getRowConstraints().clear();
        pane.getRowConstraints().addAll(r);
        pane.getColumnConstraints().clear();
        pane.getColumnConstraints().addAll(Arrays.asList(c));
        ImageView buttonNewGame = new ImageView("button_newGame_v5.png");
        pane.add(buttonNewGame, 3, 1);
        GridPane.setHalignment(buttonNewGame, HPos.CENTER);

        //buttonNewGame.setPickOnBounds(true); // allows click on transparent areas
        buttonNewGame.setOnMouseClicked((MouseEvent e) -> {
            startGame(); // change functionality
            System.out.println("test_button unga bunga");
        });

        //picture button quit
        ImageView buttonQuit = new ImageView("button_newGame_v5.png");
        GridPane.setHalignment(buttonQuit, HPos.CENTER);

        pane.add(buttonQuit, 3, 2);
        //buttonQuit.setPickOnBounds(true); // allows click on transparent areas
        buttonQuit.setOnMouseClicked((MouseEvent e) -> {
            System.exit(0); // change functionality
        });


        try {
            System.out.println(Path.of("./res/backgrounds/static_bg_v2.png").toUri().toURL());
            BackgroundSize size = new BackgroundSize(-1d, -1d, true, true, true, true);
            Background back = new Background(new BackgroundImage(new Image(String.valueOf(Path.of("./res/backgrounds/static_bg_v2.png").toUri().toURL())), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));
            pane.setBackground(back);
        } catch (MalformedURLException ignored) {
            System.out.println("not working");
        }
//
    }

//    public void menu(Stage primaryStage) {
//
//
//        //main menu bg
//        ImageView imageView = new ImageView();
//        pane.add(imageView,0,0);
//        imageView.setImage(new Image("mainmenu_v3.png"));
//        imageView.setFitWidth(width);
//        imageView.setFitHeight(height);
//
//        VBox box = new VBox();
//        box.setAlignment(Pos.CENTER);
//
//        GridPane.setValignment(box, VPos.CENTER);
//        /*
//        Button newGame = new Button("new Game");
//        newGame.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                startGame();
//            }
//        });
//        */
//
//        /*
//        Button quit = new Button("Quit");
//        quit.setOnAction(new EventHandler<ActionEvent>() {
//            @Override
//            public void handle(ActionEvent event) {
//                System.exit(0);
//            }
//        });
//
//        */
//        pane = new GridPane();
//
//        //box.getChildren().add(newGame);
//        //box.getChildren().add(quit);
//
//
//        pane.add(box, 0, 0);
//        scene = new Scene(pane, width, height);
//
//
//        System.out.println("a");
////        primaryStage.setFullScreen(false);
//        primaryStage.setScene(scene);
//        primaryStage.setFullScreen(true);
//
//        try {
//            System.out.println(Path.of("./res/backgrounds/static_bg_v1.png").toUri().toURL());
//            BackgroundSize size = new BackgroundSize(-1d, -1d, true, true, true, true);
//            Background back = new Background(new BackgroundImage(new Image(String.valueOf(Path.of("mainmenu_v3.png").toUri().toURL())), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT));
//            pane.setBackground(back);
//        } catch (MalformedURLException ignored) {
//            System.out.println("not working");
//        }
//
//
//
//
//        //picture button new game
//        ImageView buttonNewGame = new ImageView("button_newGame_v4.png");
//        box.getChildren().add(buttonNewGame);
//        //buttonNewGame.setPickOnBounds(true); // allows click on transparent areas
//        buttonNewGame.setOnMouseClicked((MouseEvent e) -> {
//            startGame(); // change functionality
//            System.out.println("test_button unga bunga");
//        });
//
//
//        //picture button quit
//        ImageView buttonQuit = new ImageView("button_quit_v1.png");
//        box.getChildren().add(buttonQuit);
//        //buttonQuit.setPickOnBounds(true); // allows click on transparent areas
//        buttonQuit.setOnMouseClicked((MouseEvent e) -> {
//            System.exit(0); // change functionality
//        });
//
//
//
//    }

    public void startGame() {
        controller = new Controller(1, 0);
        System.out.println("startgame");
    }

    public static void newStage(int stage, ArrayList<Bullet> bullets,ArrayList<EfectCard> efectCards, int health, ArrayList<Enemy> enemies){
        System.out.println("started");
        pane.setMaxWidth(scene.getWidth());
        pane.setMinWidth(scene.getWidth());
        pane.getChildren().clear();
        pane.setBackground(null);
        Text text = new Text(health+"");
        pane.add(text,0,0);
        for (int i = 0; i < 3; i++) {
            
        }

        ArrayList<StackPane> stackPanesEfectCards= new ArrayList<>();
        for (int i = 0; i < 5 && i<efectCards.size(); i++) {
            Text efectCard = new Text(efectCards.get(i).getCardNameAsString());
            efectCard.setFill(Color.BLACK);
            Rectangle rect = new Rectangle();
            rect.setHeight(150);
            rect.setWidth(150);
            rect.setFill(Color.GREY);
            StackPane stack = new StackPane();
            stack.setId("efectCard"+i);
            GridPane.setMargin(stack,new Insets(10,10,10,10));
            stack.getChildren().addAll(rect,efectCard);
            stackPanesEfectCards.add(stack);
        }



        pane.add(new Text("Stage"+ stage),5,0);

        ArrayList<ProgressBar> progressBars = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            progressBars.add(new ProgressBar());
            progressBars.get(i).setProgress(1);
            progressBars.get(i).setId("Bar_"+i);
            pane.add(progressBars.get(i),i+2,2);
        }


    }

    public void setBulletInSlot(int slot, Bullet bulletCard){
        Text bullet = new Text(bulletCard.getCardNameAsString());
        bullet.setFill(Color.BLACK);
        Rectangle rect = new Rectangle();
        rect.setHeight(150);
        rect.setWidth(150);
        rect.setFill(Color.GREY);
        StackPane stack = new StackPane();
        stack.setId("Bullet"+slot);
        GridPane.setMargin(stack,new Insets(10,10,10,10));
        stack.getChildren().addAll(rect,bullet);
        switch (slot){
            case 0:
                pane.add(stack,3,4);
                break; 
            case 1:
                pane.add(stack,2,5);
                break;
            case 2:
                pane.add(stack,2,6);
                break;
            case 3:
                pane.add(stack,4,6);
                break;
            case 4:
                pane.add(stack,4,5);
                break;
        }
    }
}
