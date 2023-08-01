package com.example.elahefinal;

import com.example.elahefinal.DB.DB;
import com.example.elahefinal.User.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class menuController implements Initializable {
    Parent root;
    Stage stage;
    Scene scene;
    @FXML
    Label welcomeLabel,errorLabel;
    @FXML
    TextField numberField;
    @FXML
    ColorPicker colorPicker;
    @FXML
    ChoiceBox<String> speedBox,levelBox;
    public static String color,speed,level;
    public static int numberOfBot;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        DB db = new DB();
        try{
            ResultSet rs = db.requestSelect("SELECT username, MAX( `point`) FROM record where username='"+User.loginUser.getUsername()+"' group by username ;");
            if(rs.next()){
                welcomeLabel.setText("welcome "+ User.loginUser.getFullName()+"your MAX score: "+rs.getInt(2));
            }else{
                welcomeLabel.setText("welcome "+ User.loginUser.getFullName()+"your MAX score: 0");
            }
        }catch (SQLException e){
            welcomeLabel.setText("welcome "+ User.loginUser.getFullName()+"your MAX score: 0");
        }

        ObservableList<String> speedItems = FXCollections.observableArrayList("slow", "normal", "fast");
        ObservableList<String> levelItem = FXCollections.observableArrayList("easy", "normal", "hard");
        speedBox.setItems(speedItems);
        levelBox.setItems(levelItem);

    }
    public void logout(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("View/loginPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("login");
        stage.setScene(scene);
        stage.show();
    }
    public void seeHistory(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("View/seeHistoryPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("history");
        stage.setScene(scene);
        stage.show();
    }
    public void start(ActionEvent event) throws IOException{
        String numberOfBot = numberField.getText();
        String color = colorPicker.getValue().toString();
        String speed = speedBox.getValue();
        String level = levelBox.getValue();
        if(!numberOfBot.isEmpty() && !color.isEmpty()&& !speed.isEmpty() && !level.isEmpty()){
            try{
                int numberOfbotInt = Integer.valueOf(numberOfBot);
                color = "#"+color.substring(2,8);
                this.color = color;
                this.numberOfBot = numberOfbotInt;
                menuController.speed = speed;
                menuController.level = level;
                root = FXMLLoader.load(getClass().getResource("View/gamePage.fxml"));
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                scene = new Scene(root);
                stage.setTitle("game");
                stage.setScene(scene);
                stage.show();
            }catch (NumberFormatException e){
                errorLabel.setText("number must be integer.");
            }
        }else{
            errorLabel.setText("boxes cant be empty!!");
        }
    }
}
