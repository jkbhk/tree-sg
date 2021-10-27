package com.example.treesg.ui.settings;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import com.example.treesg.Register;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import com.example.treesg.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SettingsManager extends AppCompatActivity {
    Button mPrivacy, mProDetails, mNotification, mLogOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_main);

        mPrivacy = findViewById(R.id.button2);
        mProDetails = findViewById(R.id.button3);
        mNotification = findViewById(R.id.button4);
        mLogOut = findViewById(R.id.button5);

        mPrivacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingsPrivacy.class));
            }
        });

        mProDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingsProfileDetails.class));
            }
        });

        mNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SettingsNotification.class));
            }
        });

        mLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), SettingsLogOut.class));
            }
        });
    }
}
