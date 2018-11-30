/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import Model.Map;
import com.jfoenix.controls.JFXButton;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.Collections;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.System.exit;

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
    private boolean isPlaceMove =false;
    private boolean isAttackMove =false;
    private  Stage startStage;

    @FXML
    private ToggleButton toggle ;
    private ArrayList<ArrayList<Integer>> continentsBordersX = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> continentsBordersY= new ArrayList<>();

    private ArrayList<Integer> countryBordersX = new ArrayList<>();
    private ArrayList<Integer> countryBordersY= new ArrayList<>();
    private Media sound = new Media(new File("out/battle-music.mp3").toURI().toString());

//    private MediaPlayer mediaPlayer = new MediaPlayer(sound);
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
    private Boolean isFullscreen = false;
    private  Stage stage;
    private String color1 ="#4A148C";
    private String color2=  "#3090C7";
    private Media adavnceSound = new Media(new File("out/advance-shout.wav").toURI().toString());
    private Media fireSound = new Media(new File("out/fire-shout.wav").toURI().toString());
    private boolean isHuman = false;
    private  ArrayList<Integer> player1Indices = new ArrayList<>();
    private  ArrayList<Integer> player2Indices = new ArrayList<>();
    private  HashMap<String,Integer> buttonsIDs = new HashMap<>(); //key is button index value is countryID
    private boolean isValidMove = true;
    private String placeButtonID=null;
    private String attackFromButtonID=null;
    private String attacktoButtonID=null;

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
            exit(0);
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
                allbtns[m].setId(String.valueOf(m+1));
                Country node =Map.getIntance().getContinents().get(i).getCountries().get(j);
                adjCountries = node.getAdj();
                for(int k = 0; k< adjCountries.size(); k++){
                    doeshaveedges[node.getCountryId()-1][adjCountries.get(k).getCountryId()-1] = true;
                }
                allbtns[m].setStyle("-fx-background-radius: 50 50 50 50");
                allbtns[m].setText(String.valueOf(node.getNumberArmies()));
                Integer randomNumX = ThreadLocalRandom.current().nextInt(Collections.min(continentsBordersX.get(i)),Collections.max(continentsBordersX.get(i)));
                Integer randomNumY = ThreadLocalRandom.current().nextInt(Collections.min(continentsBordersY.get(i)),Collections.max(continentsBordersY.get(i)));
                countryBordersX.add(randomNumX);
                countryBordersY.add(randomNumY);
                allbtns[m].setLayoutX(randomNumX);
                allbtns[m].setLayoutY(randomNumY);
                allbtns[m].setPrefHeight(30);
                allbtns[m].setPrefWidth(30);
                allbtns[m].setTextFill(Color.BLACK);
                if(node.getOwner() == Agent.player1){
                    allbtns[m].setStyle("-fx-background-color: " + color1 + ";");
                    player1Indices.add(m);
                    allbtns[m].setOnAction(player1Handler);

                }
                else{
                    allbtns[m].setStyle("-fx-background-color: " + color2 + ";");
                    player2Indices.add(m);
                    allbtns[m].setOnAction(player2Handler);
                }
                buttonsIDs.put(allbtns[m].getId(),node.getCountryId());
                allbtns[m].setVisible(true);
                pane.getChildren().add(allbtns[m]);
                m++;
            }
        }


    }
    public void createBorders (){
        // North America partion 1

        Integer[] layoutX = new Integer[]{190,380};
        Integer[] layoutY = new Integer[]{330,175};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));


        // Asia partitoin 2
        layoutX = new Integer[]{820,1130};
        layoutY = new Integer[]{130, 345};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

        // Africa partitoin 3
        layoutX = new Integer[]{ 610,800};
        layoutY = new Integer[]{335,545};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

        // South America partion 4
        layoutX = new Integer[]{365,465};
        layoutY = new Integer[]{680,480};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

        // Australia partitoin 5
        layoutX = new Integer[]{ 1090,1200};
        layoutY = new Integer[]{582,662};
        continentsBordersX.add(new ArrayList<>(Arrays.asList(layoutX)));
        continentsBordersY.add(new ArrayList<>(Arrays.asList(layoutY)));

        // europe partitoin 6
        layoutX = new Integer[]{ 670,780};
        layoutY = new Integer[]{124,285};
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
        //  playBackMusic();
        createBorders();
        generateVertices();
        generateEdges();
        placeButton.setDisable(false);
        attackButton.setDisable(true);
        nextTurnButton.setDisable(true);
        isplayer1Turn = true;
        Player1Turn.setDisable(false);
        Player2Turn.setDisable(true);
        if(Agent.player1.getType().equals(AgentType.Human))
            isHuman = true;
