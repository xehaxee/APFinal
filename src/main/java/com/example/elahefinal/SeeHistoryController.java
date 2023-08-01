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
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class SeeHistoryController implements Initializable {
    @FXML
    ListView<String> historyList;
    Parent root;
    Stage stage;
    Scene scene;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ObservableList<String> items = FXCollections.observableArrayList();
        DB db = new DB();
        try{
            ResultSet rs = db.requestSelect("SELECT * FROM record WHERE username='"+ User.loginUser.getUsername()+"';");
            while (rs.next()){
                items.add("User: "+rs.getString(1)+"\nDate: "+rs.getString(2)+"\nPoint: "+rs.getInt(3));
            }
            historyList.setItems(items);
        }catch (SQLException e){

        }
    }
    public void backToMenu(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("View/menuPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("menu");
        stage.setScene(scene);
        stage.show();
    }
}
