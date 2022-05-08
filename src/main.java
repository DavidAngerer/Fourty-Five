import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;

//TODO rotatet ids to right slot after getting it to chamber
/**
 * @author David Angelo, Philip Jankovic
 * main class for the .Fourty-Five game, handles graphics (javafx)
 */
public class main extends Application {

    //TODO zielmodus ( welcher Gegner, Kopf oder Body)
    /**
     * Resolution Height
     */
    int height = 1080;

    /**
     * Resolution width
     */
    int width = 1920;

    /**
     * The Pane to write on
     */
    static GridPane pane;

    /**
     * Scene
     */
    static Scene scene;

    /**
     * Controller class which handles Logic
     */
    static Controller controller;

    static int handcardsTaken;

    static boolean shootingMode = false;

    public static void main(String[] args) {
        //test commit
        launch(args);
    }

    /**
     * Programm started
     * @param stage primary Stage
     */
    @Override
    public void start(Stage stage) {
        pane = new GridPane();
        scene = new Scene(pane, width, height);
        EventHandler eventHandlerMouse = new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent mouseEvent) {
                menu();
                scene.removeEventHandler(MouseEvent.MOUSE_CLICKED,this);
            }
        };

        EventHandler eventHandlerKey = new EventHandler<KeyEvent>(){

            @Override
            public void handle(KeyEvent keyEvent) {
                menu();
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

    /**
     * Called once to get to Menu with start button and quit button
     */
    private void menu() {


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
            startGame();
            controller.nextStage();// change functionality
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
    }

    /**
     * Called once to start the game
     */
    public void startGame() {
        controller = new Controller(1, 0);
        System.out.println("startgame");
    }

    /**
     * Enters a new Stage with new Enemies
     * @param stage Number of Stage
     * @param health Health of Player
     * @param enemies Number of Enemies
     */
    public static void newStage(int stage, int health, ArrayList<Enemy> enemies){
        handcardsTaken = 0;
        pane.setGridLinesVisible(true);
        pane.getColumnConstraints().clear();
        pane.getRowConstraints().clear();
        ArrayList<Bullet> bullets = new ArrayList<>();
        ArrayList<EfectCard> efectCards = new ArrayList<>();
        System.out.println("started");
        pane.setMaxWidth(scene.getWidth());
        pane.setMinWidth(scene.getWidth());
        pane.getChildren().clear();
        pane.setBackground(null);
        Text text = new Text(health+"");
        pane.add(text,0,0);
        bullets.addAll(controller.getRndBullets(3));
        efectCards.addAll(controller.getRndEffectCards(3));
        for (int i = 0; i < 3; i++) {
            addCardInHand(bullets.get(i));
            addCardInHand(efectCards.get(i));
        }

        Button shoot = new Button("shoot");
        shoot.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                shootingMode = true;
                for (int i = 0; i < enemies.size(); i++) {
                    StackPane enemy = (StackPane) getNodeByNameId("Enemy_"+i);
                    Rectangle body = new Rectangle(80,300);
                    body.setFill(Color.BLUE);
                    final int ene = i;
                    body.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            controller.shoot(ene,true);
                        }
                    });
                    Rectangle head = new Rectangle(80,80);
                    head.setFill(Color.GREEN);
                    head.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            controller.shoot(ene,false);
                        }
                    });
                    enemy.setAlignment(head, Pos.TOP_CENTER);
                    enemy.getChildren().addAll(body,head);
                }
                //controller.shoot(0,true);
            }
        });
        pane.add(shoot,5,0);
        pane.add(new Text("Stage"+ stage),4,0);

        createEnemies(enemies);
    }

    /**
     * Creates the Visuals and healthbars for enemies
     * @param enemies the enemies to be placed
     */
    private static void createEnemies(ArrayList<Enemy> enemies) {
        ArrayList<StackPane> rects = new ArrayList<>();
        ArrayList<ProgressBar> progressBars = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            rects.add(new StackPane());
            Rectangle enemy= new Rectangle(80,300);
            enemy.setFill(Color.RED);
            rects.get(i).getChildren().add(enemy);
            rects.get(i).setId("Enemy_"+i);
            pane.add(rects.get(i),i+2,1);
            progressBars.add(new ProgressBar());
            progressBars.get(i).setProgress(1);
            progressBars.get(i).setId("Bar_"+i);
            pane.add(progressBars.get(i),i+2,2);
        }
    }


    /**
     * Adds a card to Players Hand
     * @param card Bullet or EfectCard
     */
    public static void addCardInHand(Card card){
        Text efectCard = new Text(card.getCardNameAsString());
        efectCard.setFill(Color.BLACK);
        Rectangle rect = new Rectangle();
        rect.setHeight(150);
        rect.setWidth(150);
        rect.setFill(Color.GREY);
        StackPane stack = new StackPane();
        final int HANDSLOTS = handcardsTaken;
        stack.setId("HandCard"+HANDSLOTS);
        GridPane.setMargin(stack,new Insets(10,10,10,10));
        stack.getChildren().addAll(rect,efectCard);
        stack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if(card.getClass().getSimpleName().equals("Bullet")){
                    setBulletInSlot(controller.chambersTaken(),(Bullet) card);
                    pane.getChildren().remove(getNodeByNameId("HandCard"+HANDSLOTS));
                }else{
                    controller.useEffectCard(card);
                }
            }
        });
        pane.add(stack,5+handcardsTaken,5);
        handcardsTaken++;
    }

    /**
     * Removes an Enemy from playing-field
     * @param enemy
     */
    public static void removeEnemy(int enemy){
        pane.getChildren().remove(getNodeByNameId("Bar_"+enemy));
        Node node;
        for (int i = 1; (node=getNodeByNameId("Bar_"+i))!=null; i++) {
            pane.getChildren().remove(node);
            node.setId("Bar_"+(i-1));
            pane.add(node,i+1,2);
        }
    }

    /**
     * Sets BulletCard in Slot of Chamber
     * @param slot Slot of Chamber
     * @param bulletCard Bullet to Set
     */
    public static void setBulletInSlot(int slot, Bullet bulletCard){
        if(controller.chambersTaken()<=slot){
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
            setNodeInSlot(stack,slot);
            controller.setBulletInSlot(bulletCard);
        }
    }

    /**
     * Sets a node into a slot in chamber
     * @param node Node to set in chamber
     * @param slot slot of chamber (first ist 0)
     */
    public static void setNodeInSlot(Node node, int slot){
        switch (slot){
            case 0:
                pane.add(node,3,4);
                break;
            case 1:
                pane.add(node,2,5);
                break;
            case 2:
                pane.add(node,2,6);
                break;
            case 3:
                pane.add(node,4,6);
                break;
            case 4:
                pane.add(node,4,5);
                break;
        }
    }

    /**
     * removes bullet in shoot slot and rotates the chamber
     */
    public static void rotate(){
        pane.getChildren().remove(getNodeByNameId("Bullet"+0));
        Node node;
        for (int i = 1; (node=getNodeByNameId("Bullet"+i))!=null; i++) {
            pane.getChildren().remove(node);
            node.setId("Bullet"+(i-1));
            setNodeInSlot(node,i-1);
        }
    }

    /**
     * Gets a node by the Id
     * @param id Id of Node
     * @return Node/null if no node has id
     */
    public static Node getNodeByNameId(String id){
        for (Node node:
                pane.getChildren()) {
            if(node.getId()!=null && node.getId().equals(id)){
                return node;
            }
        }
        return null;
    }

    /**
     * Sets the Life displayed of Enemy
     * @param enemyNumber which enemy (1,2 or 3)
     * @param enemy The enemy
     */
    public static void setLifeOfEnemy(int enemyNumber,Enemy enemy){
        ProgressBar bar = (ProgressBar) getNodeByNameId("Bar_"+enemyNumber);
        bar.setProgress(enemy.getHealth()/enemy.getMaxHealth());
    }
}