//        toggle.getStylesheets().add(this.getClass().getResource(
//                "/View/main.css"
//        ).toExternalForm());
//        while ( i<verticesNum){
//            final int tmp = i;
//            allbtns[m.setOnMouseClicked((event) -> {
//                changeButtonClicked(allbtns[mp),tmp);
//            });
//            i++;
//        }

    }

    public EventHandler<ActionEvent> player1Handler = new EventHandler<ActionEvent>(){

        public void handle(final ActionEvent event) {
            Object source = event.getSource();
            if (source instanceof JFXButton) {
                if (isHuman && isplayer1Turn ) {
                    if(isPlaceMove) {
                        if(placeButtonID ==null){
                            System.out.println("placed before");
//                            ((JFXButton) source).setStyle("-fx-background-color: yellow");
                            placeButtonID = ((JFXButton) source).getId();
                            ((HumanAgent)Agent.player1).place_for_human(getCountrybyID(Integer.parseInt(placeButtonID)));
                            System.out.println("placed after");
                            updatePane();
                            placeButtonID = null;
                        }
                    }
                    if(isAttackMove&& attackFromButtonID==null) {
//                        ((JFXButton) source).setStyle("-fx-background-color: yellow");
                        attackFromButtonID = ((JFXButton) source).getId();
                    }
                }
            }
        }
    };
    public EventHandler<ActionEvent> player2Handler = new EventHandler<ActionEvent>(){

        public void handle(final ActionEvent event) {
            Object source = event.getSource();
            if (source instanceof JFXButton) {
                if (isHuman && isplayer1Turn ) {
                    if(isAttackMove) {
                        if( attackFromButtonID!=null && attacktoButtonID==null) {
//                            ((JFXButton) source).setStyle("-fx-background-color: Yellow");
                            attacktoButtonID = ((JFXButton) source).getId();
                            Country countryFrom = getCountrybyID(Integer.parseInt(attackFromButtonID));
                            Country countryTo = getCountrybyID(Integer.parseInt(attacktoButtonID));
                            isValidMove = ((HumanAgent)Agent.player1).attack_for_human(countryFrom,countryTo);
                            if(isValidMove)
                                updatePane();
                            else{
                                showupPopupMessage("Invalid action please try again!");
                            }
                            attackFromButtonID=null;
                            attacktoButtonID = null;
                        }
                    }
                }
            }
        }
    };
    public void placeButtonAction(ActionEvent actionEvent) {
        MediaPlayer advancePlayer = new MediaPlayer(adavnceSound);
        advancePlayer.play();
        int countryid =0;
        if(isplayer1Turn){
            isPlaceMove = true;
            if(isHuman){
                showupPopupMessage("Pick a country of yours to advance bonus armies to!");
                disableCountryButtons(Agent.player2);
            }
            else {
                Agent.player1.place();
                updatePane();
            }
            //Player1Turn.setDisable(true);
            //Player2Turn.setDisable(fsalse);
            //isplayer1Turn =false;
        }
        if(isplayer2Turn){
            Agent.player2.place();
            updatePane();
            //Player1Turn.setDisable(false);
            //Player2Turn.setDisable(true);
            //isplayer2Turn = false;
        }
        placeButton.setDisable(true);
        PauseTransition pause = new PauseTransition(
                Duration.seconds(2)
        );
        pause.setOnFinished(event -> {
            //    playBackMusic();
            attackButton.setDisable(false);
        });
        pause.play();


    }

    public void attackButtonAction(ActionEvent actionEvent) {


        MediaPlayer firePlayer = new MediaPlayer(fireSound);
        firePlayer.play();
        if(isplayer1Turn){
            if(isHuman){
                isPlaceMove = false;
                isAttackMove = true;
                showupPopupMessage("1)Pick a country of yours to attack!\n"+"2)Pick an opponent country to be attacked!");
                //disableCountryButtons(Agent.player2);
            }else {
                Agent.player1.attack();

                updatePane();
            }
            if (Agent.player2.getNumberOfArmies() == 0) {
                try {
                    win("Player 1");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Player1Turn.setDisable(true);
            //Player2Turn.setDisable(false);
            //isplayer1Turn =false;
        }
        if(isplayer2Turn){
            Agent.player2.attack();
            updatePane();
            if (Agent.player1.getNumberOfArmies() == 0) {
                try {
                    win("Player 2");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //Player1Turn.setDisable(false);
            //Player2Turn.setDisable(true);
            //isplayer2Turn = false;
        }
        attackButton.setDisable(true);
        PauseTransition pause = new PauseTransition(
                Duration.seconds(2)
        );
        pause.setOnFinished(event -> {
            nextTurnButton.setDisable(false);
            //   playBackMusic();
        });
        pause.play();

    }

    private void win(String player) throws IOException {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Winner");
        alert.setHeaderText("Winner");
        alert.setContentText("Congratulations " + player + " Wins");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            exit(0);
        }
    }

    public void nextTurnButtonAction(ActionEvent actionEvent) {
        placeButton.setDisable(false);
        nextTurnButton.setDisable(true);
        isplayer2Turn = !isplayer2Turn;
        isplayer1Turn = !isplayer1Turn;
        if(isplayer1Turn){
            if(isHuman){
                isAttackMove=false;
                isPlaceMove=true;
            }
            Player1Turn.setDisable(false);
            Player2Turn.setDisable(true);
        }
        if(isplayer2Turn){
            Player1Turn.setDisable(true);
            Player2Turn.setDisable(false);
        }
    }
    public void updatePane() {
        int m=0;
        hideCountries();
        player1Indices.clear();
        player2Indices.clear();
        for (int i = 0; i < Map.getIntance().getContinents().size(); i++) {
            for (int j = 0; j < Map.getIntance().getContinents().get(i).getCountries().size(); j++) {
                Country node =Map.getIntance().getContinents().get(i).getCountries().get(j);
                allbtns[m]= new JFXButton();
                allbtns[m].setId(String.valueOf(m+1));
                allbtns[m].setText(String.valueOf(node.getNumberArmies()));
                allbtns[m].setLayoutX(countryBordersX.get(m));
                allbtns[m].setLayoutY(countryBordersY.get(m));
                allbtns[m].setPrefHeight(30);
                allbtns[m].setPrefWidth(30);
                allbtns[m].setTextFill(Color.BLACK);
                allbtns[m].setStyle("-fx-background-radius: 30 30 30 30");
                if(node.getOwner() == Agent.player1) {
                    allbtns[m].setStyle("-fx-background-color: " + color1 + ";");
                    allbtns[m].setOnAction(player1Handler);
                    player1Indices.add(m);
                    System.out.println("player1 index "+m);

                }else{
                    allbtns[m].setStyle("-fx-background-color: " + color2 + ";");
                    allbtns[m].setOnAction(player2Handler);
                    player2Indices.add(m);
                    System.out.println("player2 index "+m);


                }
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
    @FXML
    public void toggleAction (ActionEvent e){
        isFullscreen = !isFullscreen;
        if(isFullscreen){
            stage = (Stage) pane.getScene().getWindow();
            stage.setFullScreen(true);
            stage.setResizable(true);
            stage.show();
        }
        else {
            stage = (Stage) pane.getScene().getWindow();
            stage.setFullScreen(false);
            stage.setResizable(true);
            stage.show();

        }
    }
  /*  void playBackMusic(){
        mediaPlayer.setOnEndOfMedia(new Runnable() {
            public void run() {
                mediaPlayer.seek(Duration.ZERO);
            }
        });
        mediaPlayer.play();
    }*/
    void showupPopupMessage(String message){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Information Alert");
        alert.setContentText(message);
        alert.show();
    }
    void disableCountryButtons (Agent agent){
        if(agent == Agent.player1){
            for(int i=0;i<player1Indices.size();i++){
                allbtns[player1Indices.get(i)].setDisable(true);
            }
        }else {
            for(int i=0;i<player2Indices.size();i++){
                allbtns[player2Indices.get(i)].setDisable(true);
            }
        }
    }
    void enableCountryButtons (){
        for(int i=0;i<allbtns.length;i++){
            allbtns[i].setDisable(false);
        }
    }
    Country getCountrybyID (int id){
        for (int i = 0; i < Map.getIntance().getContinents().size(); i++) {
            for (int j = 0; j < Map.getIntance().getContinents().get(i).getCountries().size(); j++) {
                Country node =Map.getIntance().getContinents().get(i).getCountries().get(j);
                if(node.getCountryId()==id){
                    return node;
                }
            }
        }
        return null;
    }

}