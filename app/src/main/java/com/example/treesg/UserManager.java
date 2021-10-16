package com.example.treesg;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.function.Consumer;

public class UserManager {

    //for testing
    private static String current_user_id = "BO3xSIaihJjyxr3xvlPn";
    private static User currentUser;

    private HashMap<String,User> userCache = new HashMap<String, User>();

    public static UserManager instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public UserManager(){

        instance = this;

        //for solo testing, this will migrate to Login
        setCurrentUser(current_user_id,()->{ Treedebugger.log("current user stored successfully.");});
    }

    public void cacheUser(User u){
        this.userCache.put(u.getUserID(),u);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getUserByID(String userid, Consumer<User> callback){
        UserDao.retrieveUser(userid,callback);
    }

    // meant to be called from Login
    // after user is successfully retrieved, callback() will be called
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCurrentUser(String userid, Runnable callback){

        // consumer is what this class needs to do
        Consumer<User> consumer = (User u)->{
            currentUser=u;
            //callback is what the caller needs to do
            callback.run();
        };


        getUserByID(userid, consumer);
    }


    // MAY not be updated
    public User getCurrentUser(){
        return currentUser;
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
