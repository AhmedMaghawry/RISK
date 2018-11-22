/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Map;
import com.jfoenix.controls.JFXButton;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static guru.nidi.graphviz.model.Factory.mutGraph;
import static guru.nidi.graphviz.model.Factory.mutNode;

/**
 * FXML Controller class
 *
 * @author void
 */
public class GraphViewerController implements Initializable {

    public static ArrayList<String> vertex = FXMLDocumentController.vertex;
    public static double[][][] sequence;
    private double seqGen[][];
    public static int step=4;
    private JFXButton[] btn = new JFXButton[7];
//    private static String[] colors = {"#4A148C" , "#00838F", "#2E7D32", "#283593", "#4E342E", "#37474F", "#827717"};
    String color ="#4A148C";
    public static boolean[] selection = new boolean[7];
    private boolean newInstance = false;

    @FXML
    private AnchorPane pane;

    @FXML
    private JFXButton startOverButton;

    @FXML
    private Label resultLabel;

    @FXML
    private JFXButton previousButton;

    @FXML
    void previousButtonAction(ActionEvent event) throws IOException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("CategoryChooser.fxml"));
        pane.getChildren().setAll(temp);
    }

    @FXML
    void startOverButtonAction(ActionEvent event) throws IOException {
        AnchorPane temp = FXMLLoader.load(getClass().getResource("/View/FXMLDocument.fxml"));
        pane.getChildren().setAll(temp);
    }

   
    @Override
    public void initialize(URL url, ResourceBundle rb) {


        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println(event.getSceneX());
                System.out.println(event.getSceneY());
            }
        });

        final int nodeRadius = 20;
// North America partion 1
//        layoutX = {47.0,318.0};
//        layoutY = {391.0,155.0};

        // Asia partitoin 2
//        layoutX = {702.0,1038.0};
//        layoutY = {116.0,326.0};
        int[] layoutX = {415, 267, 576, 267, 576, 184, 646};
        int[] layoutY = {221, 362, 362, 80, 80, 221, 221};
        Line[][] newEdge = new Line[7][7];
        boolean[][] flag = new boolean[7][7];

        for (int i = 0; i < 7; i++) {
            btn[i] = new JFXButton();

        }
        flag[0][0]=true;
        flag[1][0]=true;
        flag[0][2]=true;
        flag[0][3]=true;


        //Generating Vertices
        for (int i = 0; i < step; i++) {
            btn[i].setLayoutX(layoutX[i]);
            btn[i].setLayoutY(layoutY[i]);
            btn[i].setPrefHeight(40);
            btn[i].setPrefWidth(40);
            btn[i].setTextFill(Color.WHITE);
            btn[i].setStyle("-fx-background-color: " + color + "; -fx-background-radius: 35 35 35 35");

            btn[i].setText("v1");
            pane.getChildren().add(btn[i]);

        }

        //Generating Edges

        for (int i = 0; i < step; i++) {
            for (int j = 0; j < step; j++) {
                //Coordinate of starting point
                double x1 = btn[i].getLayoutX() + nodeRadius;
                double y1 = btn[i].getLayoutY() + nodeRadius;

                //Coordinate of ending point
                double x2 = btn[j].getLayoutX() + nodeRadius;
                double y2 = btn[j].getLayoutY() + nodeRadius;

                if (!flag[i][j]) {
                    newEdge[i][j] = new Line(x1, y1, x2, y2);
                    double mx = (x1 + x2) / 2;
                    double my = (y1 + y2) / 2;
                    double dnX = y2 - y1;
                    double dnY = x1 - x2;
                    // normalize ortogonal
                    double length = Math.hypot(dnX, dnY);
                    dnX /= length;
                    dnY /= length;
                    double factor = 20;
                    Path path = new Path(new MoveTo(x1, y1), new QuadCurveTo(mx + factor * dnX, my + factor * dnY, x2, y2));
                    pane.getChildren().add(path);


                }
            }
        }
        btn[0].setOnAction((event) -> {
            KopaShamsuKopa(btn[0], 0);
        });

        btn[1].setOnAction((event) -> {
            KopaShamsuKopa(btn[1], 1);
        });

        btn[2].setOnAction((event) -> {
            KopaShamsuKopa(btn[2], 2);
        });

        btn[3].setOnAction((event) -> {
            KopaShamsuKopa(btn[3], 3);
        });

        btn[4].setOnAction((event) -> {
            KopaShamsuKopa(btn[4], 4);
        });

        btn[5].setOnAction((event) -> {
            KopaShamsuKopa(btn[5], 5);
        });

        btn[6].setOnAction((event) -> {
            KopaShamsuKopa(btn[6], 6);
        });

    }
    
    private static int selectionCtr = 0;
    private static int first;
    private static JFXButton temp;

    private void KopaShamsuKopa(JFXButton btnx, int i) {
        if (selection[i]) {
            btnx.setStyle("-fx-background-color:" + color + "; -fx-background-radius: 35 35 35 35");
            selection[i] = false;
            --selectionCtr;
        } else {
            if (selectionCtr == 0) {
                if (newInstance) {
                    for (int t = 0; t < step; t++) {
                        newInstance = false;
                        btn[t].setStyle("-fx-background-color:" + color + "; -fx-background-radius: 35 35 35 35");;
                    }
                }
                btnx.setStyle("-fx-background-color:" + color + "; -fx-border-color: #00BFA5; -fx-border-width: 3");
                selection[i] = true;
                first = i;
                temp = btnx;

                ++selectionCtr;

            } else {

            }
        }
    }

    public void placeButtonAction(ActionEvent actionEvent) {
    }

    public void attackButtonAction(ActionEvent actionEvent) {
    }

    public void nextTurnButtonAction(ActionEvent actionEvent) {
    }

    //This method will find the shortest path and return it as a string
    

}
