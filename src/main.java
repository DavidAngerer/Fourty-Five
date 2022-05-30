import javafx.application.Application;
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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.MalformedURLException;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author David Angelo, Philip Jankovic
 * main class for the .Fourty-Five game, handles graphics (javafx)
 */
public class main extends Application {
    //TODO effectkarten spielen:
    //TODO efect type
    //TODO bullet type
    //TODO remorse
    //TODO enemy aussuchen effekt
    //TODO bei poison kill hinzufügen
    //TODO deathscreen

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
     *
     * @param stage primary Stage
     */
    @Override
    public void start(Stage stage) {
        pane = new GridPane();
        scene = new Scene(pane, width, height);

        //funktioniert noch nicht
        //stage.getIcons().add(new Image("small_titlepic_v1.png"));
        EventHandler eventHandlerMouse = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                menu();
                scene.removeEventHandler(MouseEvent.MOUSE_CLICKED, this);
            }
        };

        EventHandler eventHandlerKey = new EventHandler<KeyEvent>() {

            @Override
            public void handle(KeyEvent keyEvent) {
                menu();
                scene.removeEventHandler(KeyEvent.KEY_PRESSED, this);
            }
        };
        scene.addEventHandler(KeyEvent.KEY_PRESSED, eventHandlerKey);
        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, eventHandlerMouse);


        VBox box = new VBox();
        ImageView imageView = new ImageView();

        //stage.setFullScreen(true);
        box.getChildren().add(imageView);
        imageView.setImage(new Image(this.getClass().getResource("backgrounds/titlescreen.gif").toExternalForm()));
