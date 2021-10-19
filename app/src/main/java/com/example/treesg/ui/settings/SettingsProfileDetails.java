package com.example.treesg.ui.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.example.treesg.User;
import com.example.treesg.UserManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.treesg.R;

public class SettingsProfileDetails extends AppCompatActivity {
    Button Save;
    EditText textName, textUsername, textDescription;
    private User user;
    UserManager BigBrother;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_profiledetails);

        Save = findViewById(R.id.button9);
        textName = findViewById(R.id.Name);
        textUsername = findViewById(R.id.Username);
        textDescription = findViewById(R.id.Description);
        user = BigBrother.getCurrentUser();

        String Name = textName.getText().toString();
        String Username = textUsername.getText().toString().trim();
        String Description = textDescription.getText().toString();

        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(Name)) {
                    textName.setError("Name cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(Username)) {
                    textUsername.setError("Name cannot be empty");
                    return;
                }

                user.setUsername(Username);
                user.setFullName(Name);
                
            }
        });
    }
}
