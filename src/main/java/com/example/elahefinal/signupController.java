package com.example.elahefinal;

import com.example.elahefinal.User.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class signupController {
    Parent root;
    Stage stage;
    Scene scene;
    @FXML
    Label usernameLabel,fullNameLabel,passwordLabel,errorLabel;
    @FXML
    TextField usernameField,fullNameField;
    @FXML
    PasswordField passwordField;
    @FXML
    Button signupButton,backtologinButton;

    public void backToLogin(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("View/loginPage.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("login");
        stage.setScene(scene);
        stage.show();
    }
    public void signup(ActionEvent event){
        String username = usernameField.getText();
        String password = passwordField.getText();
        String fullName = fullNameField.getText();
        if(username.isEmpty() || password.isEmpty() || fullName.isEmpty()){
            errorLabel.setText("input values can not be empty!!");
        }else{
            User user = new User(fullName,username,password);
            boolean signup = user.signup(user);
            if(signup){
                errorLabel.setText("successfuly signup!");
            }else{
                errorLabel.setText("signup failed!");
            }
        }
    }
}
