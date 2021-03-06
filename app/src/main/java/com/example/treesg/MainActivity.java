package com.example.treesg;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.core.app.NotificationManagerCompat;

import com.example.treesg.databinding.ActivityMainBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.core.utilities.Tree;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    public static MainActivity instance;
    private ActivityMainBinding binding;
    //RewardListBinding rewardListBinding;
    public static NavController navigationController;
    public static FragmentManager fragmentManager;
    private User user;
    public static androidx.fragment.app.FragmentManager fragmentSupportManager;
    private static NotificationManagerCompat notificationManager1;
    private noti noti;
    private Calendar calendar;
    //private AppManager appManager;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noti noti = new noti(this);
        noti.createNotificationChannels();
        user = UserManager.instance.getCurrentUser();
        // intitialize app manager
        //appManager = new AppManager();
        //appManager.initialize();
        instance = this;
        fragmentManager = getFragmentManager();
        fragmentSupportManager = getSupportFragmentManager();
        notificationManager1 = NotificationManagerCompat.from(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        //rewardListBinding = RewardListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //setContentView(rewardListBinding.getRoot());

        BottomNavigationView navView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
                R.id.navigation_home, R.id.navigation_map, R.id.navigation_notifications,
                R.id.navigation_explore, R.id.navigation_profile)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(binding.navView, navController);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        navigationController = navController;
        user = UserManager.instance.getCurrentUser();

        if(user.getNotifications()){
            FirebaseFirestore.getInstance().collection("Announcement").addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Treedebugger.log("Listen Failed");
                        return;
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.add(Calendar.MINUTE, -1);
                    Date before = calendar.getTime();
                    Calendar calendar1 = Calendar.getInstance();
                    Date until = calendar1.getTime();
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        Date test = dc.getDocument().getDate("notificationDate");
                        if (dc.getType() == DocumentChange.Type.ADDED) {
                            if (!before.after(test) && !until.before(test)) sendChannel3(findViewById(android.R.id.content).getRootView());
                        }
                    }
                }
            });

            FirebaseFirestore.getInstance().collection(DatabaseManager.POSTS_COLLECTION).addSnapshotListener(new EventListener<QuerySnapshot>() {
                @Override
                public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (error != null) {
                        Treedebugger.log("Listen Failed");
                        return;
                    }
                    for (DocumentChange dc : value.getDocumentChanges()) {
                        if (dc.getType() == DocumentChange.Type.MODIFIED){
                            if(user.getUserID() == dc.getDocument().getString("post_creator")) sendChannel1(findViewById(android.R.id.content).getRootView());
                        }
                    }
                }
            });
        }
    }
    

    public void sendChannel1(View v){
        String textTitle = "Someone liked your post!";
        String textContent = "Click here to see!";

        Intent MainIntent = new Intent(this, MainActivity.class);
        MainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending1 = PendingIntent.getActivity(this,0,MainIntent,0);

        Notification notification = new NotificationCompat.Builder(this, noti.CHANNEL_1_ID)
                .setSmallIcon(R.drawable.like)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pending1)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager1.notify(1, notification);
    }

    /*public void sendChannel2(View v){
        String textTitle = "Someone just followed you!";
        String textContent = "Click here to see!";

        Intent MainIntent = new Intent(this, MainActivity.class);
        MainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pending1 = PendingIntent.getActivity(this,0,MainIntent,0);

        Notification notification = new NotificationCompat.Builder(this, noti.CHANNEL_2_ID)
                .setSmallIcon(R.drawable.profile_button)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pending1)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();


        notificationManager1.notify(2, notification);
    }*/

    public void sendChannel3(View v){
        String textTitle = "There is a new announcement!";
        String textContent = "Click here to see!";

        Intent MainIntent = new Intent(this, MainActivity.class);

        PendingIntent pending1 = PendingIntent.getActivity(this,0,MainIntent,0);
        MainIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        Notification notification = new NotificationCompat.Builder(this, noti.CHANNEL_3_ID)
                .setSmallIcon(R.drawable.trending_button)
                .setContentTitle(textTitle)
                .setContentText(textContent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setCategory(NotificationCompat.CATEGORY_MESSAGE)
                .setContentIntent(pending1)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .build();

        notificationManager1.notify(3, notification);
    }
}