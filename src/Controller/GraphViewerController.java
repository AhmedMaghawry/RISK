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
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.stage.Window;

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
    public Label Player2Turn;
    public Label Player1Turn;
    public JFXButton placeButton;
    public JFXButton attackButton;
    public JFXButton nextTurnButton;
    private  Scene startscene;
    private ArrayList<ArrayList<Integer>> continentsBordersX = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> continentsBordersY= new ArrayList<>();

    private ArrayList<Integer> countryBordersX = new ArrayList<>();
    private ArrayList<Integer> countryBordersY= new ArrayList<>();

    private int verticesNum = InputReader.getIntance().getVertices().size();
    private boolean doeshaveedges [][];
    private List<Country> adjCountries = new ArrayList<>();
    public static int step=4;
    private Line[][] newEdge = new Line[verticesNum][verticesNum];
    public boolean[] selection = new boolean[verticesNum];
    private static int selectionCtr = 0;
    private static int first;
    private boolean newInstance = false;
    private boolean isplayer1Turn = false;
    private boolean isplayer2Turn = false;
    private ArrayList<Path> allPaths= new ArrayList<>();
    private JFXButton []allbtns = new JFXButton[verticesNum];

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
    void startOverButtonAction(ActionEvent event) throws IOException {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation of Quit");
        alert.setHeaderText("Confirmation Message");
        alert.setContentText("Are you sure want to quit?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            clearPane();
            AnchorPane temp = FXMLLoader.load(getClass().getResource("/View/FXMLDocument.fxml"));
            pane.getChildren().setAll(temp);
        } else {
           alert.close();
        }

    }
    final int nodeRadius = 20;


    public void generateVertices(){
        //Generating Vertices
        int m=0;
        doeshaveedges  = new boolean[verticesNum][verticesNum];
        for (int i = 0; i < Map.getIntance().getContinents().size(); i++) {
            for (int j = 0; j < Map.getIntance().getContinents().get(i).getCountries().size(); j++) {
                allbtns[m]= new JFXButton();
                Country node =Map.getIntance().getContinents().get(i).getCountries().get(j);
                adjCountries = node.getAdj();
                for(int k = 0; k< adjCountries.size(); k++){
                    doeshaveedges[node.getCountryId()-1][adjCountries.get(k).getCountryId()-1] = true;
                }
                allbtns[m].setText(String.valueOf(node.getNumberArmies()));
                Integer randomNumX = ThreadLocalRandom.current().nextInt(Collections.min(continentsBordersX.get(i)),Collections.max(continentsBordersX.get(i)));
                Integer randomNumY = ThreadLocalRandom.current().nextInt(Collections.min(continentsBordersY.get(i)),Collections.max(continentsBordersY.get(i)));
                countryBordersX.add(randomNumX);
                countryBordersY.add(randomNumY);
                allbtns[m].setLayoutX(randomNumX);
                allbtns[m].setLayoutY(randomNumY);
                allbtns[m].setPrefHeight(35);
                allbtns[m].setPrefWidth(35);
                allbtns[m].setTextFill(Color.BLACK);
                if(node.getOwner() == Agent.player1)
                    allbtns[m].setStyle("-fx-background-color: " + color1 + ";");
                else
                    allbtns[m].setStyle("-fx-background-color: " + color2 + ";");
                allbtns[m].setVisible(true);
                pane.getChildren().add(allbtns[m]);
                m++;
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
                Double x1 = allbtns[i].getLayoutX() + nodeRadius*1.0;
                Double y1 = allbtns[i].getLayoutY() + nodeRadius*1.0;

                //Coordinate of ending point
                Double x2 = allbtns[j].getLayoutX() + nodeRadius*1.0;
                Double y2 = allbtns[j].getLayoutY() + nodeRadius*1.0;

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
                    allPaths.add(path);
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
        placeButton.setDisable(false);
        attackButton.setDisable(true);
        nextTurnButton.setDisable(true);
        isplayer1Turn = true;
        Player1Turn.setDisable(false);
        Player2Turn.setDisable(true);
//        startscene = pane.getScene();
//        startStage = (Stage) startscene.getWindow();
//        while ( i<verticesNum){
//            final int tmp = i;
//            allbtns[m.setOnMouseClicked((event) -> {
//                changeButtonClicked(allbtns[mp),tmp);
//            });
//            i++;
//        }

    }

    public void placeButtonAction(ActionEvent actionEvent) {
        if(isplayer1Turn){
            Agent.player1.place();
            updatePane();
            Player1Turn.setDisable(true);
            Player2Turn.setDisable(false);
            isplayer1Turn =false;

        }
        if(isplayer2Turn){
            Agent.player2.place();
            updatePane();
            Player1Turn.setDisable(false);
            Player2Turn.setDisable(true);
            isplayer2Turn = false;
        }
        placeButton.setDisable(true);
        attackButton.setDisable(false);
    }

    public void attackButtonAction(ActionEvent actionEvent) {
        if(isplayer1Turn){
            Agent.player1.attack();
            updatePane();
            Player1Turn.setDisable(true);
            Player2Turn.setDisable(false);
            isplayer1Turn =false;
        }
        if(isplayer2Turn){
            Agent.player2.place();
            updatePane();
            Player1Turn.setDisable(false);
            Player2Turn.setDisable(true);
            isplayer2Turn = false;
        }
        placeButton.setDisable(true);
        attackButton.setDisable(true);
        nextTurnButton.setDisable(false);
    }

    public void nextTurnButtonAction(ActionEvent actionEvent) {
        placeButton.setDisable(false);
        nextTurnButton.setDisable(true);
        isplayer2Turn = !isplayer2Turn;
        isplayer1Turn = !isplayer1Turn;
        if(isplayer1Turn){
            Player1Turn.setDisable(false);
            Player2Turn.setDisable(true);
        }
        if(isplayer1Turn){
            Player1Turn.setDisable(true);
            Player2Turn.setDisable(false);
        }
    }
    public void updatePane() {
        int m=0;
        hideCountries();
        for (int i = 0; i < Map.getIntance().getContinents().size(); i++) {
            for (int j = 0; j < Map.getIntance().getContinents().get(i).getCountries().size(); j++) {
                Country node =Map.getIntance().getContinents().get(i).getCountries().get(j);
                allbtns[m]= new JFXButton();
                allbtns[m].setText(String.valueOf(node.getNumberArmies()));
                allbtns[m].setLayoutX(countryBordersX.get(m));
                allbtns[m].setLayoutY(countryBordersY.get(m));
                allbtns[m].setPrefHeight(35);
                allbtns[m].setPrefWidth(35);
                allbtns[m].setTextFill(Color.BLACK);
                if(node.getOwner() == Agent.player1)
                    allbtns[m].setStyle("-fx-background-color: " + color1 + ";");
                else
                    allbtns[m].setStyle("-fx-background-color: " + color2 + ";");
                allbtns[m].setVisible(true);
                pane.getChildren().add(allbtns[m]);
                m++;
            }
        }
        pane.requestLayout();
    }
public void hideCountries(){
        for (int i=0;i<verticesNum;i++){
            allbtns[i].setVisible(false);
        }
    allbtns = new JFXButton[verticesNum];

}
    public void hideEdges(){
        for (int i=0;i<allPaths.size();i++){
            allPaths.get(i).setVisible(false);
        }
    allPaths.clear();
    }
   public void clearPane (){
        hideCountries();
       hideEdges();
        pane.requestLayout();
   }
}
