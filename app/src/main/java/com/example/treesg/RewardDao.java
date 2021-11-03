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
import com.google.firebase.database.core.utilities.Tree;
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

public class RewardDao {

    public static void retrieveRewards(Consumer<ArrayList<Reward>> c){
        FirebaseFirestore.getInstance()
                .collection(DatabaseManager.REWARDS_COLLECTION)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            ArrayList<Reward> rewards = new ArrayList<>();
                            List<DocumentSnapshot> myListOfDocuments = task.getResult().getDocuments();
                            for(DocumentSnapshot d : myListOfDocuments){

                                String description = d.getString("reward_description");
                                String id = d.getString("reward_id");
                                String itemName = d.getString("reward_item_name");
                                Long lpointsRequired = (Long)d.get("reward_points_required");
                                int pointsRequired = lpointsRequired.intValue();

                                Reward retrieved = new Reward(itemName, id, description, pointsRequired);

                                List<String> group = (List<String>) d.get("reward_behaviours");
                                for(String s : group){
                                    String[] temp = s.split("_");
                                    IContractBehaviour b = RewardBehaviourFactory.createBehaviour(Integer.parseInt(temp[0]), Integer.parseInt(temp[1]));
                                    retrieved.behaviours.add(b);
                                }

                                rewards.add(retrieved);

                            }
                            Treedebugger.log("all rewards retrieved successfully.");
                            c.accept(rewards);

                        }

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Treedebugger.log("failed to retrieve all rewards.");
            }
        });
    }
}
