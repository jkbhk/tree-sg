package com.example.treesg;

public class DeductSpinBehaviour implements IContractBehaviour{
    private int toDeduct;

    public DeductSpinBehaviour(int param){
        toDeduct = param;
    }

    public void execute(){
        int currentSpin = UserManager.instance.getCurrentUser().getSpins();
        UserManager.instance.getCurrentUser().setSpins(currentSpin - toDeduct);
    }
}
