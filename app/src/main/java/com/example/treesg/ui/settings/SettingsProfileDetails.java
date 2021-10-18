package com.example.treesg.ui.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.treesg.R;

public class SettingsProfileDetails extends AppCompatActivity {
    Button Save;
    EditText textName, textUsername, textDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_profiledetails);

        Save = findViewById(R.id.button9);
        textName = findViewById(R.id.Name);
        textUsername = findViewById(R.id.Username);
        textDescription = findViewById(R.id.Description);

        String Name = textName.getText().toString().trim();
        String Username = textUsername.getText().toString().trim();
        String Description = textDescription.getText().toString();


    }
}
