package com.example.treesg;

public class Post {

    private String description;
    private Integer postImage;
    private String from;
    private String location;
    private Integer profilePic;

    public Post(String d, Integer i){
        this.description = d;
        this.postImage = i;
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
