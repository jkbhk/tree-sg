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
        Post p1 = new Post("Look at nature! Isn't it pretty?\n#nature",R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,10,5);
        p1.getHashtags().add("#nature");
        Post p2 = new Post("Dam, Singapore is pretty cool!\n#relax",R.drawable.nature_placeholder_2,"simuliu","Marina Bay",R.drawable.simu_liu,1232338,52102);
        p2.getHashtags().add("#relax");
        Post p3 = new Post("This is my 2nd time posting on TreeSG!\n#tree",R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,30,2);
        p3.getHashtags().add("#tree");
        Post p4 = new Post("This is my 3rd time posting on TreeSG!\n#environment",R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,10,11);
        p4.getHashtags().add("#environment");

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);
        posts.add(p4);
    }

    private void serializePosts(){
        //insert call to DB manager here
    }

}
