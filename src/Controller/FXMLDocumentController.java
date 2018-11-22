/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.Agent;
import Model.AgentType;
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

    @FXML
    private void nextButtonAction(ActionEvent e) throws IOException{
      //  value = vertex.getValue();
        if (selectedFile != null) {
            String agent1Type = Agent1.getSelectionModel().getSelectedItem().toString();
            System.out.println(agent1Type);
            String agent2Type = Agent2.getSelectionModel().getSelectedItem().toString();
            System.out.println(agent2Type);
            Agent.player1.setType( AgentType.valueOf(agent1Type));
            Agent.player2.setType( AgentType.valueOf(agent2Type));
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
             InputReader.getIntance().addFile(selectedFile);
             InputReader.getIntance().readInput();
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
        Agent1.setValue("Human");
        Agent2.setValue("Completely_Passive");

    }
    
}
