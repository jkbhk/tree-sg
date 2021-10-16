package com.example.treesg;

import android.content.Intent;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.List;

public class UserDao {

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
                UserManager.instance.cacheUser(u);

            }
        });
    }

}
