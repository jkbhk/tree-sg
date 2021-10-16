package com.example.treesg;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExploreManager {

    public static ExploreManager instance;
    private HashMap<String, ArrayList<Post>> hashtagMap;

    public ExploreManager(){
        instance = this;
        hashtagMap = new HashMap<>();
        mapHashTagsToPosts();
        Treedebugger.log(hashtagMap.size()+"");

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

    private void mapHashTagsToPosts(){
        ArrayList<Post> list = PostDataManager.instance.posts;

        for(Post p : list){
            for(String tag : p.getHashtags()){
                Treedebugger.log(tag);
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
