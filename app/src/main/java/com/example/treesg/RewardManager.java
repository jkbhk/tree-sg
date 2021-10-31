package com.example.treesg;

import com.google.firebase.database.core.utilities.Tree;

import java.util.ArrayList;

public class RewardManager {
    public static RewardManager instance;

    public RewardManager(){
        instance = this;
        initialiseRewards();
    }

    ArrayList<Reward> rewardArrayList = new ArrayList<Reward>();

    public void initialiseRewards()
    {
        Reward r1 = new Reward("1 Spin", "001", "Purchase 1 spin", 200);
        r1.behaviours.add(new AddSpinBehaviour(1));
        r1.behaviours.add(new DeductPointBehaviour(200));
        rewardArrayList.add(r1);

        Reward r2 = new Reward("2 Spin", "002", "Purchase 2 spin", 300);
        r2.behaviours.add(new AddSpinBehaviour(2));
        r2.behaviours.add(new DeductPointBehaviour(300));
        rewardArrayList.add(r2);

        Reward r3 = new Reward("2D1N Staycation", "003", "Enjoy your day with a 2 day 1 night Staycation at Genting Hotel!", 2000);
        r3.behaviours.add(new DeductPointBehaviour(2000));
        rewardArrayList.add(r3);

        Reward r4 = new Reward("$20 Fairprice Coupon", "004", "Enjoy a shopping spree at Fairprice with this $20 coupon!", 1000);
        r4.behaviours.add(new DeductPointBehaviour(1000));
        rewardArrayList.add(r4);

        Reward r5 = new Reward("$20 Lazada Coupon", "005", "Enjoy a shopping spree at Lazada with this $20 coupon!", 1000);
        r5.behaviours.add(new DeductPointBehaviour(1000));
        rewardArrayList.add(r5);

        Reward r6 = new Reward("$20 Shopee Coupon", "006", "Enjoy a shopping spree at Shopee with this $20 coupon!", 1000);
        r6.behaviours.add(new DeductPointBehaviour(1000));
        rewardArrayList.add(r6);

        Reward r7 = new Reward("Plant Tree Opportunity", "007", "An opportunity to plant your own personal tree in Singapore and call it yours! Includes a $50 general coupon!", 2000);
        r7.behaviours.add(new DeductPointBehaviour(2000));
        rewardArrayList.add(r7);
    }

    public ArrayList<Reward> getRewardArrayList()
    {
        return rewardArrayList;
    }

    public Reward[] getRewardArray(){
        Reward[] tempReward = new Reward[rewardArrayList.size()];

        for (int i = 0; i < rewardArrayList.size(); i++){
            tempReward[i] = rewardArrayList.get(i);
        }

        return tempReward;
    }

}
