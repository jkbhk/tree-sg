package com.example.treesg;

import android.content.Intent;
import android.os.Build;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.core.utilities.Tree;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class UserDao {

    // updated properties structure for user


    // for registering new users into the system
    // sign up with fAuth first, then use the generated fAuth ID to create this user data wrapper
    // meant to be called after successful fAuthentication register***
    public static void createUser(String fAuthID,String email, String fullName, String phone, Boolean isAdmin, Runnable callback) {

        Map<String, Object> map = new HashMap<>();
        map.put("profilePic", "https://firebasestorage.googleapis.com/v0/b/treesg-5aca9.appspot.com/o/uploads%2Fdefault_profile.png?alt=media&token=3f1d0c1d-96a9-4738-9167-ea2a17fefa84");
        map.put("email", email);
        map.put("fullName", fullName);
        map.put("phone", phone);
        map.put("isAdmin", isAdmin);

        map.put("username", "your_default_username");
        map.put("points", 0);
        ArrayList<String> empty = new ArrayList<>();
        map.put("likedPosts", empty.subList(0,empty.size()));
        map.put("userDescription", "");
        map.put("isNew", true);
        map.put("notifications",true);
        map.put("spins", 0);

        // create the actual user object in the collection, using the fAuthID as the userID
       FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(fAuthID).set(map).addOnSuccessListener(new OnSuccessListener<Void>() {
           @Override
           public void onSuccess(Void unused) {
               Treedebugger.log("new user setup success.");
               callback.run();
           }
       }).addOnFailureListener(new OnFailureListener() {
           @Override
           public void onFailure(@NonNull Exception e) {
               Treedebugger.log("new user setup failed.");
           }
       });
    }


    public static void addToLikedPosts(String userid, String post){

        FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(userid)
                .update("likedPosts", FieldValue.arrayUnion(post)).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Treedebugger.log("added to likes!");
            }
        });
    }

    public static void retrieveUsers(Consumer<HashMap<String,User>> callback){
        FirebaseFirestore.getInstance()
                .collection(DatabaseManager.USERS_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            HashMap<String, User> users = new HashMap<>();
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();

                            for(DocumentSnapshot d : myListOfDocuments){

                                String profilePic = d.getString("profilePic");
                                String email = d.getString("email");
                                String fullname = d.getString("fullName");
                                String phone = d.getString("phone");
                                Boolean isAdmin = d.getBoolean("isAdmin");
                                String username = d.getString("username");
                                int points = d.getLong("points").intValue();

                                HashSet hs = new HashSet();
                                List<String> group = (List<String>) d.get("likedPosts");
                                for(String s : group){
                                    hs.add(s);
                                }

                                Boolean isNew = d.getBoolean("isNew");
                                String userDescription = d.getString("userDescription");
                                Boolean notifications = d.getBoolean("notifications");
                                int spins = d.getLong("spins").intValue();


                                User u = new User(profilePic,d.getId(),email,fullname,phone,isAdmin,username,points,hs,isNew,userDescription,notifications,spins);
                                users.put(d.getId(),u);

                            }

                            Treedebugger.log("all users retrieved.");
                            callback.accept(users);

                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Treedebugger.log("failed to retrieve all users");
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
    public static void updateUser(User u, Runnable r){
        DocumentReference df = FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(u.getUserID());
        df.update(
                "profilePic", u.getProfilePic(),
                "email",u.getEmail(),
                "fullName", u.getFullName(),
                "isAdmin", u.isAdmin(),
                "phone", u.getPhone(),
                "points", u.getPoints(),
                "username", u.getUsername(),
                "userDescription", u.getUserDescription(),
                "isNew", u.isNew(),
                "notifications", u.getNotifications(),
                "spins", u.getSpins()
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                if(r != null)
                    r.run();

                Treedebugger.log("User "+ u.getFullName()+ " updated in firestore.");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Treedebugger.log("failed to update user in firestore");
            }
        });
    }

    //=========================== ONLY USE THESE IN COMMAND MODE =================================//

    public static void updatePropertiesForAll(){
        FirebaseFirestore.getInstance()
                .collection(DatabaseManager.USERS_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {

                            //HashMap<String, User> users = new HashMap<>();
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();

                            for(DocumentSnapshot d : myListOfDocuments){

                                // add new properties here
                                d.getReference().update(
                                        "spins", 0
                                );

                            }

                            Treedebugger.log("updated user properties for all.");


                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Treedebugger.log("failed to update properties for all users.");
            }
        });
    }

    public static void updateProperties(User u){
        DocumentReference df = FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).document(u.getUserID());
        df.update(
                "userDescription", "",
                "isNew", true
        ).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Treedebugger.log("User "+ u.getFullName()+ " updated in firestore.");
            }
        });
    }


}
