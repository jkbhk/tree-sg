package com.example.treesg;

import android.content.Intent;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.List;

public class UserDao {

    public static void addToLikedPosts(String userid, String post){

        FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(userid)
                .update("likedPosts", FieldValue.arrayUnion(post)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Treedebugger.log("added to likes!");
            }
        });
    }

    public static void removeFromLikedPosts(String userid, String post ){

        FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(userid)
                .update("likedPosts", FieldValue.arrayRemove(post)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Treedebugger.log("removed from likes!");
            }
        });
    }

    public static void updateUser(User u){

    }

    public static void storeCurrentUser(String userid){
        FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(userid).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {

                String email = d.getString("email");
                String fullname = d.getString("fullName");
                String phone = d.getString("phone");
                Boolean isAdmin = d.getBoolean("isAdmin");
                String username = "";
                int points = d.getLong("points").intValue();

                HashSet hs = new HashSet();
                List<String> group = (List<String>) d.get("likedPosts");
                for(String s : group){
                    hs.add(s);
                }

                User u = new User(userid,email,fullname,phone,isAdmin,username,points,hs);
                Treedebugger.log("stored");
                UserManager.instance.setCurrentUser(u);
            }
        });
    }

    public static void read(String userid){

        DocumentReference df = FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(userid);
        df.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot d) {

                //(String userID, String email, String fullName, String phone, Boolean isAdmin, String username, int points,HashSet<String> likedPosts)
                String email = d.getString("email");
                String fullname = d.getString("fullName");
                String phone = d.getString("phone");
                Boolean isAdmin = d.getBoolean("isAdmin");
                String username = "";
                int points = d.getLong("points").intValue();

                HashSet hs = new HashSet();
                List<String> group = (List<String>) d.get("likedPosts");
                for(String s : group){
                    hs.add(s);
                }

                User u = new User(userid,email,fullname,phone,isAdmin,username,points,hs);
                Treedebugger.log("hey i got em :" + fullname);
                UserManager.instance.cacheUser(u);

            }
        });
    }

}
