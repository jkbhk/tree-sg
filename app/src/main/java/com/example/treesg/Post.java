package com.example.treesg;

public class Post {

    private String description;
    private Integer postImage;
    private String from;
    private String location;
    private Integer profilePic;

    public Post(String description, Integer postImage, String from, String location, Integer profilePic){
        this.description = description;
        this.postImage = postImage;
        this.from = from;
        this.location = location;
        this.profilePic = profilePic;
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
