package com.example.treesg;

import java.util.ArrayList;

public class Post {

    private String description;
    private String postImage;
    private String from;
    private String location;
    private String profilePic;
    private int likes;
    private int comments;
    private boolean liked;
    private ArrayList<String> hashtags;

    public Post(String description, String postImage, String from, String location, String profilePic, int likes, int comments){
        this.description = description;
        this.postImage = postImage;
        this.from = from;
        this.location = location;
        this.profilePic = profilePic;
        this.likes = likes;
        this.comments = comments;
        this.hashtags = new ArrayList<>();

    }

    public Post(){
    }

    public boolean isLiked() {
        return liked;
    }

    public void setLiked(boolean liked) {
        this.liked = liked;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getComments() {
        return comments;
    }

    public void setComments(int comments) {
        this.comments = comments;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(String profilePic) {
        this.profilePic = profilePic;
    }

    public String getDescription() {
        return description;
    }

    public String getDescriptionWithName() {
        return "<b>"+from+"</b>" +" "+ description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostImage() {
        return postImage;
    }

    public void setPostImage(String postImage) {
        this.postImage = postImage;
    }

    public ArrayList<String> getHashtags(){return this.hashtags;}

}
