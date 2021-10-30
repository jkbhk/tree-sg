package com.example.treesg;

public class AddPointBehaviour implements IContractBehaviour{
    private int toAdd;

    public AddPointBehaviour(int param){
        toAdd = param;
    }

    public void execute(){
        int currentPoint = UserManager.instance.getCurrentUser().getPoints();
        UserManager.instance.getCurrentUser().setPoints(currentPoint + toAdd);
    }
}
