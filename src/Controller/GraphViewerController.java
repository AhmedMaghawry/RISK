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
    private ArrayList<ArrayList<Integer>> continentsBordersX = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> continentsBordersY= new ArrayList<>();
    private int verticesNum = InputReader.getIntance().getVertices().size();
    private boolean doeshaveedges [][];
    private List<Country> adjCountries = new ArrayList<>();
    public static int step=4;
    private JFXButton btn ;
    private Line[][] newEdge = new Line[verticesNum][verticesNum];
    public boolean[] selection = new boolean[verticesNum];
    private static int selectionCtr = 0;
    private static int first;
    private static JFXButton temp;
    private boolean newInstance = false;
    private ArrayList<JFXButton> allbtns = new ArrayList<>();
//    private static String[] colors = {"#4A148C" , "#00838F", "#2E7D32", "#283593", "#4E342E", "#37474F", "#827717"};
    String color1 ="#4A148C";
    String color2=  "#3090C7";
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
    final int nodeRadius = 20;


    public void generateVertices(){
        //Generating Vertices
        doeshaveedges  = new boolean[verticesNum][verticesNum];
        for (int i = 0; i < Map.getIntance().getContinents().size(); i++) {
            for (int j = 0; j < Map.getIntance().getContinents().get(i).getCountries().size(); j++) {
                btn = new JFXButton();
                Country node =Map.getIntance().getContinents().get(i).getCountries().get(j);
                adjCountries = node.getAdj();
                for(int k = 0; k< adjCountries.size(); k++){
                    doeshaveedges[node.getCountryId()-1][adjCountries.get(k).getCountryId()-1] = true;
                }
                btn.setText(String.valueOf(node.getNumberArmies()));
                Integer randomNumX = ThreadLocalRandom.current().nextInt(Collections.min(continentsBordersX.get(i)),Collections.max(continentsBordersX.get(i)));
                Integer randomNumY = ThreadLocalRandom.current().nextInt(Collections.min(continentsBordersY.get(i)),Collections.max(continentsBordersY.get(i)));
                btn.setLayoutX(randomNumX);
                btn.setLayoutY(randomNumY);
                btn.setPrefHeight(30);
                btn.setPrefWidth(30);
                btn.setTextFill(Color.WHITE);
                if(node.getOwner() == Agent.player1)
                    btn.setStyle("-fx-background-color: " + color1 + "; -fx-background-radius: 35 35 35 35");
                else
                    btn.setStyle("-fx-background-color: " + color2 + "; -fx-background-radius: 35 35 35 35");
                btn.setVisible(true);
                allbtns.add(btn);
                pane.getChildren().add(btn);

            }
        }


    }
    public void createBorders (){
        // North America partion 1

        Integer[] layoutX = new Integer[]{107,318};
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

        for (int i = 0; i < verticesNum; i++) {
            for (int j = i; j < verticesNum; j++) {
                //Coordinate of starting point
                Double x1 = allbtns.get(i).getLayoutX() + nodeRadius*1.0;
                Double y1 = allbtns.get(i).getLayoutY() + nodeRadius*1.0;

                //Coordinate of ending point
                Double x2 = allbtns.get(j).getLayoutX() + nodeRadius*1.0;
                Double y2 = allbtns.get(j).getLayoutY() + nodeRadius*1.0;

                if (doeshaveedges[i][j]) {
                   newEdge[i][j] = new Line(x1, y1, x2, y2);
                    Double mx = (x1 + x2) / 2;
                    Double my = (y1 + y2) / 2;
                    Double dnX = y2 - y1;
                    Double dnY = x1 - x2;
                    // normalize ortogonal
                    Double length = Math.hypot(dnX, dnY);
                    dnX /= length;
                    dnY /= length;
                    Integer factor = 20;
                    Path path = new Path(new MoveTo(x1, y1), new QuadCurveTo(mx + factor * dnX, my + factor * dnY, x2, y2));
                    pane.getChildren().add(path);

                }
            }
        }
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

        createBorders();
        generateVertices();
        generateEdges();
        int i =0;
//        while ( i<verticesNum){
//            final int tmp = i;
//            allbtns.get(i).setOnMouseClicked((event) -> {
//                changeButtonClicked(allbtns.get(tmp),tmp);
//            });
//            i++;
//        }

    }
//    private void changeButtonClicked(JFXButton btnx, int i) {
//
//
//    }

    public void placeButtonAction(ActionEvent actionEvent) {
    }

    public void attackButtonAction(ActionEvent actionEvent) {
    }

    public void nextTurnButtonAction(ActionEvent actionEvent) {
    }


}
