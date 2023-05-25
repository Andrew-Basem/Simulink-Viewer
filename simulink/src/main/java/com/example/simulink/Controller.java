package com.example.simulink;

import com.example.simulink.datamodels.*;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Scanner;
public class Controller {
    Main m = new Main();
    private final String str ="Open your .mdl file";
    private static final String IDLE_BUTTON_STYLE = "-fx-text-fill:linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%); -fx-border-radius: 10; -fx-border-color: Black; -fx-background-color:White;";
    private static final String HOVERED_BUTTON_STYLE = "-fx-text-fill:linear-gradient(from 0% 0% to 100% 200%, repeat, aqua 0%, red 50%); -fx-border-radius: 10; -fx-border-color: transparent; -fx-background-color:#d9d6d4;" ;


    @FXML
    public Button browse;

    @FXML
    public Label open;

    @FXML
    public StackPane stack;

    public void initialize(){
        browse.setOnMouseEntered(e -> browse.setStyle(HOVERED_BUTTON_STYLE));
        browse.setOnMouseExited(e -> browse.setStyle(IDLE_BUTTON_STYLE));


        final IntegerProperty i = new SimpleIntegerProperty(0);
        Timeline timeline = new Timeline();
        KeyFrame keyFrame = new KeyFrame(
                Duration.seconds(0.12),
                event -> {
                    if (i.get() > str.length()) {
                        timeline.stop();
                    } else {
                        open.setText(str.substring(0, i.get()));
                        i.set(i.get() + 1);
                    }
                });
        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.setDelay(Duration.seconds(0.3));
        timeline.play();
    }


