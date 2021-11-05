package com.example.treesg;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SetUserActivity extends AppCompatActivity {
    EditText setUser;
    Button confirmBtn;
    TextView welcomeMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.set_user);

        setUser = findViewById(R.id.setUser);
        confirmBtn = findViewById(R.id.confirmBtn);
        welcomeMsg = findViewById(R.id.promtText);

        confirmBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = setUser.getText().toString().trim();

                if(TextUtils.isEmpty(username)){
                    setUser.setError("Set a name so that we can address you!:)");
                    return;
                }

                UserManager.instance.getCurrentUser().setUsername(username);
                UserManager.instance.getCurrentUser().setIsNew(false);
                UserManager.instance.updateUserAsync(UserManager.instance.getCurrentUser().getUserID());
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();

            }
        });
    }
}