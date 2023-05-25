package com.example.simulink.datamodels;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;

public class Box {
    protected static int strokeWidth = 1;
    protected static int fontSize = 8;
    protected static int spacing = 6;
    private int SID;
    private String name;
    private int[] position;

    public Box() {
    }

    public Box(int SID, String name, int[] position) {
        this.SID = SID;
        this.name = name;
        this.position = position;
    }

    public int getSID() {
        return SID;
    }

    public void setSID(int SID) {
        this.SID = SID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int[] getPosition() {
        return position;
    }

    public void setPosition(int[] position) {
        this.position = position;
    }

    public Pane drawObject(Box b){
        return new Pane();
    }
    public int getcenter(Box b){
        return (b.getPosition()[1] + ((Math.abs(b.getPosition()[0] - b.getPosition()[2]) )/2 ));

    }

    public static void stroke (Shape shape, Color c, int w){
        shape.setStroke(c);
        shape.setStrokeWidth(w);
    }

    @Override
    public String toString() {
        return name;
    }
}