    @FXML
    public void buttonAction(){
        FileChooser.ExtensionFilter ex1 = new FileChooser.ExtensionFilter("MDL Files", "*.mdl");
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(ex1);
        chooser.setTitle("Select simulink file...");

        try {
            m.setFile(chooser.showOpenDialog(stack.getScene().getWindow()));
            if(m.getFile() != null){


                FileInputStream stream = new FileInputStream(m.getFile()); //for reading from file
                StringBuffer sb = new StringBuffer(); //will carry content of file
                int num; //for read method purpose
                while ((num = stream.read()) != -1) {
                    sb.append((char) num); // append till reach end of file
                }
                String parsed = sb.toString();
                Scanner input = new Scanner(parsed); //for reading data parsed to string

                while (input.hasNext()) {
                    String tag = input.nextLine();
                    if (tag.contains("<System>")) {
                        while (input.hasNext()) {
                            String component = input.nextLine();

                            if (component.startsWith("  <Block")) {
                                String btype = component.substring(component.indexOf("BlockType=") + 11, component.indexOf(" Name") - 1);
                                String name = component.substring(component.indexOf("Name=") + 6, component.indexOf(" SID") - 1);
                                String sid = component.substring(component.indexOf("SID=") + 5, component.indexOf(">") - 1);
                                int sidInt = Integer.parseInt(sid);
                                if (btype.equals("Sum")) {

                                    int[] ports = new int[5];
                                    int[] position = new int[5];
                                    String inputs = new String("");

                                    while (!(component.contains("</Block>"))) {
                                        component = input.nextLine();
                                        if (component.contains("Ports")) {

                                            String n = component.substring(component.indexOf("[")+1,component.indexOf("]"));
                                            String[] parts = n.split(", ");
                                            for (int i = 0 ;i<parts.length;i++){
                                                String x = parts[i];
                                                ports[i] = Integer.parseInt(x);

                                            }

                                        } else if (component.contains("    <P Name=\"Position\">")) {
                                            String str = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                            String[] parts = str.split(", ");
                                            int left = Integer.parseInt(parts[0]);
                                            int top = Integer.parseInt(parts[1]);
                                            int right = Integer.parseInt(parts[2]);
                                            int bottom = Integer.parseInt(parts[3]);
                                            position = new int[]{left, top, right, bottom};

                                        } else if (component.contains("    <P Name=\"Inputs\">")) {
                                            inputs = component.substring(component.indexOf("+"), component.indexOf("</P>"));

                                        }
                                    }
                                    Sum sum = new Sum(sidInt, name, position, ports, inputs);
                                    m.getBoxes().add(sum);
                                } else if (btype.contains("Constant")) {
                                    int[] position = new int[4];
                                    while (!(component.contains("</Block>"))) {
                                        component = input.nextLine();

                                        if (component.contains("    <P Name=\"Position\">")) {
                                            String str = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                            String[] parts = str.split(", ");
                                            int left = Integer.parseInt(parts[0]);
                                            int top = Integer.parseInt(parts[1]);
                                            int right = Integer.parseInt(parts[2]);
                                            int bottom = Integer.parseInt(parts[3]);
                                            position = new int[]{left, top, right, bottom};

                                        }


                                    }
                                    Constant constant = new Constant(sidInt, name, position);
                                    m.getBoxes().add(constant);
                                } else if (btype.contains("Saturate")) {
                                    int[] position = new int[4];
                                    int[] ports = new int[3];
                                    while (!(component.contains("</Block>"))) {
                                        component = input.nextLine();

                                        if (component.contains("    <P Name=\"Position\">")) {
                                            String str = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                            String[] parts = str.split(", ");
                                            int left = Integer.parseInt(parts[0]);
                                            int top = Integer.parseInt(parts[1]);
                                            int right = Integer.parseInt(parts[2]);
                                            int bottom = Integer.parseInt(parts[3]);
                                            position = new int[]{left, top, right, bottom};

                                        }



                                    }
                                    Saturate saturate = new Saturate(sidInt, name, position);
                                    m.getBoxes().add(saturate);
                                } else if (btype.contains("Scope")) {
                                    int[] position = new int[4];
                                    int[] ports = new int[3];
                                    while (!(component.contains("</Block>"))) {
                                        component = input.nextLine();

                                        if (component.contains("    <P Name=\"Ports\">")) {
                                            String n = component.substring(component.indexOf("[")+1,component.indexOf("]"));
                                            String[] parts = n.split(", ");
                                            for (int i = 0 ;i<parts.length;i++){
                                                String x = parts[i];
                                                ports[i] = Integer.parseInt(x);

                                            }
                                        } else if (component.contains("    <P Name=\"Position\">")) {
                                            String str = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                            String[] parts = str.split(", ");
                                            int left = Integer.parseInt(parts[0]);
                                            int top = Integer.parseInt(parts[1]);
                                            int right = Integer.parseInt(parts[2]);
                                            int bottom = Integer.parseInt(parts[3]);
                                            position = new int[]{left, top, right, bottom};

                                        }

                                    }

                                    Scope sc = new Scope(sidInt, name, position,ports);
                                    m.getBoxes().add(sc);
                                } else if (btype.contains("UnitDelay")) {
                                    int[] position = new int[4];
                                    while (!(component.contains("</Block>"))) {
                                        component = input.nextLine();

                                        if (component.contains("    <P Name=\"Position\">")) {
                                            String str = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                            String[] parts = str.split(", ");
                                            int left = Integer.parseInt(parts[0]);
                                            int top = Integer.parseInt(parts[1]);
                                            int right = Integer.parseInt(parts[2]);
                                            int bottom = Integer.parseInt(parts[3]);
                                            position = new int[]{left, top, right, bottom};

                                        }
                                    }
                                    UnitDelay u = new UnitDelay(sidInt,name,position);
                                    m.getBoxes().add(u);
                                }
                            }else if (component.contains("  <Line>")) {

                                ArrayList<int[]> points = new ArrayList<>();
                                int dst=0;
                                int src=0;
                                ArrayList<Branch> branchs = new ArrayList<>();
                                while (!(component.contains("</Line>"))) {
                                    component = input.nextLine();
                                    if (component.contains("<Branch>")){
                                        int branch =0;
                                        int dst2=0;
                                        int src2=0;
                                        ArrayList<int[]> points2 = new ArrayList<>();
                                        while (!(component.contains("</Branch>"))){
                                            component = input.nextLine();
                                            branch++;


                                            String Dst;
                                            if (component.contains("Src")) {
                                                String Src = component.substring(component.indexOf(">") + 1, component.indexOf('#'));


                                            } else if (component.contains("Points")) {

                                                if (component.contains(";")) {
                                                    String n = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                                    String[] parts = n.split("; ");
                                                    for (int i = 0; i < (parts.length); i++) {
                                                        String[] parts1 = parts[i].split(", ");
                                                        int[] p = new int[2];
                                                        p[0] = Integer.parseInt(parts1[0]);
                                                        p[1] = Integer.parseInt(parts1[1]);
                                                        points.add(p);

                                                    }

                                                } else {
                                                    String n = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                                    String[] parts = n.split(", ");
                                                    int[] p = new int[2];
                                                    p[0] = Integer.parseInt(parts[0]);
                                                    p[1] = Integer.parseInt(parts[1]);
                                                    points2.add(p);


                                                }
                                            } else if (component.contains("Dst")) {
                                                Dst = component.substring(component.indexOf(">") + 1, component.indexOf('#'));
                                                dst2 = Integer.parseInt(Dst);

                                            }

                                        }
                                        Branch b = new Branch(points2,dst2);
                                        branchs.add(b);

                                    }else { // WITHOUT BRANCHES

                                        String Dst = new String("");
                                        if (component.contains("Src")) {
                                            String Src = component.substring(component.indexOf(">") + 1, component.indexOf('#'));
                                            src = Integer.parseInt(Src);

                                        } else if (component.contains("Points")) {

                                            if (component.contains(";")) {
                                                String n = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                                String[] parts = n.split("; ");
                                                for (int i = 0; i < (parts.length); i++) {
                                                    String[] parts1 = parts[i].split(", ");
                                                    int[] p = new int[2];
                                                    p[0] = Integer.parseInt(parts1[0]);
                                                    p[1] = Integer.parseInt(parts1[1]);
                                                    points.add(p);

                                                }

                                            } else {
                                                String n = component.substring(component.indexOf("[") + 1, component.indexOf("]"));
                                                String[] parts = n.split(", ");
                                                int[] p = new int[2];
                                                p[0] = Integer.parseInt(parts[0]);
                                                p[1] = Integer.parseInt(parts[1]);
                                                points.add(p);


                                            }
                                        } else if (component.contains("Dst")) {
                                            Dst = component.substring(component.indexOf(">") + 1, component.indexOf('#'));
                                            dst = Integer.parseInt(Dst);

                                        }


                                    }

                                }
                                // draw all lines and branches
                                NewLine line = new NewLine(src,dst,points,branchs);
                                Box b1 = m.getBox(m.getBoxes(),line.getSrc());
                                Box b2 = m.getBox(m.getBoxes(),line.getDst()) ;

                                if (line.getBranches().size()==0 && line.getPoints().size()==0){
                                    Pane p = new Pane();

                                    m.drawArrowLine(b1.getPosition()[2], b1.getcenter(b1), b2.getPosition()[0], b2.getcenter(b2), p);
                                    m.getPanes().add(p);
                                }else if (line.getBranches().size()==0 && line.getPoints().size()>0){
                                    int startx = 0;
                                    int starty = 0;
                                    for (int i = 0;i<line.getPoints().size();i++){

                                        int[] pp = line.getPoints().get(i);
                                        if (pp[0] <0){
                                            Pane p = new Pane();
                                            m.drawNormalLine(b1.getPosition()[0],b1.getcenter(b1),b1.getPosition()[0]+pp[0],b1.getcenter(b1)+pp[1],p);
                                            m.getPanes().add(p);
                                            startx += pp[0];
                                        }
                                        if (pp[1] < 0){
                                            Pane p = new Pane();
                                            m.drawNormalLine(b1.getPosition()[0]+ startx ,b1.getcenter(b1),b1.getPosition()[0]+startx,b1.getcenter(b1)+pp[1],p);
                                            m.getPanes().add(p);
                                            starty += pp[1];
                                        }

                                    }
                                    Pane p = new Pane();
                                    m.drawArrowLine(b1.getPosition()[0]+ startx ,b1.getcenter(b1)+starty,b2.getPosition()[0]  ,b1.getcenter(b1)+starty,p);
                                    m.getPanes().add(p);
                                } else if (line.getBranches().size()>0) {
                                    int startx = 0;
                                    int starty = 0;
                                    int[] ll = line.getPoints().get(0);
                                    Pane p = new Pane();
                                    m.drawNormalLine(b1.getPosition()[2] ,b1.getcenter(b1),b1.getPosition()[2]+ll[0],b1.getcenter(b1),p);
                                    m.getPanes().add(p);
                                    startx += ll[0];
                                    Box b3 = new Box();
                                    if (line.getBranches().get(0) != null ){
                                        b3 = m.getBox(m.getBoxes(),line.getBranches().get(0).getDst());
                                    }
                                    for (int i=0;i<branchs.size();i++){
                                        if (i==0 && branchs.get(i).getPoints().size()>0){
                                            int[] branchpo = branchs.get(i).getPoints().get(i);
                                            Pane circle = new Pane();
                                            m.drawCircleLine(b1.getPosition()[2]+startx ,b1.getcenter(b1),b1.getPosition()[2]+startx,b1.getcenter(b1) + branchpo[1],circle);
                                            m.getPanes().add(circle);
                                            starty += branchpo[1];

                                            if (Math.abs((b3.getPosition()[0])-(b1.getPosition()[2]+startx)) < Math.abs((b3.getPosition()[2])-(b1.getPosition()[2]+startx))) {
                                                Pane ff = new Pane();
                                                m.drawArrowLine(b1.getPosition()[2] + startx, b1.getcenter(b1) + starty, b3.getPosition()[0], b1.getcenter(b1) + starty, ff);
                                                m.getPanes().add(ff);
                                            }else {
                                                Pane fff = new Pane();
                                                m.drawArrowLine(b1.getPosition()[2] + startx, b1.getcenter(b1) + starty, b3.getPosition()[2]  , b1.getcenter(b1) + starty, fff);
                                                m.getPanes().add(fff);
                                            }

                                        }else if(i >0) {
                                            Branch br =branchs.get(i);
                                            b3 = m.getBox(m.getBoxes(),br.getDst());
                                            Pane arrow = new Pane();
                                            m.drawArrowLine(b1.getPosition()[2]+startx ,b1.getcenter(b1),b3.getPosition()[0],b1.getcenter(b1) ,arrow);
                                            m.getPanes().add(arrow);
                                        }

                                    }

                                }


                            }
                            else if (tag.contains("</System>")) {
                                break;
                            }
                        }

                    }
                }

                for (int i=0;i< m.getBoxes().size();i++){
                    m.getPanes().add(m.getBoxes().get(i).drawObject(m.getBoxes().get(i)));
                }
                Pane root = new Pane();

                root.setMaxWidth(Double.MAX_VALUE);
                root.setMaxHeight(Double.MAX_VALUE);

                root.getChildren().addAll(m.getPanes());

                Scene scene = new Scene(root, Double.MAX_VALUE, Double.MAX_VALUE);
                Stage stage = new Stage();
                stage.setFullScreen(true);
                stage.setScene(scene);
                stage.show();


            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

    }
}