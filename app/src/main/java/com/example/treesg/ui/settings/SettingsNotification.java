package com.example.treesg.ui.settings;

import android.os.Bundle;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;

import com.example.treesg.R;

public class SettingsNotification extends AppCompatActivity {
    Button on, off;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_notification);

        on = findViewById(R.id.button6);
        off = findViewById(R.id.button7);
    }
}
