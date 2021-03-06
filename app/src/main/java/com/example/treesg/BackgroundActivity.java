package com.example.treesg;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.Scanner;

public class BackgroundActivity extends AppCompatActivity {

    AppManager appManager;
    Boolean commandMode = false;
    Scanner sc = new Scanner(System.in);

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appManager = new AppManager();

        if(!commandMode){

            //if logged in or returning user
            FirebaseUser u = FirebaseAuth.getInstance().getCurrentUser();
            if(u != null){
                appManager.initialize();
                Treedebugger.log("previous session detected.");
                UserManager.instance.setCurrentUserAsync(u.getUid(),()->{
                    Treedebugger.log("user fetching complete, safe to proceed to homepage.");
                    Treedebugger.log("Welcome back " + UserManager.instance.getCurrentUser().getFullName());
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                });
            }else{
                // This is where the app starts.
                // For now we will start at login activity.
                // Change login.class to the your desired starting activity here.
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        }else{

            // test commands
            appManager.initialize();
            FirebaseAuth.getInstance().signInWithEmailAndPassword("alpha@gmail.com","12345678").addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {


                    //UserDao.updatePropertiesForAll();


                    //UserManager.instance.getUserByIDAsync("Z0BfmUgAmoaxm9DIMH0gAO15dzX2",(User u)->{
                        //UserDao.updatePropertiesForAll();
                        //PostDataManager.instance.createNewPost(u,"Anyone still awake?! #nosleep","cannot find","TreeSG HQ",()->{Treedebugger.log("created a fake post.");});
                    //});



                }
            });
        }

    }
}