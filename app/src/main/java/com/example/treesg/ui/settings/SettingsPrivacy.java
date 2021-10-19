package com.example.treesg.ui.settings;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.treesg.R;
import com.example.treesg.login;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SettingsPrivacy extends AppCompatActivity {
    EditText Curr, New, Confirm;
    Button ConfirmButton;
    private FirebaseUser user;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.settings_privacy);
        Curr = findViewById(R.id.CurrentPassword);
        New = findViewById(R.id.NewPassword);
        Confirm = findViewById(R.id.ConfirmPassword);
        ConfirmButton = findViewById(R.id.button8);
        user = fAuth.getCurrentUser();
        final String email = user.getEmail();

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentPassword = Curr.getText().toString().trim();
                String newPassword = New.getText().toString().trim();
                String confirmPassword = Confirm.getText().toString().trim();

                if(TextUtils.isEmpty(currentPassword)){
                    Curr.setError("Current Password is required");
                    return;
                }
                if(TextUtils.isEmpty(newPassword)){
                    New.setError("New Password is required");
                    return;
                }
                if(TextUtils.isEmpty(confirmPassword)){
                    Confirm.setError("Confirm New Password is required");
                    return;
                }
                if(newPassword.length() < 8){
                    New.setError("Password set must be at least 8 characters.");
                    return;
                }
                if(!(newPassword.equals(confirmPassword))){
                    Confirm.setError("Confirm New Password must match New Password.");
                    return;
                }

                fAuth.signInWithEmailAndPassword(email, currentPassword) . addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            user.updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (!task.isSuccessful()) {
                                        Toast.makeText(SettingsPrivacy.this, "Password Changed successfully.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SettingsPrivacy.this, "Error! " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                        }else{
                            Toast.makeText(SettingsPrivacy.this, "Password entered incorrectly" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
