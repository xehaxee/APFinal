package com.example.elahefinal;

import com.example.elahefinal.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class loginController {
    Parent root;
    Scene scene;
    Stage stage;
    @FXML
    public Label usernameLabel,passwordLabel,errorLabel;
    @FXML
    public TextField usernameTextField;
    @FXML
    public PasswordField passwordPasswordField;
    public User user;
    public void login(ActionEvent event) throws  IOException{

        String username = usernameTextField.getText();
        String password = passwordPasswordField.getText();
        if(username.isEmpty() || password.isEmpty()){
            errorLabel.setText("Username and password can not be empty!!!!!");
        }else{
            this.user = new User(username,password);
            try{
                boolean result = user.login(this.user);
                if(result){
                    root = FXMLLoader.load(getClass().getResource("View/menuPage.fxml"));
                    stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setTitle("menu");
                    stage.setScene(scene);
                    stage.show();
                }else{
                    errorLabel.setText("we don't have this user in our database!!!");
                }
            }catch (SQLException e){
                errorLabel.setText("wthere is error with database!");
            }
        }
    }
    public void signup(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("View/signupPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("signup");
        stage.setScene(scene);
        stage.show();
    }
}
