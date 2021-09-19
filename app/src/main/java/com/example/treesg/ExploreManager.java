package com.example.treesg;

import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ExploreManager {

    public static ExploreManager instance;
    private HashMap<String, ArrayList<Integer>> trending;

    public ExploreManager(){

        instance = this;
        trending = new HashMap<>();

        //---------- dummy data --------------------------------------//
        ArrayList<Integer> dummyBundle = new ArrayList<Integer>();
        dummyBundle.add(R.drawable.nature_placeholder_2);
        dummyBundle.add(R.drawable.nature_placeholder);

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

    public ArrayList<Integer> getBundlePreviewIds(String hashtag){
        return trending.get(hashtag);
    }













}
