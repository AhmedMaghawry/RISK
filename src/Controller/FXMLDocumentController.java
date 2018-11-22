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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

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
    @FXML
    private JFXButton nextButton;

    @FXML
    private JFXButton FileButton;
    @FXML
    private AnchorPane rootPane;
    
    @FXML
    private ToggleGroup inputType;

    @FXML
    private JFXComboBox<String> Agent1;

    @FXML
    private JFXComboBox<String> Agent2;
    @FXML
    private FileChooser fileChooser = new FileChooser();

    private  String agent1Type;
    private  String agent2Type;


    @FXML
    private void nextButtonAction(ActionEvent e) throws IOException{
      //  value = vertex.getValue();
        if (selectedFile != null) {
             agent1Type = Agent1.getSelectionModel().getSelectedItem().toString();
             agent2Type = Agent2.getSelectionModel().getSelectedItem().toString();
           determinePlayersType(agent1Type,agent2Type);
       System.out.println(Agent.player1.getBounceValue());
            InputReader.getIntance().addFile(selectedFile);
            InputReader.getIntance().readInput();
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
        Agent1.setValue("Aggressive");
        Agent2.setValue("Completely_Passive");

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
        }
    }
}
