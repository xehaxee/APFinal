package com.example.elahefinal;


import com.example.elahefinal.DB.DB;
import com.example.elahefinal.Game.Game;
import com.example.elahefinal.Game.Node;
import com.example.elahefinal.Node.PlayerABS;
import com.example.elahefinal.User.User;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.ResourceBundle;

public class GameController implements Initializable {
    @FXML
    AnchorPane rootPane;
    @FXML
    GridPane gameGrid;
    @FXML
    Label messageLabel;

    //1 -> up ; 2 -> right ; 3-> down ; 4-> left
    private int playerMove;
    //1-> gun B 2-> gun A
    private int shootKind = 3;
    //the more the speed become its delay is less
    private int speed;
    private Game game;
    private Node[][] board;
    public Parent root;
    public Stage stage;
    public Scene scene;
    private String makeColorBrighter(String colorString, double brightnessFactor) {
        Color color = Color.web(colorString);
        double r = clamp(color.getRed() + brightnessFactor, 0, 1);
        double g = clamp(color.getGreen() + brightnessFactor, 0, 1);
        double b = clamp(color.getBlue() + brightnessFactor, 0, 1);
        return String.format("#%02X%02X%02X",
                (int) (r * 255),
                (int) (g * 255),
                (int) (b * 255));
    }

    private double clamp(double value, double min, double max) {
        return Math.max(min, Math.min(value, max));
    }
    //paints the gridepane after every move
    private void setBoard(){
        for(int i = 0 ; i < 15 ; i++){
            for(int j = 0 ; j < 25 ; j++){
                Region cell = new Region();
                Node node = this.board[i][j];
                if(node.situation == 1){
                    cell.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"),null,null)));
                }else if(node.situation == 2){
                    String brighterColor = this.makeColorBrighter(node.color,0.2);
                    cell.setBackground(new Background(new BackgroundFill(Color.web(brighterColor),null,null)));
                }else if(node.situation == 3){
                    cell.setBackground(new Background(new BackgroundFill(Color.web(node.color),null,null)));
                }
                this.gameGrid.add(cell,j,i);
            }
        }
    }
    private void handleKeyPress(KeyEvent event) {
        if (event.getCode() == KeyCode.W) {
            this.playerMove = 1;
        }else if(event.getCode() == KeyCode.S){
            this.playerMove = 3;
        }else if(event.getCode() == KeyCode.D){
            this.playerMove = 2;
        }else if(event.getCode() == KeyCode.A){
            this.playerMove = 4;
        }else if(event.getCode() == KeyCode.N){
            this.shootKind = 1;
        }else if(event.getCode() == KeyCode.M){
            this.shootKind = 2;
        }
    }
    public void start(ActionEvent event){
        new Thread(() -> {
            while (true) {
                int point = this.game.checkPoint();
                Platform.runLater(() -> {
                    this.messageLabel.setText("your point is: "+point);
                });
                this.game.move(this.playerMove);
                if(this.shootKind != 3){
                    this.game.shoot(this.shootKind,this.playerMove);
                    this.shootKind = 3;///
                }
                this.board = this.game.getBoard();

                Platform.runLater(() -> {
                    this.setBoard();
                });
                int finish = this.game.checkFinish();
                if(finish == 1){
                    Platform.runLater(() -> {
                        this.messageLabel.setText("you win!");
                    });
                    break;
                }else if(finish == 2){
                    Platform.runLater(() -> {
                        this.messageLabel.setText("you lose!");
                    });
                    break;
                }
                try {
                    Thread.sleep(this.speed);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public void backToMenu(ActionEvent event)throws IOException {
        DB db = new DB();
        LocalDate todayDate = LocalDate.now();
        String dateFormatPattern = "yyyy-MM-dd";
        String todayDateString = todayDate.format(DateTimeFormatter.ofPattern(dateFormatPattern));
        int point = this.game.checkPoint();
        String username= User.loginUser.getUsername();
        db.requestInsert("INSERT INTO record VALUE('"+username+"','"+todayDateString+"',"+point+");");
        root = FXMLLoader.load(getClass().getResource("View/menuPage.fxml"));
        stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setTitle("menu");
        stage.setScene(scene);
        stage.show();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)  {

        this.rootPane.setOnKeyPressed(this::handleKeyPress);
        this.game = new Game(menuController.color, User.loginUser.getUsername(),menuController.numberOfBot,menuController.level);
        this.board = this.game.getBoard();
        this.setBoard();
        this.playerMove = 1;
        if(menuController.speed.equals("slow")){
            this.speed = 500;
        }else if(menuController.speed.equals("normal")){
            this.speed = 250;
        }else if(menuController.speed.equals("fast")){
            this.speed = 100;
        }
    }
}
