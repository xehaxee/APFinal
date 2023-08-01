package com.example.elahefinal.Node;

public abstract class PlayerABS {
    public int indexI,indexJ,lastSI,lastSJ;
    public String color;
    public String ownerType;
    public String player;
    public int numberOfGunA = 10;
    public int lastShotB = (int) System.currentTimeMillis();
    public PlayerABS(int indexI, int indexJ, String color, String ownerType, String player) {
        this.indexI = indexI;
        this.indexJ = indexJ;
        this.color = color;
        this.ownerType = ownerType;
        this.player = player;
    }
    public PlayerABS(){

    }
    @Override
    public String toString() {
        return "PlayerABS{" +
                "indexI=" + indexI +
                ", indexJ=" + indexJ +
                ", lastSI=" + lastSI +
                ", lastSJ=" + lastSJ +
                ", color='" + color + '\'' +
                ", ownerType='" + ownerType + '\'' +
                ", player='" + player + '\'' +
                '}';
    }
}
