package com.example.treesg;

import android.provider.ContactsContract;

public class AppManager {

    public static AppManager instance;

    private ExploreController exploreController;
    private PostDataManager postDataManager;
    private UserManager userManager;
    private RewardManager rewardManager;

    public AppManager(){
        instance = this;
    }

    public void initialize(){
        Treedebugger.log("starting all controller entities...");

        userManager = new UserManager();
        postDataManager = new PostDataManager();
        exploreController = new ExploreController();
        rewardManager = new RewardManager();

        Treedebugger.log("All controller entities started.");
    }




}
