package com.example.treesg;

import java.util.ArrayList;

public class Reward {

    String itemName;
    String ID;
    String description;
    int pointsRequired;
    ArrayList<IContractBehaviour> behaviours;

    public Reward(String itemName, String ID, String description, int pointsRequired)
    {
        this.itemName = itemName;
        this.ID = ID;
        this.description = description;
        this.pointsRequired = pointsRequired;
        behaviours = new ArrayList<IContractBehaviour>();
    }

    public void applyContract(){
        for (IContractBehaviour b : behaviours)
        {
            Treedebugger.log("executed");
            b.execute();
        }
    }
}
