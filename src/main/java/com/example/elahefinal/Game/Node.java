package com.example.elahefinal.Game;

public class Node {
    public String playerName;
    public String color;
    //1->for empty 2->for taking 3->taken
    public int situation;

    public Node(int situation,String color) {
        this.situation = situation;
        this.color = color;
    }


    public Node(String playerName, int situation,String color) {
        this.playerName = playerName;
        this.situation = situation;
        this.color = color;
    }

    @Override
    public String toString() {
        return "Node{" +
                "playerName='" + playerName + '\'' +
                ", color='" + color + '\'' +
                ", situation=" + situation +
                '}';
    }
}
