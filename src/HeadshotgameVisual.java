import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;


public class HeadshotgameVisual extends Region implements Runnable{

    Pane pane = new Pane();
    private double speed;
    private double sizeArea;
    private double pointer = 1;
    private Line line;
    private double width;
    Arc fieldToHit;

    public HeadshotgameVisual(double speed, double sizeArea,int width, Color color) {
        this.speed = speed;
        this.sizeArea = sizeArea;
        this.minWidth(width);
        this.maxWidth(width);
        this.minHeight(width);
        this.maxHeight(width);
        this.width = width;
        Circle circle = new Circle();
        circle.setLayoutX(width);
        circle.setLayoutY(width);
        circle.setRadius(width-(width/5)/2);
        circle.setStroke(color);
        circle.setFill(Color.TRANSPARENT);
        circle.setStrokeWidth(width/5);
        Circle outSide = new Circle();
        outSide.setLayoutX(width);
        outSide.setLayoutY(width);
        outSide.setRadius(width+(width/50)/2);
        outSide.setStrokeWidth(width/50);
        outSide.setFill(Color.TRANSPARENT);
        outSide.setStroke(Color.BLACK);
        Circle inside = new Circle();
        inside.setLayoutX(width);
        inside.setLayoutY(width);
        inside.setRadius(width-(width/2.5)/2);
        inside.setStrokeWidth(width/50);
        inside.setFill(Color.TRANSPARENT);
        inside.setStroke(Color.BLACK);
        line = new Line(width,0,width,width/4.5);
        line.setManaged(false);
        fieldToHit = new Arc();
        pane.getChildren().addAll(circle,fieldToHit,outSide,inside,line);
    }

    public Pane getNode() {
        return pane;
    }

    public void run(){
        fieldToHit.setLayoutX(width+(width/50));
        fieldToHit.setLayoutY(width+(width/50));
        fieldToHit.setRadiusX(width-(width/10));
        fieldToHit.setRadiusY(width-(width/10));
        fieldToHit.setStartAngle(90-sizeArea);
        fieldToHit.setLength(sizeArea);
        fieldToHit.setFill(Color.TRANSPARENT);
        fieldToHit.setManaged(false);
        fieldToHit.setStrokeWidth(width/4.5);
        fieldToHit.setStroke(Color.GREY);
        fieldToHit.setType(ArcType.OPEN);
        final long startTime = System.nanoTime();
        new AnimationTimer(){

            @Override
            public void handle(long l) {
                double t = (l - startTime) / (10_000_000_000.0/speed)+Math.PI;
                double x = width + width * Math.sin(t);
                double y = width + width * Math.cos(t);
                line.setStartX(x);
                line.setStartY(y);
                line.setEndX(x-(x-width));
                line.setEndY(y-(y-width));
                pointer = t;
            }
        }.start();
    }

    public synchronized boolean isInside(){
        pointer = (pointer+Math.PI)%(2*Math.PI);
        //System.out.println(Math.PI*2-pointer);
        //System.out.println(sizeArea*(Math.PI/180));
        if(Math.PI*2-pointer< sizeArea*(Math.PI/180)*2){
            return true;
        }
        return false;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public double getSizeArea() {
        return sizeArea;
    }

    public void setSizeArea(double sizeArea) {
        this.sizeArea = sizeArea;
    }

    public void terminate(){
        Thread.currentThread().interrupt();
    }
}
