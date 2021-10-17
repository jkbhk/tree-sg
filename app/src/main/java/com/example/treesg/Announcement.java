package com.example.treesg;

public class Announcement {
    public String announcementPost;
    public String imageURL;
    public Announcement(){}

    public Announcement(String aPost, String url) {
        this.announcementPost = aPost;
        this.imageURL = url;
    }

    public String getAnnouncementPost() {
        return announcementPost;
    }
    public String getImageURL() {
        return imageURL;
    }
}
