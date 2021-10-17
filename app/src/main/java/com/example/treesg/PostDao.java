package com.example.treesg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.DataInput;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class PostDao {


    public static void create(Post post, Runnable callback) {

        Map<String, Object> map = new HashMap<>();
        map.put("post_creator", post.getFrom());
        map.put("post_creator_pic", post.getProfilePic());
        map.put("post_description", post.getDescription());
        List<String> list = post.getHashtags().subList(0,post.getHashtags().size());
        map.put("post_hashtags", list);
        map.put("post_image", post.getPostImage());
        map.put("post_likes", 0);
        map.put("post_location", post.getLocation());
        map.put("post_num_comments", 0);

        FirebaseFirestore.getInstance().collection(DatabaseManager.POSTS_COLLECTION).add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Treedebugger.log("post created in server.");
                callback.run();
            }

        });


    }

    public static void retrievePosts(Consumer<ArrayList<Post>> c){
        FirebaseFirestore.getInstance()
                .collection(DatabaseManager.POSTS_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Post> posts = new ArrayList<>();
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            Treedebugger.log("count is "+ myListOfDocuments.size());
                            for(DocumentSnapshot d : myListOfDocuments){

                                String description = d.getString("post_description");
                                String postImage = d.getString("post_image");
                                String from = d.getString("post_creator");
                                String location = d.getString("post_location");
                                String profilePic = d.getString("post_creator_pic");
                                Long likes = (Long)d.get("post_likes");
                                int postLikes = likes.intValue();
                                Long comments = (long)d.get("post_num_comments");
                                int postComments = comments.intValue();
                                List<String> tags = (List<String>)d.get("post_hashtags");

                                Post retrieved = new Post(d.getId(),description,postImage,from,location,profilePic,postLikes,postComments);
                                for(String s : tags)
                                    retrieved.getHashtags().add(s);

                                //retrieved.setLiked(UserManager.instance.getCurrentUser().getLikedPosts().contains(d.getId()));
                                posts.add(retrieved);
                            }
                            Treedebugger.log("all posts retrieved successfully.");
                            c.accept(posts);

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                       Treedebugger.log("failed to retrieve all posts.");
                    }
                });
    }


    public static void incrementPostLikes(String postID, int increment) {
        FirebaseFirestore.getInstance().collection(DatabaseManager.POSTS_COLLECTION)
                .document(postID).update("post_likes",FieldValue.increment(increment));
    }

    public void delete(Post post) {

    }
}
