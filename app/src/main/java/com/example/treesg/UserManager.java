package com.example.treesg;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.function.Consumer;

public class UserManager {


    //login will call setCurrentUser, which will start populating the userCache

    private static User currentUser;

    private HashMap<String,User> userCache = new HashMap<String, User>();

    public static UserManager instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public UserManager(){

        instance = this;

    }

    public User getUserByID(String userid){

        return userCache.get(userid);
    }


    // meant to be called from Login
    // will fetch all users from firestore first, then set current user from our local user cache
    // after current user is set, callback() will be called
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCurrentUserAsync(String userid, Runnable callback){

        retrieveAllUsersAsync(()->{

            //after retrieving
            currentUser = userCache.get(userid);
            if(currentUser != null){

                Treedebugger.log("current user set successfully.");

                if(callback != null)
                    callback.run();

            }else{
                Treedebugger.log("invalid userid ,could not set current user");
            }

        });
    }


    public User getCurrentUser(){
        return currentUser;
    }

    // can be called when searching for users, to get all the latest users from firebase
    public void retrieveAllUsersAsync(Runnable callback){
        userCache = null;
        UserDao.retrieveUsers((HashMap<String,User> hm)->{
            userCache = hm;

            Treedebugger.log("Fetched "+hm.size()+" total users. UserManager cache updated.");
            if(callback != null)
                callback.run();
        });
    }

    public HashMap<String, User> getAllUsers(Runnable callback){
        return userCache;
    }


    public void addToLikesAsync(String postID){
        currentUser.getLikedPosts().add(postID);
        //update server
        UserDao.addToLikedPosts(currentUser.getUserID(),postID);
    }

    public void removeFromLikesAsync(String postID){
        currentUser.getLikedPosts().remove(postID);
        // update server
        UserDao.removeFromLikedPosts(currentUser.getUserID(),postID);
    }

    // called to update a single user object in firebase
    public void updateUserAsync(String userid){
        User u = userCache.get(userid);
        if(u != null)
            UserDao.updateUser(u, null);
    }

    // called to update a single user object in firebase
    public void updateUserAsync(String userid, Runnable r){
        User u = userCache.get(userid);
        if(u != null)
            UserDao.updateUser(u,  r);
    }

    public void createUserAsync(String userid, String email, String fullName, String phone, Boolean isAdmin, Runnable callback){
        UserDao.createUser(userid, email,fullName,phone,isAdmin,callback);
    }

    public void incrementPoints(int param){
        int temp = currentUser.getPoints();
        temp += param;
        currentUser.setPoints(temp);

    }

    public void incrementSpins(int param){
        int temp = currentUser.getSpins();
        temp += param;
        currentUser.setSpins(temp);
    }

    public void incrementPointsAsync(int param, Runnable r){
        int temp = currentUser.getPoints();
        temp += param;
        currentUser.setPoints(temp);
        updateUserAsync(currentUser.getUserID(), r);
    }

    public void incrementSpinsAsync(int param, Runnable r){
        int temp = currentUser.getSpins();
        temp += param;
        currentUser.setSpins(temp);
        updateUserAsync(currentUser.getUserID(), r);
    }
}
