package com.example.treesg;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.treesg.R;

import org.jetbrains.annotations.NotNull;

public class SettingsNotification extends Fragment {
    Button on, off;
    private User user;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_settings_notifications, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        on = getView().findViewById(R.id.button6);
        off = getView().findViewById(R.id.button7);
        user = UserManager.instance.getCurrentUser();

        on.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNotifications(true);
                on.setEnabled(false);
                UserManager.instance.updateUserAsync(user.getUserID(), ()->{
                    Toast.makeText(getActivity(), "Notification turned on", Toast.LENGTH_SHORT).show();
                    on.setEnabled(true);});
            }
        });
        off.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user.setNotifications(false);
                off.setEnabled(false);
                UserManager.instance.updateUserAsync(user.getUserID(), ()->{
                    Toast.makeText(getActivity(), "Notification turned off", Toast.LENGTH_SHORT).show();
                    off.setEnabled(true);});
            }
        });

    }
}
