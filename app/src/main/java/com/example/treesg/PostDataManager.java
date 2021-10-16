package com.example.treesg;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;


public class PostDataManager {

    public static PostDataManager instance;
    //private HashMap<String, Post> local_posts_reference;

    // allow direct modification of posts
    public ArrayList<Post> posts;

    public PostDataManager(){
        instance = this;
        posts = new ArrayList<>();
        retreiveAllPosts();
    }

    private void retreiveAllPosts(){
        // replace with calls to DB Manager function here
        /*
        Post p1 = new Post("Look at nature! Isn't it pretty?\n#nature","1634046393420","alice_rox","Whanganui River",R.drawable.profile_placeholder,10,5);
        p1.getHashtags().add("#nature");
        Post p2 = new Post("Dam, Singapore is pretty cool!\n#relax","1634046393420","simuliu","Marina Bay",R.drawable.simu_liu,1232338,52102);
        p2.getHashtags().add("#relax");
        Post p3 = new Post("This is my 2nd time posting on TreeSG!\n#tree","1634046393420","alice_rox","Whanganui River",R.drawable.profile_placeholder,30,2);
        p3.getHashtags().add("#tree");
        Post p4 = new Post("This is my 3rd time posting on TreeSG!\n#environment","1634046393420","alice_rox","Whanganui River",R.drawable.profile_placeholder,10,11);
        p4.getHashtags().add("#environment");

        posts.add(p1);
        posts.add(p2);
        posts.add(p3);
        posts.add(p4);
*/
        PostDao.retrievePosts((ArrayList<Post> retrieved)->{this.posts = retrieved;Treedebugger.log("all posts retrieved");});
    }

    public void incrementLikes(String postID, int increment){
        PostDao.incrementPostLikes(postID,increment);
    }

    public void loadPreliked(){
        if(UserManager.instance.getCurrentUser() == null){
            Treedebugger.log("current user not loaded yet");
            return;
        }

        for(Post p : posts){
            p.setLiked(UserManager.instance.getCurrentUser().getLikedPosts().contains(p.getPostID()));
        }
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
