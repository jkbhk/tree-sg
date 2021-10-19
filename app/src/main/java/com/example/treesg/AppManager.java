package com.example.treesg;

import android.provider.ContactsContract;

public class AppManager {

    public static AppManager instance;

    private ExploreController exploreController;
    private PostDataManager postDataManager;
    private UserManager userManager;

    public AppManager(){
        instance = this;
    }

    public void initialize(){
        Treedebugger.log("starting all controller entities...");

        userManager = new UserManager();
        postDataManager = new PostDataManager();
        exploreController = new ExploreController();

        Treedebugger.log("All controller entities started.");
    }




}
