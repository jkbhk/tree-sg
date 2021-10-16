package com.example.treesg;

import java.util.ArrayList;
import java.util.HashSet;

public class PostDataManager {

    public static PostDataManager instance;
    private PostDao dao = new PostDao();
    //private HashMap<String, ImageView> postImageCache;

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
        dao.update(postID,increment);
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

}
