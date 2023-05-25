package com.example.simulink.datamodels;

import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class Saturate extends Box{
    public Saturate(int SID, String name, int[] position) {
        super(SID, name, position);
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


        //adjusting image
        Image img = new Image("/download.jpg");
        ImageView selectedImage = new ImageView();
        selectedImage.setFitHeight(square.getHeight()-2);
        selectedImage.setFitWidth(square.getWidth()-2);

        selectedImage.setImage(img);

        sp.getChildren().addAll(square2,square,selectedImage);

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
