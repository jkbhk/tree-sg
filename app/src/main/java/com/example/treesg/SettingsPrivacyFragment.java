package com.example.treesg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class SettingsPrivacyFragment extends Fragment {
    EditText Curr, New, Confirm;
    Button ConfirmButton;
    private FirebaseUser user;
    FirebaseAuth fAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_settings_privacy, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        fAuth = FirebaseAuth.getInstance();
        Curr = getView().findViewById(R.id.CurrentPassword);
        New = getView().findViewById(R.id.NewPassword);
        Confirm = getView().findViewById(R.id.ConfirmPassword);
        ConfirmButton = getView().findViewById(R.id.button8);
        String email = UserManager.instance.getCurrentUser().getEmail();
        //user = fAuth.getCurrentUser();
        //final String email = user.getEmail();

        ConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // u cannot trim their password without telling them
                // otherwise they would think their password works with spaces

                //String currentPassword = Curr.getText().toString().trim();
                //String newPassword = New.getText().toString().trim();
                //String confirmPassword = Confirm.getText().toString().trim();

                String currentPassword = Curr.getText().toString();
                String newPassword = New.getText().toString();
                String confirmPassword = Confirm.getText().toString();


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

                ConfirmButton.setEnabled(false);

                fAuth.signInWithEmailAndPassword(email, currentPassword) . addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Treedebugger.log("can sign in");

                            fAuth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> t) {
                                    if(t.isSuccessful()){
                                        Treedebugger.log("password updated.");
                                        Toast.makeText(getActivity(), "Password changed successfully." , Toast.LENGTH_SHORT).show();
                                    } else{
                                        Treedebugger.log("fail to change password.");
                                        Toast.makeText(getActivity(), "Failed to change password.", Toast.LENGTH_SHORT).show();
                                    }


                                    ConfirmButton.setEnabled(true);
                                }
                            });


                        }else{
                            ConfirmButton.setEnabled(true);
                            Treedebugger.log("current password is wrong.");
                            Toast.makeText(getActivity(), "Current password is wrong!." , Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });
    }
}
