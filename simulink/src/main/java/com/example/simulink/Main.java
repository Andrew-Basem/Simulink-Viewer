package com.example.simulink;

import com.example.simulink.datamodels.*;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main extends javafx.application.Application {

    public File file;

    private static int strokeWidth = 1;
    private static int fontSize = 8;
    private static int spacing = 10;
    private List<Pane> panes = FXCollections.observableArrayList();
    private ArrayList<Box> boxes= new ArrayList<>();

    @Override
    public void start(Stage stage) throws IOException {

        stage.setFullScreen(true);

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load(), stage.getWidth(), stage.getHeight());
        stage.setTitle("Simulink");
        stage.setScene(scene);
        stage.show();


    }

    public static void main(String[] args) {
        launch();
    }

    public void drawArrowLine(double startX, double startY, double endX, double endY, Pane pane) {
        transparent(pane);
        // get the slope of the line and find its angle
        double slope = (startY - endY) / (startX - endX);
        double lineAngle = Math.atan(slope);

        double arrowAngle = startX > endX ? Math.toRadians(45) : -Math.toRadians(225);

        Line line = new Line(startX, startY, endX, endY);
        Box.stroke(line, Color.DARKCYAN,strokeWidth);


        double arrowLength = 4;

        // create the arrow legs
        Line arrow1 = new Line();
        arrow1.setStartX(line.getEndX());
        arrow1.setStartY(line.getEndY());
        arrow1.setEndX(line.getEndX() + arrowLength * Math.cos(lineAngle - arrowAngle));
        arrow1.setEndY(line.getEndY() + arrowLength * Math.sin(lineAngle - arrowAngle));


        Line arrow2 = new Line();
        arrow2.setStartX(line.getEndX());
        arrow2.setStartY(line.getEndY());
        arrow2.setEndX(line.getEndX() + arrowLength * Math.cos(lineAngle + arrowAngle));
        arrow2.setEndY(line.getEndY() + arrowLength * Math.sin(lineAngle + arrowAngle));

        Polygon poly = new Polygon(line.getEndX(),line.getEndY(),arrow1.getEndX(),arrow1.getEndY(),arrow2.getEndX(),arrow2.getEndY());
        Box.stroke(poly,Color.DARKCYAN,strokeWidth);
        pane.getChildren().addAll(line, poly);
    }

    public void drawCircleLine(double startX, double startY, double endX, double endY, Pane pane){
        transparent(pane);
        Circle circle = new Circle(startX,startY,2);
        Line l = new Line(startX,startY,endX,endY);
        Box.stroke(l,Color.DARKCYAN,strokeWidth);
        pane.getChildren().addAll(l,circle);
    }
    public void drawNormalLine(double startX, double startY, double endX, double endY, Pane pane){
        transparent(pane);
        Line l = new Line(startX,startY,endX,endY);
        Box.stroke(l,Color.DARKCYAN,strokeWidth);
        pane.getChildren().addAll(l);
    }

    public Box getBox (ArrayList<Box> boxes,int sid){
        Box b = new Box();
        for (int i=0 ;i<boxes.size();i++){
            if(boxes.get(i).getSID() == sid){
                b = boxes.get(i);
            }
        }
        return b;

    }


    public void transparent (Pane p){
        p.setStyle("-fx-background-color: rgba(255, 255, 255, 0);");
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public static int getStrokeWidth() {
        return strokeWidth;
    }

    public static void setStrokeWidth(int strokeWidth) {
        Main.strokeWidth = strokeWidth;
    }

    public static int getFontSize() {
        return fontSize;
    }

    public static void setFontSize(int fontSize) {
        Main.fontSize = fontSize;
    }

    public static int getSpacing() {
        return spacing;
    }

    public static void setSpacing(int spacing) {
        Main.spacing = spacing;
    }

    public List<Pane> getPanes() {
        return panes;
    }

    public void setPanes(List<Pane> panes) {
        this.panes = panes;
    }

    public ArrayList<Box> getBoxes() {
        return boxes;
    }

    public void setBoxes(ArrayList<Box> boxes) {
        this.boxes = boxes;
    }



}