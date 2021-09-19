package com.example.treesg;

public class AppManager {

    public static AppManager instance;

    private ExploreManager exploreManager;

    public AppManager(){
        instance = this;
    }

    public void initialize(){
        exploreManager = new ExploreManager();
    }




}
