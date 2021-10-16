package com.example.treesg;

import java.util.HashMap;

public class UserManager {

    private static String current_user_id = "BO3xSIaihJjyxr3xvlPn";
    private static User currentUser;

    private HashMap<String,User> userCache = new HashMap<String, User>();

    public static UserManager instance;

    public UserManager(){

        instance = this;
        setupCurrentUser(current_user_id,()->{Treedebugger.log("current user stored!");});
    }

    public void cacheUser(User u){
        this.userCache.put(u.getUserID(),u);
    }

    public User getUser(String userid){

        if(!userCache.containsKey(userid))
            UserDao.read(userid);

        return userCache.get(userid);
    }

    public void setupCurrentUser(String userid, Runnable callback){
        UserDao.storeCurrentUser(userid,callback);
    }

    public void setCurrentUser(User u){
        currentUser = u;
    }

    public User getCurrentUser(){
        return currentUser;
    }

    public void updateCurrentUser(){
        UserDao.updateUser(currentUser);
    }

    public void addToLikes(String postID){
        currentUser.getLikedPosts().add(postID);
        //update server
        UserDao.addToLikedPosts(currentUser.getUserID(),postID);
    }

    public void removeFromLikes(String postID){
        currentUser.getLikedPosts().remove(postID);
        // update server
        UserDao.removeFromLikedPosts(currentUser.getUserID(),postID);
    }


}
