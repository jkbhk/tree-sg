package com.example.treesg;

public class Post {

    private String description;
    private Integer postImage;
    private String from;
    private String location;
    private Integer profilePic;
    private int likes;
    private int comments;

    public Post(String description, Integer postImage, String from, String location, Integer profilePic, int likes, int comments){
        this.description = description;
        this.postImage = postImage;
        this.from = from;
        this.location = location;
        this.profilePic = profilePic;
        this.likes = likes;
        this.comments = comments;
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

    public Integer getProfilePic() {
        return profilePic;
    }

    public void setProfilePic(Integer profilePic) {
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

    public Integer getPostImage() {
        return postImage;
    }

    public void setPostImage(Integer postImage) {
        this.postImage = postImage;
    }

}
