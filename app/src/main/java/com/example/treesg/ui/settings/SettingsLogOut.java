package com.example.treesg.ui.settings;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

import com.example.treesg.R;
import com.example.treesg.login;

public class SettingsLogOut extends AppCompatActivity {
    Button confirmLogOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_logout);

        confirmLogOut = findViewById(R.id.button);

        confirmLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), login.class));
            }
        });
    }

}
