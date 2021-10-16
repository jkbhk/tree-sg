package com.example.treesg;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
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

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PostDao {


    public void create(Post post) {

    }

    public List<Post> read(Post post) {
        FirebaseFirestore.getInstance()
                .collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

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
                                PostDataManager.instance.posts.add(retrieved);
                            }

                        }
                    }
                });

        return null;
    }

    public void update(String postID, int increment) {
        FirebaseFirestore.getInstance().collection(DatabaseManager.POSTS_COLLECTION)
                .document(postID).update("post_likes",FieldValue.increment(increment));
    }


    public void delete(Post post) {

    }
}
