package com.example.treesg;

import java.util.HashMap;
import java.util.HashSet;

public class User {
    private String email;
    private String fullName;
    private String userID;
    private String phone;
    private boolean isAdmin;
    private String username;
    private HashSet<String> likedPosts;

    public User(){}

    public User(String userID, String email, String fullName, String phone, Boolean isAdmin, String username, HashSet<String> likedPosts){
        this.userID = userID;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.isAdmin = isAdmin;
        this.username = username;
        this.likedPosts = likedPosts;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public HashSet<String> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(HashSet<String> likedPosts) {
        this.likedPosts = likedPosts;
    }


}
