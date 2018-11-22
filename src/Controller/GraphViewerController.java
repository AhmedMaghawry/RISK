/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Agent;
import Model.Continent;
import Model.Country;
import Model.Map;
import com.jfoenix.controls.JFXButton;
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
import java.util.Collections;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

/**
 * FXML Controller class
 *
 * @author void
 */
public class GraphViewerController implements Initializable {

    private static ArrayList<String> vertex = FXMLDocumentController.vertex;
    private List<Continent> allContinents= new ArrayList<>();
    public static Integer[][][] sequence;
    private ArrayList<ArrayList<Integer>> continentsBordersX = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> continentsBordersY= new ArrayList<>();

    public static int step=4;
    private JFXButton[] btn = new JFXButton[7];
//    private static String[] colors = {"#4A148C" , "#00838F", "#2E7D32", "#283593", "#4E342E", "#37474F", "#827717"};
    String color1 ="#4A148C";
    String color2=  "#3090C7";
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


//        pane.setOnMouseClicked(new EventHandler<MouseEvent>() {
//            @Override
//            public void handle(MouseEvent event) {
//                System.out.println(event.getSceneX());
//                System.out.println(event.getSceneY());
//            }
//        });

        final int nodeRadius = 20;
        createBorders();

        Line[][] newEdge = new Line[7][7];
        boolean[][] flag = new boolean[7][7];

         allContinents =  Map.getIntance().getContinents();







        btn[0].setOnAction((event) -> {
           changeButtonClicked(btn[0], 0);
        });

        btn[1].setOnAction((event) -> {
           changeButtonClicked(btn[1], 1);
        });

        btn[2].setOnAction((event) -> {
           changeButtonClicked(btn[2], 2);
        });

        btn[3].setOnAction((event) -> {
           changeButtonClicked(btn[3], 3);
        });

        btn[4].setOnAction((event) -> {
           changeButtonClicked(btn[4], 4);
        });

        btn[5].setOnAction((event) -> {
           changeButtonClicked(btn[5], 5);
        });

        btn[6].setOnAction((event) -> {
           changeButtonClicked(btn[6], 6);
        });

    }
    
    private static int selectionCtr = 0;
    private static int first;
    private static JFXButton temp;

    private void changeButtonClicked(JFXButton btnx, int i) {
        if (selection[i]) {
            btnx.setStyle("-fx-background-radius: 35 35 35 35");
            selection[i] = false;
            --selectionCtr;
        } else {
            if (selectionCtr == 0) {
                if (newInstance) {
                    for (int t = 0; t < step; t++) {
                        newInstance = false;
                        btn[t].setStyle("-fx-background-radius: 35 35 35 35");;
                    }
                }
                btnx.setStyle("-fx-border-width: 3");
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
    private void generateVertices(){
        //Generating Vertices
        for (int i = 0; i < allContinents.size(); i++) {
            for (int j = 0; j < allContinents.get(i).getCountries().size(); j++) {
                btn[j] = new JFXButton();
                Country node = allContinents.get(i).getCountries().get(j);
                btn[j].setText(String.valueOf(node.getNumberArmies()));
                Integer randomNumX = ThreadLocalRandom.current().nextInt(Collections.min(continentsBordersX.get(i)),Collections.max(continentsBordersX.get(i)));
                Integer randomNumY = ThreadLocalRandom.current().nextInt(Collections.min(continentsBordersY.get(i)),Collections.max(continentsBordersY.get(i)));
                btn[i].setLayoutX(randomNumX);
                btn[i].setLayoutY(randomNumY);
                btn[i].setPrefHeight(30);
                btn[i].setPrefWidth(30);
                btn[i].setTextFill(Color.WHITE);
                if(node.getOwner() == Agent.player1)
                    btn[i].setStyle("-fx-background-color: " + color1 + "; -fx-background-radius: 35 35 35 35");
                else
                    btn[i].setStyle("-fx-background-color: " + color2 + "; -fx-background-radius: 35 35 35 35");

            }
        }

    }
    private void createBorders (){
        // North America partion 1

        Integer[] layoutX = new Integer[]{47,318};
        Integer[] layoutY = new Integer[]{391,155};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));


        // Asia partitoin 2
        layoutX = new Integer[]{702,1038};
        layoutY = new Integer[]{116, 326};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

        // Africa partitoin 3
        layoutX = new Integer[]{ 645,537};
        layoutY = new Integer[]{363,510};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

        // South America partion 4
        layoutX = new Integer[]{375,320};
        layoutY = new Integer[]{608,445};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

        // Australia partitoin 5
        layoutX = new Integer[]{ 939,1038};
        layoutY = new Integer[]{612,543};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

        // europe partitoin 6
        layoutX = new Integer[]{ 574,655};
        layoutY = new Integer[]{158,254};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

    }

    public  void generateEdges (){
        //Generating Edges
//
//        for (int i = 0; i < ; i++) {
//            for (int j = 0; j < step; j++) {
//                //Coordinate of starting point
//                Integer x1 = btn[i].getLayoutX() + nodeRadius;
//                Integer y1 = btn[i].getLayoutY() + nodeRadius;
//
//                //Coordinate of ending point
//                Integer x2 = btn[j].getLayoutX() + nodeRadius;
//                Integer y2 = btn[j].getLayoutY() + nodeRadius;
//
//                if (!flag[i][j]) {
//                    newEdge[i][j] = new Line(x1, y1, x2, y2);
//                    Integer mx = (x1 + x2) / 2;
//                    Integer my = (y1 + y2) / 2;
//                    Integer dnX = y2 - y1;
//                    Integer dnY = x1 - x2;
//                    // normalize ortogonal
//                    Integer length = Math.hypot(dnX, dnY);
//                    dnX /= length;
//                    dnY /= length;
//                    Integer factor = 20;
//                    Path path = new Path(new MoveTo(x1, y1), new QuadCurveTo(mx + factor * dnX, my + factor * dnY, x2, y2));
//                    pane.getChildren().add(path);
//
//
//                }
//            }
//        }
    }

}
