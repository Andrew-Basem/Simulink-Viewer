package com.example.simulink.datamodels;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Sum extends Box{
    private int[] ports;
    private String input;

    public Sum(int SID, String name, int[] position, int[] ports, String input) {
        super(SID, name, position);
        this.ports = ports;
        this.input = input;
    }

    public int[] getPorts() {
        return ports;
    }

    public void setPorts(int[] ports) {
        this.ports = ports;
    }

    public String getInput() {
        return String.valueOf(this.input.charAt(0));
    }

    public void setInput(String input) {
        this.input = input;
    }

    @Override
    public Pane drawObject(Box b) {
        VBox vb = new VBox();
        StackPane sp = new StackPane();

        //Positioning
        vb.setLayoutX(b.getPosition()[0]);
        vb.setLayoutY(b.getPosition()[1]);

        //drawing rectangle and its cyan border
        int side = Math.abs(b.getPosition()[2] - b.getPosition()[0]);
        Rectangle square = new Rectangle(side, side, Color.WHITE);
        Rectangle square2 = new Rectangle(side+5, side+5,Color.DARKCYAN);
        stroke(square,Color.BLACK,strokeWidth);

        //Aligning inputs
        VBox vbIN = new VBox(-7);
        for(int i=0;i<ports[0];i++){
            vbIN.getChildren().add(new Text(getInput()));
        }
        Text t = new Text("+");

        vbIN.setAlignment(Pos.CENTER_LEFT);
        vbIN.setPadding(new Insets(-6,0,0,2));

        //adding children to stackPane
        sp.getChildren().addAll(square2,square,vbIN);

        //drawing bottom text
        Text btm = new Text(b.toString());
        btm.setFont(new Font(fontSize));
        btm.setFill(Color.DARKCYAN);

        //setting spacing between box and text
        vb.setSpacing(spacing);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(sp,btm);

        return vb;
    }
}
