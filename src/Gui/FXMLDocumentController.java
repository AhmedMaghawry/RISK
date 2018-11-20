/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Gui;

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
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 *
 * @author void
 */


public class FXMLDocumentController implements Initializable {
    
    public static int value = 1;
    



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
        AnchorPane temp = null;

        rootPane.getChildren().setAll(temp);
    }
    @FXML
    private void FileButtonAction(ActionEvent e) throws IOException{
        //  value = vertex.getValue();
        AnchorPane temp = null;
        File selectedFile = fileChooser.showOpenDialog(null);
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
        ObservableList<String> list = FXCollections.observableArrayList("Human","Passive","Aggressive","Pacifist","Intelligent");
        Agent1.setItems(list);
        Agent2.setItems(list);

        //  inputType.selectToggle(matrixInput);
        Agent1.setValue("Human");
        Agent2.setValue("Passive");

    }
    
}
