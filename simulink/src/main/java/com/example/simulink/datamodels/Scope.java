package com.example.simulink.datamodels;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Scope extends Box{
    private int[] ports;

    public Scope(int SID, String name, int[] position, int[] ports) {
        super(SID, name, position);
        this.ports = ports;
    }

    public int[] getPorts() {
        return ports;
    }

    public void setPorts(int[] ports) {
        this.ports = ports;
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

        //drawing inner rectangle
        Rectangle inner = new Rectangle(15,10,Color.WHITE);
        inner.setStroke(Color.BLACK);
        inner.setStrokeWidth(1);

        sp.getChildren().addAll(square2,square,inner);

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
