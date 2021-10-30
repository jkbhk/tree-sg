package com.example.treesg;

public class Announcement {
    public String announcementPost;
    public String imageURL;
    public Announcement(){
        //empty constructor needed
    }

    public Announcement(String aPost, String imgUrl) {
        this.announcementPost = aPost;
        this.imageURL = imgUrl;
    }

    public String getAnnouncementPost() {
        return announcementPost;
    }

    public void setAnnouncementPost(String post){
        announcementPost = post;
    }
    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imgUrl){
        imageURL = imgUrl;
    }
}