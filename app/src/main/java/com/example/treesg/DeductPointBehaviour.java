package com.example.treesg;

public class DeductPointBehaviour implements IContractBehaviour{
    private int toDeduct;

    public DeductPointBehaviour(int param){
        toDeduct = param;
    }

    public void execute(){
        int currentPoint = UserManager.instance.getCurrentUser().getPoints();
        UserManager.instance.getCurrentUser().setPoints(currentPoint - toDeduct);
    }
}
