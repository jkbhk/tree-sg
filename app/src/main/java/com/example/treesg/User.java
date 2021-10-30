package com.example.treesg;

import java.util.HashMap;
import java.util.HashSet;

public class User {

    private String profilePic;
    private String email;
    private String fullName;
    private String userID;
    private String phone;
    private boolean isAdmin;
    private String username;
    private int points;
    private HashSet<String> likedPosts;
    private boolean isNew;
    private String userDescription;
    private boolean notifications;
    private int spins;

    public User(){}

    public User(String profilePic, String userID, String email, String fullName, String phone, Boolean isAdmin, String username, int points,HashSet<String> likedPosts, boolean isNew, String description, Boolean notifications,int spins){
        this.profilePic = profilePic;
        this.userID = userID;
        this.email = email;
        this.fullName = fullName;
        this.phone = phone;
        this.isAdmin = isAdmin;
        this.username = username;
        this.points = points;
        this.likedPosts = likedPosts;
        this.isNew = isNew;
        this.userDescription = description;
        this.notifications = notifications;
        this.spins = spins;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
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

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }
    public HashSet<String> getLikedPosts() {
        return likedPosts;
    }

    public void setLikedPosts(HashSet<String> likedPosts) {
        this.likedPosts = likedPosts;
    }

    public boolean isNew(){
        return this.isNew;
    }

    public void setIsNew(boolean val){
        this.isNew = val;
    }

    public String getUserDescription(){
        return this.userDescription;
    }

    public void setUserDescription(String description){
        this.userDescription = description;
    }

    public Boolean getNotifications(){
        return this.notifications;
    }

    public void setNotifications(Boolean val){
        this.notifications = val;
    }

    public int getSpins(){
        return this.spins;
    }

    public void setSpins(int val){
        this.spins = val;
    }


}
