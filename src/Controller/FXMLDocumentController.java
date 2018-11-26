/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.*;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXRadioButton;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleButton;

import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;


import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 *
 * @author void
 */


public class FXMLDocumentController implements Initializable {

    public static int value = 1;

    public  static  int vertexNum ;
    
    public static ArrayList <String>vertex = new ArrayList<>();

    public  File selectedFile;
    public ImageView backgroundImage;
    public StackPane stackPane;
    @FXML
    private ToggleButton toggle ;
    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton FileButton;
    @FXML
    private AnchorPane rootPane;

    @FXML
    private ImageView img;


    @FXML
    private JFXComboBox<String> Agent1;

    @FXML
    private JFXComboBox<String> Agent2;
    @FXML
    private FileChooser fileChooser = new FileChooser();

    private  String agent1Type;
    private  String agent2Type;
    private Boolean isFullscreen = false;
    private  Stage stage;
    @FXML
    private void nextButtonAction(ActionEvent e) throws IOException{
      //  value = vertex.getValue();
        if (selectedFile != null) {
             agent1Type = Agent1.getSelectionModel().getSelectedItem().toString();
             agent2Type = Agent2.getSelectionModel().getSelectedItem().toString();
            determinePlayersType(agent1Type,agent2Type);
            InputReader.getIntance().addFile(selectedFile);
            InputReader.getIntance().readInput();
            if (agent1Type.equals("A_Search"))
                ((AStarAgent)Agent.player1).setCurrentState(NState.globalState);
            if (agent2Type.equals("A_Search"))
                ((AStarAgent)Agent.player2).setCurrentState(NState.globalState);
            if (agent1Type.equals("A_Real"))
            	((AStarAgent)Agent.player1).setCurrentState(NState.globalState);
            if (agent2Type.equals("A_Real"))
            	((AStarAgent)Agent.player2).setCurrentState(NState.globalState);
            if (agent1Type.equals("Greedy"))
                ((GreedyAgent)Agent.player1).setCurrentState(NState.globalState);
            if (agent2Type.equals("Greedy"))
                ((GreedyAgent)Agent.player2).setCurrentState(NState.globalState);
            System.out.println(Agent.player1.getBounceValue());
            AnchorPane temp = FXMLLoader.load(getClass().getResource("/View/GraphViewer.fxml"));
            rootPane.getChildren().setAll(temp);
        }else {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Information Alert");
            String s ="Please select a valid input file before you play!";
            alert.setContentText(s);
            alert.show();
        }
    }
    @FXML
    private void FileButtonAction(ActionEvent e) throws IOException{
        //  value = vertex.getValue();
        AnchorPane temp = null;
         selectedFile = fileChooser.showOpenDialog(null);
            if (selectedFile != null) {
            System.out.println("yes!!!!");
            FileButton.setText(selectedFile.getName());
            }
    else {
                System.out.println("NO!!!!");
        }
       // rootPane.getChildren().setAll(temp);


    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ObservableList<String> list1 = FXCollections.observableArrayList(    "Human", "Completely_Passive", "Aggressive", "Nearly_Pacifist", "Greedy", "A_Search", "A_Real");
        ObservableList<String> list2 = FXCollections.observableArrayList("Completely_Passive", "Aggressive", "Nearly_Pacifist", "Greedy", "A_Search", "A_Real");
        Agent1.setItems(list1);
        Agent2.setItems(list2);

        //  inputType.selectToggle(matrixInput);
        Agent1.setValue("Nearly_Pacifist");
        Agent2.setValue("Completely_Passive");
//        toggle.getStylesheets().add(this.getClass().getResource(
//                "/View/main.css"
//        ).toExternalForm());



    }

    public void determinePlayersType(String playerType1,String playerType2){
        switch (playerType1){
            case "Human":
                Agent.player1 = new HumanAgent();
                break;
            case "Completely_Passive":
                Agent.player1 = new CompletelyPassiveAgent();
                break;
            case "Aggressive":
                Agent.player1 = new AggressiveAgent();
                break;
            case "Nearly_Pacifist":
                Agent.player1 = new NearlyPacifistAgent();
                break;
            case "A_Search":
                Agent.player1 = new AStarAgent(InputReader.getIntance().getVertices(),Integer.MAX_VALUE);
                break;
            case "A_Real":
                Agent.player1 = new AStarAgent(InputReader.getIntance().getVertices(),3);
                break;
            case "Greedy":
                Agent.player1 = new GreedyAgent(InputReader.getIntance().getVertices());
                break;
        }
        switch (playerType2){
            case "Completely_Passive":
                Agent.player2 = new CompletelyPassiveAgent();
                break;
            case "Aggressive":
                Agent.player2 = new AggressiveAgent();
                break;
            case "Nearly_Pacifist":
                Agent.player2 = new NearlyPacifistAgent();
                break;
            case "A_Search":
                Agent.player2 = new AStarAgent(InputReader.getIntance().getVertices(),Integer.MAX_VALUE);
                break;
            case "A_Real":
                Agent.player2 = new AStarAgent(InputReader.getIntance().getVertices(),3);
                break;
            case "Greedy":
                Agent.player2 = new GreedyAgent(InputReader.getIntance().getVertices());
                break;
        }
    }
    @FXML
    public void toggleAction (ActionEvent e){
        isFullscreen = !isFullscreen;
        if(isFullscreen){
            stage = (Stage) rootPane.getScene().getWindow();
            stage.setFullScreen(true);
            stage.setResizable(true);
            stage.show();
        }
        else {
             stage = (Stage) rootPane.getScene().getWindow();
            stage.setFullScreen(false);
            stage.setResizable(true);
            stage.show();

        }

    }

}
