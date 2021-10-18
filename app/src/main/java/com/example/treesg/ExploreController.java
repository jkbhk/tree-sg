package com.example.treesg;

import java.util.ArrayList;
import java.util.HashMap;

public class ExploreController {

    public static ExploreController instance;
    private HashMap<String, ArrayList<Post>> hashtagMap;

    public ExploreController(){
        instance = this;
        hashtagMap = new HashMap<>();
    }

    public void loadPreliked(){
        if(UserManager.instance.getCurrentUser() == null){
            Treedebugger.log("current user not loaded yet, could not load preliked posts");
            return;
        }

        for(Post p : PostDataManager.instance.posts){
            p.setLiked(UserManager.instance.getCurrentUser().getLikedPosts().contains(p.getPostID()));
        }
    }


    public String[] getTopTrendingHashTags(int n){

        String[] top = new String[n];

        //for now we return the first n results
        if(hashtagMap.size() >= n){
            int counter = 0;
            for(String s : hashtagMap.keySet()){
                top[counter] = s;
                counter++;
                if(counter == n)
                    break;
            }

        }else{
            for(int i =0 ;i<top.length;i++){
                top[i] = "dummy";
            }
        }
        return top;
    }

    public ArrayList<Post> getBundle(String hashtag){

        return hashtagMap.get(hashtag);
    }

    public void mapHashTagsToPosts(){
        ArrayList<Post> list = PostDataManager.instance.posts;

        for(Post p : list){
            for(String tag : p.getHashtags()){
                addToMap(tag,p);
            }
        }
    }

    private void addToMap(String key, Post p){
        ArrayList<Post> temp = hashtagMap.get(key);
        if(temp != null){
            temp.add(p);
        }else{
            ArrayList<Post> newList = new ArrayList<>();
            newList.add(p);
            hashtagMap.put(key,newList);
        }
    }
















}
