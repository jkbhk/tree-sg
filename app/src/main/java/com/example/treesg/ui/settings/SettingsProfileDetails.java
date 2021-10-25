package com.example.treesg.ui.settings;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.treesg.Treedebugger;
import com.example.treesg.User;
import com.example.treesg.UserManager;

import androidx.appcompat.app.AppCompatActivity;

import com.example.treesg.R;
import com.example.treesg.login;

public class SettingsProfileDetails extends AppCompatActivity {
    Button Save;
    EditText textName, textUsername, textDescription;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_profiledetails);

        user = UserManager.instance.getCurrentUser();

        Save = findViewById(R.id.button9);
        textName = findViewById(R.id.Name);
        textUsername = findViewById(R.id.Username);
        textDescription = findViewById(R.id.Description);

        // this does nothing btw
        //String Name = textName.getText().toString();
        //String Username = textUsername.getText().toString().trim();
        //String Description = textDescription.getText().toString();

        // load the existing values into the views
        textName.setText(user.getFullName());
        textUsername.setText(user.getUsername());
        textDescription.setText(user.getUserDescription());


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = textUsername.getText().toString();
                String fullname = textName.getText().toString();


                if (TextUtils.isEmpty(fullname)) {
                    textName.setError("Name cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    textUsername.setError("Username cannot be empty");
                    return;
                }

                if (username.contains(" ")) {
                    textUsername.setError("Username cannot have spaces");
                    return;
                }


                user.setUsername(username);
                user.setFullName(fullname);
                user.setUserDescription(textDescription.getText().toString());
                // disable the button so they cant spam firebase
                // then enable it back on callback
                Save.setEnabled(false);
                UserManager.instance.updateUserAsync(user.getUserID(), ()->{
                    Toast.makeText(getApplicationContext(), "profile updated", Toast.LENGTH_SHORT).show();
                    Save.setEnabled(true);});
            }
        });
    }
}
