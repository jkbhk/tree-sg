package com.example.treesg;

public class RewardBehaviourFactory {

    public static IContractBehaviour createBehaviour(int behaviourID, int behaviourParam) {
        switch (behaviourID) {
            case 1:
                return new AddPointBehaviour(behaviourParam);
            case 2:
                return new DeductPointBehaviour(behaviourParam);
            case 3:
                return new AddSpinBehaviour(behaviourParam);
            default:
                return null;
        }

    }
}
