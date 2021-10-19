package com.example.treesg;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.utilities.Tree;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class BackgroundActivity extends AppCompatActivity {

    AppManager appManager;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appManager = new AppManager();

        //if logged in or returning user
        FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();

        if(u != null){
            appManager.initialize();
            UserManager.instance.setCurrentUserAsync(u.getUid(),()->{
                Treedebugger.log("user fetching complete, safe to proceed to homepage.");
                Treedebugger.log("Welcome " + UserManager.instance.getCurrentUser().getFullName());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            });
        }else{
            startActivity(new Intent(getApplicationContext(), login.class));
        }
    }
}