package com.example.treesg;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.core.utilities.Tree;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BackgroundActivity extends AppCompatActivity {

    AppManager appm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*Treedebugger.log("brb");
        FirebaseFirestore.getInstance().collection(DatabaseManager.USERS_COLLECTION).get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
            @Override
            public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                Treedebugger.log("back");
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Treedebugger.log("failed");
            }
        });
        */

        appm = new AppManager();
        appm.initialize();

        startActivity(new Intent(getApplicationContext(), login.class));


    }
}