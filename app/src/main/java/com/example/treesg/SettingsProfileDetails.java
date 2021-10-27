package com.example.treesg;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.treesg.Treedebugger;
import com.example.treesg.User;
import com.example.treesg.UserManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.treesg.R;
import com.example.treesg.login;

import org.jetbrains.annotations.NotNull;

public class SettingsProfileDetails extends Fragment {
    Button Save;
    EditText textName, textUsername, textDescription;
    ImageView ProfilePic;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_settings_profiledetails, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = UserManager.instance.getCurrentUser();

        Save = getView().findViewById(R.id.button9);
        textName = getView().findViewById(R.id.Name);
        textUsername = getView().findViewById(R.id.Username);
        textDescription = getView().findViewById(R.id.Description);
        ProfilePic = getView().findViewById(R.id.pp);

        //ProfilePic = user.getProfilePic();
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
                    Toast.makeText(getActivity(), "profile updated", Toast.LENGTH_SHORT).show();
                    Save.setEnabled(true);});
            }
        });
    }
}
