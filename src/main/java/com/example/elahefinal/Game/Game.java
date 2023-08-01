package com.example.elahefinal.Game;

import com.example.elahefinal.Node.Bot;
import com.example.elahefinal.Node.Player;
import com.example.elahefinal.Node.PlayerABS;
import com.example.elahefinal.User.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Game {

    private Random random;
    private Node[][] board;
    private PlayerABS player;
    private List<PlayerABS> players;
    private int checkRate = 10;
    private int numberOfBut;
    public Game(String color,String username,int numberOfBot,String level){
        if(level.equals("easy")){
            this.checkRate = 10;
        }else if(level.equals("normal")){
            this.checkRate = 20;
        }else if(level.equals("hard")){
            this.checkRate = 40;
        }
        this.random = new Random();
        this.board = new Node[100][100];
        for(int i = 0 ; i < 100 ; i++){
            for(int j = 0 ; j < 100 ; j++){
                Node node = new Node(1,"#FFFFFF");
                board[i][j] = node;
            }
        }
        this.players = new ArrayList<>();
        this.player = new Player(50,50,color,username);
        this.players.add(this.player);
        this.numberOfBut = numberOfBot;
        for(int i = 0 ; i < this.numberOfBut ; i++){
            int indexI = this.random.nextInt(100);
            int indexJ = this.random.nextInt(100);
            String botColor = this.getRandomColorString();
            boolean flag = true;
            for(PlayerABS player1 : this.players){
                if(player1.indexI == indexI){
                    flag = false;
                }
                if(player1.indexJ == indexJ){
                    flag = false;
                }
                if(player1.color == botColor){
                    flag = false;
                }
            }

            if(flag){
                Bot bot = new Bot(indexI,indexJ,botColor,i);
                this.players.add(bot);
            }else{
                i--;
            }
        }
        for(PlayerABS playerABS : this.players){
            Node node = new Node(playerABS.player,3,playerABS.color);
            this.board[playerABS.indexI][playerABS.indexJ] = node;
        }
    }

    public String getRandomColorString() {

        int red = this.random.nextInt(256);
        int green = this.random.nextInt(256);
        int blue = this.random.nextInt(256);

        String colorString = String.format("#%02X%02X%02X", red, green, blue);

        return colorString;
    }
    public void getPlayer(){
        for(PlayerABS playerABS : this.players){
            if(playerABS.ownerType.equals("PLAYER")){
                this.player = playerABS;
                break;
            }
        }
    }
    //index out of range
    public Node [][] getBoard(){
        Node [][] returnValue = new Node[15][25];
        this.getPlayer();
        int indexI = this.player.indexI - 7;
        int indexJ = this.player.indexJ - 12;
        for(int i = 0 ; i <   15 ; i++){
            for(int j = 0 ; j <  25 ; j++){
                try{
                    returnValue[i][j] = this.board[indexI + i][indexJ + j];
                }catch (IndexOutOfBoundsException e){
                    returnValue[i][j] = new Node(1,"#FFFFFF");
                }


            }
        }
        return returnValue;
    }
    public void increaseTop(){
        int x = this.board.length;
        int y = this.board[0].length;
        Node [][] newBoard = new Node[x+1][y];
        for(int j = 0 ; j < y ; j++){
            Node node = new Node(1,"#FFFFFF");
            newBoard[0][j] = node;
        }
        for(int i = 0 ; i < x ; i++){
            for(int j = 0 ; j < y ; j++){
                newBoard[i+1][j] = this.board[i][j];
            }
        }
        for(PlayerABS playerABS : this.players){
            playerABS.indexI++;
        }
        this.board = newBoard;
    }
    public void increaseDown(){
        int x = this.board.length;
        int y = this.board[0].length;
        Node [][] newBoard = new Node[x + 1][y];

        for(int i = 0 ; i < x ; i++){
            for(int j = 0 ; j < y ; j++){
                newBoard[i][j] = this.board[i][j];
            }
        }
        for(int j = 0 ; j < y ; j++){
            Node node = new Node(1,"#FFFFFF");
            newBoard[x][j] = node;
        }
        this.board = newBoard;
    }
    public void increaseLeft(){
        int x = this.board.length;
        int y = this.board[0].length;
        Node [][] newBoard = new Node[x][ y + 1];
        for(int i = 0 ; i < x ; i++){
            Node node = new Node(1,"#FFFFFF");
            newBoard[i][0] = node;
        }
        for(int i = 0 ; i < x ; i++){
            for(int j = 0 ; j < y ; j++){
                newBoard[i][j + 1] = this.board[i][j];
            }
        }

        for(PlayerABS playerABS : this.players){
            playerABS.indexJ++;
        }
        this.board = newBoard;
    }
    public void increaseRight(){
        int x = this.board.length;
        int y = this.board[0].length;
        Node [][] newBoard = new Node[x][ y + 1];
        for(int i = 0 ; i < x ; i++){
            for(int j = 0 ; j < y ; j++){
                newBoard[i][j] = this.board[i][j];
            }
        }
        for(int i = 0 ; i < x ; i++){
            Node node = new Node(1,"#FFFFFF");
            newBoard[i][y] = node;
        }
        this.board = newBoard;
    }
    public void lose(String player){
        int index = 0;
        boolean flag = false;
        for (PlayerABS playerABS : this.players){
            if(playerABS.player.equals(player)){
                flag = true;
                break;
            }
            index++;
        }
        if(flag){
            this.players.remove(index);
        }
        System.out.println(this.players.size());
        for(int i = 0 ; i < this.board.length ; i++){
            for(int j = 0 ; j < this.board[0].length ; j++){
                if(this.board[i][j].playerName!= null){
                    if(this.board[i][j].playerName.equals(player)){
                        this.board[i][j] = new Node(1,"#FFFFFF");
                    }
                }
            }
        }

    }
    public void fill(PlayerABS player){
        for(int i = 0 ; i < this.board.length ; i++){
            for(int j = 0 ; j < this.board[0].length ; j++){
                if(this.board[i][j].playerName != null){
                    if(this.board[i][j].playerName.equals(player.player)){
                        Node node = this.board[i][j];
                        node.situation = 3;
                        int end = j;
                        for(int k = j+1 ; k < this.board[0].length ; k++){
                            if(this.board[i][k].playerName != null){
                                if(board[i][k].playerName.equals(player.player)){
                                    end = k;
                                    break;
                                }
                            }
                        }
                        if(!(end == j)){
                            for(int k = j ; k <= end ; k++){
                                this.board[i][k] = node;
                            }
                            j = end - 1;
                        }

                    }
                }
            }
        }
    }
    //check if player of node in board is exist yet
    public void clean(){
        for(int i = 0 ; i < this.board.length ; i++){
            for(int j = 0 ; j < this.board[0].length ; j++){
                if(this.board[i][j].playerName!= null){
                    boolean flag = false;
                    for(PlayerABS playerABS : this.players){
                        if(this.board[i][j].playerName.equals(playerABS.player)){
                            flag = true;
                        }
                    }
                    if(!flag){
                        this.board[i][j] = new Node(1,"#FFFFFF");
                    }
                }
            }
        }
    }
    public void shoot(int kind,int playerSide){
        List<PlayerABS> copy = new ArrayList<>(this.players);
        for(PlayerABS playerABS:copy){
            //find login player in game
            if(playerABS.player.equals(User.loginUser.getUsername())){
                int indexI = playerABS.indexI;
                int indexJ = playerABS.indexJ;
                //create new node which our player take it
                Node node = new Node(this.board[indexI][indexJ].playerName,3,this.board[indexI][indexJ].color);
                if(kind == 1){
                    if(playerABS.numberOfGunA != 0) {
                        for (int i = 0; i < 5; i++) {
                            if (playerSide == 1) {
                                indexI--;
                                if (indexI < 0) {
                                    increaseTop();
                                }
                            } else if (playerSide == 2) {
                                indexJ++;
                                if (indexJ >= this.board[0].length) {
                                    increaseRight();
                                }
                            } else if (playerSide == 3) {
                                indexI++;
                                if (indexI >= this.board.length) {
                                    increaseDown();
                                }
                            } else if (playerSide == 4) {
                                indexJ--;
                                if (indexJ < 0) {
                                    increaseLeft();
                                }
                            }
                        }

                        if (indexI - 1 < 0) {
                            increaseTop();
                        }
                        if (indexI + 1 >= this.board.length) {
                            increaseDown();
                        }
                        if (indexJ - 1 < 0) {
                            increaseLeft();
                        }
                        if (indexJ + 1 >= this.board.length) {
                            increaseRight();
                        }
                        //check if there is another player in 3 * 3 squire
                        if(this.board[indexI][indexJ - 1].playerName!=null){
                            if (!this.board[indexI][indexJ - 1].playerName.equals(playerABS.player)) {
                                if (this.board[indexI][indexJ - 1].situation == 2) {
                                    lose(this.board[indexI][indexJ - 1].playerName);
                                }
                            }
                        }

                        if(this.board[indexI][indexJ].playerName!=null){
                            if (!this.board[indexI][indexJ].playerName.equals(playerABS.player)) {
                                if (this.board[indexI][indexJ].situation == 2) {
                                    lose(this.board[indexI][indexJ].playerName);
                                }
                            }
                        }

                        if(this.board[indexI][indexJ + 1].playerName!=null){
                            if (!this.board[indexI][indexJ + 1].playerName.equals(playerABS.player)) {
                                if (this.board[indexI][indexJ + 1].situation == 2) {
                                    lose(this.board[indexI][indexJ + 1].playerName);
                                }
                            }
                        }

                        if(this.board[indexI - 1][indexJ - 1].playerName!=null){
                            if (!this.board[indexI - 1][indexJ - 1].playerName.equals(playerABS.player)) {
                                if (this.board[indexI][indexJ - 1].situation == 2) {
                                    lose(this.board[indexI - 1][indexJ - 1].playerName);
                                }
                            }
                        }

                        if(this.board[indexI - 1][indexJ].playerName!=null){
                            if (!this.board[indexI - 1][indexJ].playerName.equals(playerABS.player)) {
                                if (this.board[indexI - 1][indexJ].situation == 2) {
                                    lose(board[indexI - 1][indexJ].playerName);
                                }
                            }
                        }

                        if(this.board[indexI - 1][indexJ + 1].playerName!=null){
                            if (!this.board[indexI - 1][indexJ + 1].playerName.equals(playerABS.player)) {
                                if (this.board[indexI - 1][indexJ + 1].situation == 2) {
                                    lose(board[indexI - 1][indexJ + 1].playerName);
                                }
                            }
                        }

                        if(this.board[indexI + 1][indexJ - 1].playerName!=null){
                            if (!this.board[indexI + 1][indexJ - 1].playerName.equals(playerABS.player)) {
                                if (this.board[indexI + 1][indexJ - 1].situation == 2) {
                                    lose(board[indexI + 1][indexJ - 1].playerName);
                                }
                            }
                        }

                        if(this.board[indexI + 1][indexJ].playerName!=null){
                            if (!this.board[indexI + 1][indexJ].playerName.equals(playerABS.player)) {
                                if (this.board[indexI + 1][indexJ].situation == 2) {
                                    lose(board[indexI + 1][indexJ].playerName);
                                }
                            }
                        }

                        if(this.board[indexI + 1][indexJ + 1].playerName!=null){
                            if (!this.board[indexI + 1][indexJ + 1].playerName.equals(playerABS.player)) {
                                if (this.board[indexI + 1][indexJ + 1].situation == 2) {
                                    lose(board[indexI + 1][indexJ + 1].playerName);
                                }
                            }
                        }

                        this.board[indexI][indexJ - 1] = node;
                        this.board[indexI][indexJ] = node;
                        this.board[indexI][indexJ + 1] = node;
                        this.board[indexI + 1][indexJ - 1] = node;
                        this.board[indexI + 1][indexJ] = node;
                        this.board[indexI + 1][indexJ + 1] = node;
                        this.board[indexI - 1][indexJ - 1] = node;
                        this.board[indexI - 1][indexJ] = node;
                        this.board[indexI - 1][indexJ + 1] = node;
                        playerABS.numberOfGunA--;
                    }
                }else if(kind == 2){
                    if(Math.abs((int) System.currentTimeMillis() - playerABS.lastShotB)>=3000){
                        indexI = playerABS.indexI;
                        indexJ = playerABS.indexJ;
                        node = this.board[indexI][indexJ];
                        if (playerSide == 1) {
                            for(int i = indexI ; i >= 0 ;i--){
                                if(this.board[i][indexJ].playerName!=null){
                                    if(!this.board[i][indexJ].playerName.equals(playerABS.player) && this.board[i][indexJ].situation == 2){
                                        lose(this.board[i][indexJ].playerName);
                                    }
                                }
                            }
                        }
                        if (playerSide == 2) {
                            for(int j = indexJ ; j < this.board[0].length ;j++){
                                if(this.board[indexI][j].playerName!=null){
                                    if(!this.board[indexI][j].playerName.equals(playerABS.player) && this.board[indexI][j].situation == 2){
                                        lose(this.board[indexI][j].playerName);
                                    }
                                }
                            }
                        }
                        if (playerSide == 3) {
                            for(int i = indexI ; i <this.board.length;i++){
                                if(this.board[i][indexJ].playerName!=null){
                                    if(!this.board[i][indexJ].playerName.equals(playerABS.player) && this.board[i][indexJ].situation == 2){
                                        lose(this.board[i][indexJ].playerName);
                                    }
                                }
                            }
                        }
                        if (playerSide == 4) {
                            for(int j = indexJ ; j >= 0 ;j--){
                                if(this.board[indexI][j].playerName!=null){
                                    if(!this.board[indexI][j].playerName.equals(playerABS.player) && this.board[indexI][j].situation == 2){
                                        lose(this.board[indexI][j].playerName);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    //check if there is another player upside of the bot
    private boolean checkLeft(PlayerABS player){
        boolean flag = false;
        for(int j = 1 ; j <= this.checkRate ; j++){
            if(player.indexJ - j >= 0){
                if(this.board[player.indexI][player.indexJ - j].situation != 1){
                    if(!this.board[player.indexI][player.indexJ - j].playerName.equals(player.player)){
                        flag = true;
                        break;
                    }
                }
            }else{
                break;
            }
        }
        return flag;
    }
    //check if there is another player downside of the bot
    private boolean checkRight(PlayerABS player){
        boolean flag = false;
        for(int j = 1 ; j <= this.checkRate ; j++){
            if(player.indexJ + j < this.board[0].length){
                if(this.board[player.indexI][player.indexJ + j].situation != 1){
                    if(!this.board[player.indexI][player.indexJ + j].playerName.equals(player.player)){
                        flag = true;
                        break;
                    }
                }
            }else{
                break;
            }
        }
        return flag;
    }
    //check if there is another player upside of the bot
    private boolean checkUp(PlayerABS player){
        boolean flag = false;
        for(int i = 1 ; i <= this.checkRate ; i++){
            if(player.indexI - i >= 0){
                if(this.board[player.indexI - i][player.indexJ].situation != 1){
                    if(!this.board[player.indexI-i][player.indexJ].playerName.equals(player.player)){
                        flag = true;
                        break;
                    }
                }
            }else{
                break;
            }
        }
        return flag;
    }
    //check if there is another player in downside of the bot
    private boolean checkDown(PlayerABS player){
        boolean flag = false;
        for(int i = 1 ; i <= this.checkRate ; i++){
            if(player.indexI + i < this.board.length){
                if(this.board[player.indexI + i][player.indexJ].situation != 1){
                    if(!this.board[player.indexI + i][player.indexJ].playerName.equals(player.player)){
                        flag = true;
                        break;
                    }
                }
            }else{
                break;
            }
        }
        return flag;
    }
    public void move(int playerAspect){
        int moveSide;
        List<PlayerABS> copy = new ArrayList<>(this.players);
        for(PlayerABS playerABS:copy){
            if(playerABS.ownerType.equals("PLAYER")){
                moveSide = playerAspect;
            }else{
                boolean leftCheck = checkLeft(playerABS);
                boolean rightCheck = checkRight(playerABS);
                boolean upCheck = checkUp(playerABS);
                boolean downCheck = checkDown(playerABS);
                moveSide = 1;
                if(leftCheck){
                    moveSide = 4;
                }
                if(rightCheck){
                    moveSide = 2;
                }
                if(upCheck){
                    moveSide = 1;
                }
                if (downCheck){
                    moveSide = 3;
                }
                if(!upCheck && !downCheck && !leftCheck && !rightCheck){
                    moveSide = this.random.nextInt(4);
                }
            }
            if(moveSide == 1){
                playerABS.indexI --;
                if(playerABS.indexI < 0){
                    increaseTop();
                }
            }else if(moveSide == 2){
                playerABS.indexJ ++;
                if(playerABS.indexJ >= this.board[0].length){
                    increaseRight();
                }
            }else if(moveSide == 3){
                playerABS.indexI ++;
                if(playerABS.indexI >= this.board.length){
                    increaseDown();
                }
            }else if(moveSide == 4){
                playerABS.indexJ --;
                if(playerABS.indexJ < 0){
                    increaseLeft();
                }
            }
            if(this.board[playerABS.indexI][playerABS.indexJ].situation == 1){
                Node node = new Node(playerABS.player,2,playerABS.color);
                this.board[playerABS.indexI][playerABS.indexJ] = node;
            }else if(this.board[playerABS.indexI][playerABS.indexJ].situation == 2){
                if(this.board[playerABS.indexI][playerABS.indexJ].playerName.equals(playerABS.player)){
                    //do nothing
                }else{
                    this.lose(this.board[playerABS.indexI][playerABS.indexJ].playerName);
                    Node node = new Node(playerABS.player,2,playerABS.color);
                    this.board[playerABS.indexI][playerABS.indexJ] = node;
                }
            }else if(this.board[playerABS.indexI][playerABS.indexJ].situation == 3){
                if(this.board[playerABS.indexI][playerABS.indexJ].playerName.equals(playerABS.player)){
                    this.fill(playerABS);
                }else{
                    //do nothing
                }
            }
            if(playerABS.ownerType.equals("PLAYER")){
                this.player = playerABS;
            }
        }
        this.clean();

    }
    public int checkPoint(){
        for (PlayerABS playerABS : this.players){
            if(playerABS.ownerType.equals("PLAYER")){
                this.player = playerABS;
            }
        }
        int point = 0;
        for(int i = 0 ; i < this.board.length ; i++){
            for(int j = 0 ; j < this.board[0].length ; j++){
                if(this.board[i][j].playerName != null){
                    if(this.board[i][j].playerName.equals(this.player.player)){
                        point++;
                    }
                }
            }
        }
        return point;
    }
    public int checkFinish(){
        boolean flag = false;
        for(PlayerABS playerABS : this.players){
            if(playerABS.ownerType.equals("PLAYER")){
                flag = true;
            }
        }
        //there is no other player
        if(this.players.size() == 1){
            return 1;
        }
        //user lose
        if(flag == false){
            return 2;
        }
        //game still going
        return 3;
    }
}
