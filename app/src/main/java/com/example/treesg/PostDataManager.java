package com.example.treesg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class PostDataManager {

    public static PostDataManager instance;

    // allow direct modification of posts
    public ArrayList<Post> posts;
    private HashMap<String, Post> postCache;

    public PostDataManager(){
        instance = this;
        posts = new ArrayList<>();
        //retreiveAllPosts(null);
    }

    public void retreiveAllPosts(Runnable callback){

        posts.clear();
        posts = new ArrayList<>();

        PostDao.retrievePosts((ArrayList<Post> retrieved)->{
            this.posts = retrieved;

            if(callback != null)
                callback.run();
        });
    }

    public Post[] getPostArrayByUser(User u){

        ArrayList<Post> tempList = new ArrayList<>();
        for(Post p : posts){
            if(u.getUserID() == p.getFrom()){
                tempList.add(p);
            }
        }

        Post[] temp = new Post[tempList.size()];

        for(int i = 0; i < temp.length; i++){
            temp[i] = tempList.get(i);
        }

        return temp;
    }

    public Post[] getPostArray(){

        Post[] temp = new Post[posts.size()];

        for(int i = 0; i < posts.size(); i++){
            temp[i] = posts.get(i);
        }

        return temp;
    }

    public void incrementLikes(String postID, int increment){

        PostDao.incrementPostLikes(postID,increment);
    }


    public void createNewPost(User u, String post_description, String post_image, String post_location, Runnable callback){

        Post p = new Post();
        String[] words = post_description.split(" ");

        for(String word : words) {
            if(word.substring(0, 1).equals("#")) {
                p.getHashtags().add(word);
            }
        }


        p.setFrom(u.getUserID());
        p.setPostImage(post_image);
        p.setDescription(post_description);
        p.setLikes(0);
        p.setComments(0);
        p.setProfilePic(u.getProfilePic());
        p.setLocation(post_location);

        PostDao.create(p,callback);

    }


}
