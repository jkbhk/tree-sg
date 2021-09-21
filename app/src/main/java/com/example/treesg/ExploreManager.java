package com.example.treesg;

import android.util.Log;

import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExploreManager {

    public static ExploreManager instance;
    private HashMap<String, ArrayList<Integer>> trending;
    private HashMap<String, ArrayList<Post>> hashtagMap;
    private String[] trendingHashTags;

    public ExploreManager(){

        instance = this;
        trending = new HashMap<>();
        hashtagMap = new HashMap<>();
        mapHashTagsToPosts();
        Treedebugger.log(hashtagMap.size()+"");

        //---------- dummy data --------------------------------------//

        addToTrending("#nature", R.drawable.nature_placeholder);
        addToTrending("#nature", R.drawable.nature_placeholder_2);
        addToTrending("#love", R.drawable.nature_placeholder);
        addToTrending("#love", R.drawable.nature_placeholder_2);
        addToTrending("#park", R.drawable.nature_placeholder);
        addToTrending("#park", R.drawable.nature_placeholder_2);
        addToTrending("#tree", R.drawable.nature_placeholder);
        addToTrending("#tree", R.drawable.nature_placeholder_2);

        Treedebugger.log("created dummy trending data");

    }

    public void addToTrending(String hashtag, Integer previewID){

        ArrayList<Integer> bundle = trending.get(hashtag);
        if(bundle != null){
            bundle.add(previewID);
        }else{
            ArrayList<Integer> temp = new ArrayList<>();
            temp.add(previewID);
            trending.put(hashtag,temp);
        }

    }

    public String getMostTrendingHashtag(){

        if(trending.size() < 1)
            return null;

        int most = 0;
        String ans = "";

        for(String key : trending.keySet()){
            int temp = trending.get(key).size();
            if(temp > most){
                ans = key;
                most = temp;
            }
        }

        return ans;
    }

    public String[] getTopTrendingHashTags(int n){

        String[] top = new String[n];

        //for now we return the first n results
        if(hashtagMap.size() >= n){
            int counter = 0;
            for(String s : hashtagMap.keySet()){
                Treedebugger.log("hhh");
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
