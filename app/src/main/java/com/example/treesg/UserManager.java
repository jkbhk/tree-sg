package com.example.treesg;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.util.HashMap;
import java.util.function.Consumer;

public class UserManager {


    //login will call setCurrentUser, which will start populating the userCache
    //for testing
    private static String current_user_id = "BO3xSIaihJjyxr3xvlPn";


    private static User currentUser;

    private HashMap<String,User> userCache = new HashMap<String, User>();

    public static UserManager instance;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public UserManager(){

        instance = this;

    }

    public void cacheUser(User u){
        this.userCache.put(u.getUserID(),u);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void getUserByID(String userid, Consumer<User> callback){

        if(userCache.containsKey(userid)){
            callback.accept(userCache.get(userid));
        }else{
            UserDao.retrieveUser(userid,callback);
        }
    }

    // meant to be called from Login
    // after user is successfully retrieved, callback() will be called
    @RequiresApi(api = Build.VERSION_CODES.N)
    public void setCurrentUser(String userid, Runnable callback){


        retrieveAllUsers(()->{

            //after retrieving
            if(userCache.containsKey(userid)){
                currentUser = userCache.get(userid);
                Treedebugger.log("current user set successfully.");

                if(callback != null)
                    callback.run();
            }else{
                Treedebugger.log("invalid userid ,could not set current user");
            }

        });
    }

    // MAY not be updated
    public User getCurrentUser(){
        return currentUser;
    }

    // can be called when searching for users, to get all the latest users from firebase
    public void retrieveAllUsers(Runnable callback){
        UserDao.retrieveUsers((HashMap<String,User> hm)->{
            userCache = hm;
            Treedebugger.log("UserManager cache updated.");
            if(callback != null)
                callback.run();
        });
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

    // called to update a single user object in firebase
    public void updateUser(User u){
        UserDao.updateUser(u);
    }

    //(String profilePic, String userID, String email, String fullName, String phone, Boolean isAdmin, String username, int points,HashSet<String> likedPosts)
    public void createUser(String email, String fullName, String phone, Boolean isAdmin, Runnable callback){
        //UserDao.create(email,fullName,phone,isAdmin,callback);
    }


}
