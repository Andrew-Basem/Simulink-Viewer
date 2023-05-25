package com.example.simulink.datamodels;

import javafx.geometry.Pos;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Constant extends Box{
    public Constant(int SID, String name, int[] position) {
        super(SID, name, position);
    }


    @Override
    public Pane drawObject(Box b) {
        VBox vb = new VBox();
        StackPane sp = new StackPane();

        //Positioning
        vb.setLayoutX(b.getPosition()[0]);
        vb.setLayoutY(b.getPosition()[1]-8);

        //drawing rectangle and its cyan border
        int side = Math.abs(b.getPosition()[2] - b.getPosition()[0]);
        Rectangle square = new Rectangle(side, side, Color.WHITE);
        Rectangle square2 = new Rectangle(side+5, side+5,Color.DARKCYAN);
        stroke(square,Color.BLACK,strokeWidth);

        //drawing border offset
        Rectangle main = new Rectangle(side+5+10,side+5+10,Color.WHITE); //side+5+10(for border offset)
        stroke(main,Color.DARKCYAN,3);

        Rectangle h = new Rectangle(side,side+18,Color.WHITE);//side,side-2+10+10
        Rectangle w = new Rectangle(side+18,side,Color.WHITE);

        //drawing middle text
        Text text = new Text("1");
        text.setFont(new Font(12));
        text.setFill(Color.DARKCYAN);

        //adding children to stackPane
        sp.getChildren().addAll(main,h,w,square2,square,text);

        //drawing bottom text
        Text btm = new Text(b.toString());
        btm.setFont(new Font(fontSize));
        btm.setFill(Color.DARKCYAN);

        vb.setSpacing(spacing-4);
        vb.setAlignment(Pos.CENTER);
        vb.getChildren().addAll(sp,btm);

        return vb;
    }
}
