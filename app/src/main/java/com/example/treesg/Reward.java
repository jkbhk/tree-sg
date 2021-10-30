package com.example.treesg;

public class Reward {

    String itemName;
    String ID;
    String description;
    int pointsRequired;
    //private int quantity;

    public Reward(String itemName, String ID, String description, int pointsRequired)
    {
        this.itemName = itemName;
        this.ID = ID;
        this.description = description;
        this.pointsRequired = pointsRequired;
        //this.quantity = quantity;
    }
}
