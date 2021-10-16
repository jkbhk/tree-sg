package com.example.treesg;

import java.util.HashMap;

public class UserManager {

    private HashMap<String,User> userCache = new HashMap<String, User>();

    public static UserManager instance;

    public UserManager(){
        instance = this;
    }

    public void cacheUser(User u){
        this.userCache.put(u.getUserID(),u);
    }

    public User getUser(String userid){

        if(!userCache.containsKey(userid))
            UserDao.read(userid);

        return userCache.get(userid);
    }




}
