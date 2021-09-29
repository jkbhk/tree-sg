package com.example.treesg;

public class AppManager {

    public static AppManager instance;

    private ExploreManager exploreManager;
    private PostDataManager postDataManager;

    public AppManager(){
        instance = this;
    }

    public void initialize(){
        postDataManager = new PostDataManager();
        exploreManager = new ExploreManager();
    }




}