//        pane.add(box, 0, 0);
        imageView.setFitWidth(width);
        imageView.setFitHeight(height);

        try {
            BackgroundSize size = new BackgroundSize(-1d, -1d, true, true, true, false);
            Background back = new Background(new BackgroundImage(new Image(String.valueOf(Path.of("./res/backgrounds/titlescreen.gif").toUri().toURL())), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));
            pane.setBackground(back);
        } catch (MalformedURLException ignored) {
            ignored.printStackTrace();
        }

        stage.setScene(scene);
        stage.setFullScreenExitHint("");
        stage.show();
    }

    /**
     * Called once to get to Menu with start button and quit button
     */
    private static void menu() {


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
        ImageView buttonNewGame = new ImageView("button_newGame_v6.png");
        pane.add(buttonNewGame, 3, 1);
        GridPane.setHalignment(buttonNewGame, HPos.CENTER);

        //buttonNewGame.setPickOnBounds(true); // allows click on transparent areas
        buttonNewGame.setOnMouseClicked((MouseEvent e) -> {
            startGame();
            controller.nextStage();// change functionality
        });

        //picture button quit
        ImageView buttonQuit = new ImageView("button_quit_v6.png");
        GridPane.setHalignment(buttonQuit, HPos.CENTER);

        pane.add(buttonQuit, 3, 2);
        //buttonQuit.setPickOnBounds(true); // allows click on transparent areas
        buttonQuit.setOnMouseClicked((MouseEvent e) -> {
            System.exit(0); // change functionality
        });


        try {
            BackgroundSize size = new BackgroundSize(-1d, -1d, true, true, true, true);
            Background back = new Background(new BackgroundImage(new Image(String.valueOf(Path.of("./res/backgrounds/static_bg_v2.png").toUri().toURL())), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, size));
            pane.setBackground(back);
        } catch (MalformedURLException ignored) {
            ignored.printStackTrace();
        }
    }

    public static Card cardSelectScreen(){
        pane.setGridLinesVisible(true);
        pane.getColumnConstraints().clear();
        pane.getRowConstraints().clear();
        pane.setMaxWidth(scene.getWidth());
        pane.setMinWidth(scene.getWidth());
        pane.getChildren().clear();
        pane.setBackground(null);
        Card[] cardsToSelect = controller.getCardsToSelect();
        for (int i = 0; i < cardsToSelect.length; i++) {
            addCardToSelectScreen(i,cardsToSelect);
        }
        return null;
    }

    public static void addCardToSelectScreen(int slot, Card[] card){
        StackPane stack = getCardVisual(400,card[slot].getCardNameAsString());
        card[slot].setNode(stack);
        stack.setId("Choose"+slot);
        stack.setOnMouseEntered(e -> {
            turnCardAround(card[slot],slot);
            for (int i = 0; i < 3; i++) {
                if(slot!=i && getNodeByNameId("Choose"+i)==null){
                    pane.getChildren().remove(getNodeByNameId("Turned"+i));
                    addCardToSelectScreen(i,card);
                }
            }
        });
        pane.add(stack,slot,2);
    }

    /**
     * Turns Card Around
     * @param card
     */
    public static void turnCardAround(Card card, int slot){
        StackPane stack = getCardVisual(400,card.getStats().entrySet().stream().map(n -> n.getKey() + " = " + n.getValue()).
                collect(Collectors.joining("\n")));
        pane.getChildren().remove(getNodeByNameId("Choose"+slot));
        pane.add(stack,slot,2);
        stack.setId("Turned"+slot);
        stack.setOnMouseClicked(e ->{
            controller.addCardToPlayer(card);
            controller.nextStage();
        });
    }

    private static StackPane getCardVisual(int size,String writing) {
        Text text = new Text(writing);
        text.setFill(Color.BLACK);
        Rectangle rect = new Rectangle();
        rect.setHeight(size);
        rect.setWidth(size);
        rect.setFill(Color.GREY);
        StackPane stack = new StackPane();
        GridPane.setMargin(stack, new Insets(10, 10, 10, 10));
        stack.getChildren().addAll(rect, text);
        return stack;
    }

    /**
     * Called once to start the game
     */
    public static void startGame() {
        controller = new Controller(1, 0);
    }

    /**
     * Enters a new Stage with new Enemies
     *
     * @param stage   Number of Stage
     * @param health  Health of Player
     * @param enemies Number of Enemies
     */
    public static void newStage(int stage, int health, ArrayList<Enemy> enemies) {
        handcardsTaken = 0;
        pane.setGridLinesVisible(true);
        pane.getColumnConstraints().clear();
        pane.getRowConstraints().clear();
        ArrayList<Bullet> bullets = new ArrayList<>();
        ArrayList<EfectCard> efectCards = new ArrayList<>();
        Text energy = new Text("Energy left = 5");
        energy.setId("Energy");
        pane.setMaxWidth(scene.getWidth());
        pane.setMinWidth(scene.getWidth());
        pane.getChildren().clear();
        pane.setBackground(null);
        Text text = new Text(health +"/" + controller.getMaxHealth());
        text.setId("Life");
        pane.add(text, 0, 0);
        //TODO Phillip anfangs box hintun wo dann wenn noch keine karte drüber gehovert wurde
        bullets.addAll(controller.getRndBullets(3));
        efectCards.addAll(controller.getRndEffectCards(3));
        for (int i = 0; i < 3; i++) {
            addCardInHand(bullets.get(i));
            addCardInHand(efectCards.get(i));
        }

        scene.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                    leaveshootingMode();
                }
            }
        });
        buttonEndTurn();
        buttonShoot(enemies);
        pane.add(new Text("Stage" + stage), 4, 0);
        pane.add(energy, 3, 0);
        createEnemies(enemies);
    }

    private static void buttonEndTurn() {
        Button endTurn = new Button("End Turn");
        endTurn.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                controller.enemiesTurn();
            }
        });
        pane.add(endTurn, 5, 1);
    }

    private static void buttonShoot(ArrayList<Enemy> enemies) {
        Button shoot = new Button("shoot");
        shoot.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (controller.chambersTaken() > 0 && controller.getEnergy() > 0) {
                    shootingMode = true;
                    for (Node node :
                            pane.getChildren()) {

                        node.setMouseTransparent(true);
                    }
                    for (int i = 0; i < enemies.size(); i++) {
                        StackPane enemy = (StackPane) enemies.get(i).getVisual();
                        enemy.setMouseTransparent(false);
                        Rectangle body = new Rectangle(80, 300);
                        body.setFill(Color.BLUE);
                        final Enemy ene = enemies.get(i);
                        body.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                controller.shoot(ene, true);
                                leaveshootingMode();
                            }
                        });
                        Rectangle head = new Rectangle(80, 80);
                        head.setFill(Color.GREEN);
                        head.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                            @Override
                            public void handle(MouseEvent mouseEvent) {
                                HeadshotgameVisual a = new HeadshotgameVisual(20,
                                        controller.getHeadShotProbability(),50,Color.LIGHTBLUE);
                                pane.add(a.getNode(),3,3);
                                new Thread(a).start();
                                scene.addEventHandler(KeyEvent.KEY_TYPED, new EventHandler<KeyEvent>(){
                                    @Override
                                    public void handle(KeyEvent keyEvent) {
                                        if(keyEvent.getCharacter().equals(" ")){
                                            if(a.isInside()){
                                                controller.shoot(ene, false);
                                            }else{
                                                controller.miss(ene);
                                            }
                                            scene.removeEventHandler(KeyEvent.KEY_TYPED,this);
                                            leaveshootingMode();
                                            pane.getChildren().remove(a.getNode());
                                            a.terminate();
                                        }
                                    }
                                });
                            }
                        });
                        enemy.setAlignment(head, Pos.TOP_CENTER);
                        enemy.getChildren().addAll(body, head);
                        enemies.get(i).setVisual(enemy);
                    }
                }
            }

        });
        pane.add(shoot, 5, 0);
    }

    public static void leaveshootingMode() {
        if (shootingMode) {
            for (Node node :
                    pane.getChildren()) {
                node.setMouseTransparent(false);
            }
            ArrayList<Enemy> enemies = controller.getEnemiesThisTurn();
            for (int i = 0; i < enemies.size(); i++) {
                StackPane pane = (StackPane) (enemies.get(i).getVisual());
                pane.getChildren().remove(1);
                if(pane.getChildren().size()==2){
                    pane.getChildren().remove(1);
                }
            }
            shootingMode = false;
        }
    }

    /**
     * Creates the Visuals and healthbars for enemies
     *
     * @param enemies the enemies to be placed
     */
    private static void createEnemies(ArrayList<Enemy> enemies) {
        ArrayList<StackPane> rects = new ArrayList<>();
        ArrayList<ProgressBar> progressBars = new ArrayList<>();
        for (int i = 0; i < enemies.size(); i++) {
            rects.add(new StackPane());
            Rectangle enemy = new Rectangle(80, 300);
            enemy.setFill(Color.RED);
            rects.get(i).getChildren().add(enemy);
            pane.add(rects.get(i), i + 2, 1);
            progressBars.add(new ProgressBar());
            progressBars.get(i).setProgress(1);
            pane.add(progressBars.get(i), i + 2, 2);
            enemies.get(i).setHealthBar(progressBars.get(i));
            enemies.get(i).setVisual(rects.get(i));
        }
    }

    /**
     * Zeigt den Todesscreen an wenn man gestorben ist
     */
    public static void deathScreen() {
        menu();
    }

    /**
     * Adds a card to Players Hand
     *
     * @param card Bullet or EfectCard
     */
    public static void addCardInHand(Card card) {
        StackPane stack = getCardVisual(150,card.getCardNameAsString());
        final int HANDSLOTS = handcardsTaken;
        stack.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                if (card.getClass().getSimpleName().equals("Bullet")) {
                    setBulletInSlot(controller.chambersTaken(), (Bullet) card);
                } else {
                    controller.useEffectCard((EfectCard) card);
                }
            }
        });
        stack.setOnMouseEntered(e -> {
            hoveredCard(card);
        });
        card.setNode(stack);
        putCardWhereGoes(card);
        controller.cardsOnField.add(card);
    }

    /**
     * Removes an Enemy from playing-field
     *
     * @param enemy
     */
    public static void removeEnemy(Enemy enemy) {
        pane.getChildren().removeAll(enemy.getHealthBar(), enemy.getVisual());
    }

    /**
     * Sets BulletCard in Slot of Chamber
     *
     * @param slot       Slot of Chamber
     * @param bulletCard Bullet to Set
     */
    public static void setBulletInSlot(int slot, Bullet bulletCard) {
        if (controller.chambersTaken() <= slot) {
            bulletCard.getNode().setId("Bullet"+slot);
            pane.getChildren().remove(bulletCard.getNode());
            StackPane stack = (StackPane) bulletCard.getNode();
            bulletCard.setNode(stack);
            GridPane.setMargin(stack, new Insets(10, 10, 10, 10));
            setNodeInSlot(stack, slot);
            controller.setBulletInSlot(bulletCard);
            handcardsTaken--;
        }
    }

    public static void setEnergy(int energy) {
        Text text = (Text) getNodeByNameId("Energy");
        text.setText("Energy left = " + energy);
    }

    public static void removeCard(Card card) {
        pane.getChildren().remove(card.getNode());
        handcardsTaken--;
    }

    public static void putCardWhereGoes(Card card) {
        for (int i = 0; i < 6; i++) {
            if (getNodeByNameId("HandCard" + i) == null) {
                pane.add(card.getNode(), 5 + i, 5);
                handcardsTaken++;
                card.getNode().setId("HandCard" + i);
                break;
            }
        }
    }

    /**
     * Sets a node into a slot in chamber
     *
     * @param node Node to set in chamber
     * @param slot slot of chamber (first ist 0)
     */
    public static void setNodeInSlot(Node node, int slot) {
        switch (slot) {
            case 0:
                pane.add(node, 3, 4);
                break;
            case 1:
                pane.add(node, 2, 5);
                break;
            case 2:
                pane.add(node, 2, 6);
                break;
            case 3:
                pane.add(node, 4, 6);
                break;
            case 4:
                pane.add(node, 4, 5);
                break;
        }
    }

    /**
     * Called when mouse hovers over Handcard
     * Displays stats
     *
     * @param card the Card which was hovered over
     */
    public static void hoveredCard(Card card) {
        pane.getChildren().remove(getNodeByNameId("Infos"));

        StackPane infos = new StackPane();
        //TODO ersetzten durch imageview von karte
        Text stats = new Text(card.getStats().entrySet().stream().map(n -> n.getKey() + " = " + n.getValue()).
                collect(Collectors.joining("\n")));
        Text name = new Text(card.getCardNameAsString());
        Rectangle background = new Rectangle(150, 300);
        background.setFill(Color.GREY);
        infos.getChildren().addAll(background, name, stats);
        infos.setAlignment(name, Pos.TOP_CENTER);
        infos.setId("Infos");
        pane.add(infos, 11, 1);
    }

    /**
     * removes bullet in shoot slot and rotates the chamber
     */
    public static void rotate() {
        ArrayList<Bullet> bullets = controller.getPlayersBullet();

        pane.getChildren().remove(bullets.get(0).getNode());
        for (int i = 1; i < bullets.size(); i++) {
            pane.getChildren().remove(bullets.get(i).getNode());
            setNodeInSlot(bullets.get(i).getNode(), i - 1);
        }
    }

    public static void TurnRight(){
        ArrayList<Bullet> bullets = controller.getPlayersBullet();
        pane.getChildren().remove(bullets.get(0).getNode());
        for (int i = 1; i < bullets.size(); i++) {
            pane.getChildren().remove(bullets.get(i).getNode());
            setNodeInSlot(bullets.get(i).getNode(), i - 1);
        }
        setNodeInSlot(bullets.get(0).getNode(),4);
    }

    public static void TurnLeft(){
        ArrayList<Bullet> bullets = controller.getPlayersBullet();
        if(bullets.size()==5){
            pane.getChildren().remove(bullets.get(4).getNode());
        }
        for (int i = bullets.size()-1; i >= 0; i--) {
            pane.getChildren().remove(bullets.get(i).getNode());
            setNodeInSlot(bullets.get(i).getNode(), i + 1);
        }
        if(bullets.size()==5){
            setNodeInSlot(bullets.get(4).getNode(),0);
        }
    }

    /**
     * Gets a node by the Id
     *
     * @param id Id of Node
     * @return Node/null if no node has id
     */
    public static Node getNodeByNameId(String id) {
        for (Node node :
                pane.getChildren()) {
            if (node.getId() != null && node.getId().equals(id)) {
                return node;
            }
        }
        return null;
    }

    /**
     * Displays Coinflip
     */
    public static void coinflip(boolean isHead){
        StackPane coin = new StackPane();
        Circle circle = new Circle(50);
        circle.setFill(Color.GOLD);
        Text text = new Text(isHead?"Head":"Tails");
        coin.getChildren().addAll(circle,text);
        pane.add(coin,3,3);
        scene.addEventHandler(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                pane.getChildren().remove(coin);
                scene.removeEventHandler(MouseEvent.MOUSE_PRESSED,this);
            }
        });
    }

    /**
     * Sets the Life displayed of Enemy
     *
     * @param enemy The enemy
     */
    public static void setLifeOfEnemy(Enemy enemy) {
        enemy.getHealthBar().setProgress((double) enemy.getHealth() / (double) enemy.getMaxHealth());
    }

    /**
     * Sets Life of player visualy
     *
     * @param life life to set
     */
    public static void setLife(int life) {
        Text text = (Text) getNodeByNameId("Life");
        text.setText(life + "/" + controller.getMaxHealth());
    }

    public static void displaySelectscreenForEffectAttack(ArrayList<Enemy> enemies,EfectCard card){
        shootingMode = true;
        for (Node node :
                pane.getChildren()) {

            node.setMouseTransparent(true);
        }
        for (int i = 0; i < enemies.size(); i++) {
            StackPane enemy = (StackPane) enemies.get(i).getVisual();
            enemy.setMouseTransparent(false);
            Rectangle body = new Rectangle(80, 300);
            body.setFill(Color.BLUE);
            final Enemy ene = enemies.get(i);
            body.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent mouseEvent) {
                    //controller.
                    leaveshootingMode();
                }
            });
            enemy.getChildren().add(body);
            enemies.get(i).setVisual(enemy);
        }
    }
}
