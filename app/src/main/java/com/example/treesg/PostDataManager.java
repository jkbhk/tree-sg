package com.example.treesg;

import java.util.ArrayList;

public class PostDataManager {

    public static PostDataManager instance;

    // allow direct modification of posts
    public ArrayList<Post> posts;

    public PostDataManager(){
        instance = this;
        posts = new ArrayList<>();
        retreiveAllPosts();
    }

    private void retreiveAllPosts(){
        // replace with calls to DB Manager function here
        posts.add(new Post("Look at nature! Isn't it pretty?",R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,10,5));
        posts.add(new Post("Dam, Singapore is pretty cool!",R.drawable.nature_placeholder_2,"simuliu","Marina Bay",R.drawable.simu_liu,1232338,52102));
        posts.add(new Post("wowzers i love nature",R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,10,5));
        posts.add(new Post("wowzers i love nature",R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,10,5));
    }

    private void serializePosts(){
        //insert call to DB manager here
    }

}
