package com.example.treesg;

public class AddSpinBehaviour implements IContractBehaviour{

    private int toAdd;

    public AddSpinBehaviour(int param){
        toAdd = param;
    }

    public void execute(){
        int currentSpins = UserManager.instance.getCurrentUser().getSpins();
        UserManager.instance.getCurrentUser().setSpins(currentSpins + toAdd);
    }
}
