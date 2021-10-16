package com.example.treesg;

import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashSet;
import java.util.List;
import java.util.function.Consumer;

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

    public static void retrieveUser(String userid, Consumer<User> c){
        FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(userid).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(DocumentSnapshot d) {

                        String profilePic = d.getString("profilePic");
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

                        User u = new User(profilePic,userid,email,fullname,phone,isAdmin,username,points,hs);
                        c.accept(u);
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

    // doesnt deal with iterables
    public static void updateUser(User u){
        DocumentReference df = FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(u.getUserID());
        df.update(
                "email",u.getEmail(),
                "fullName", u.getFullName(),
                "isAdmin", u.isAdmin(),
                "phone", u.getPhone(),
                "phone", u.getPhone(),
                "points", u.getPoints(),
                "username", u.getUsername()
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Treedebugger.log("User "+ u.getFullName()+ " updated in firestore.");
            }
        });
    }


}
