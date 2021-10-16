package com.example.treesg;

public class AppManager {

    public static AppManager instance;

    private ExploreManager exploreManager;
    private PostDataManager postDataManager;
    private UserManager userManager;

    public AppManager(){
        instance = this;
    }

    public void initialize(){
        userManager = new UserManager();
        postDataManager = new PostDataManager();
        exploreManager = new ExploreManager();
    }




}
