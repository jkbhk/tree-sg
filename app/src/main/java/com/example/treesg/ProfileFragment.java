package com.example.treesg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;


public class ProfileFragment extends Fragment {

    ImageView rewardBtn;
    ImageView settingsBtn;
    ImageView postBtn;
    ImageView profileImage;
    TextView usernameText;
    TextView descriptionText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v;
        v = inflater.inflate(R.layout.fragment_profile, container, false);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rewardBtn = getView().findViewById(R.id.rewardBtn);
        settingsBtn = getView().findViewById(R.id.settingsBtn);
        postBtn = getView().findViewById(R.id.tv_profile_postBtn);

        profileImage = getView().findViewById(R.id.tv_profile_profileimage);
        usernameText = getView().findViewById(R.id.tv_profile_username);
        descriptionText = getView().findViewById(R.id.tv_profile_description);

        Glide.with(getContext()).load(UserManager.instance.getCurrentUser().getProfilePic()).placeholder(R.drawable.default_profile).into(profileImage);
        usernameText.setText(""+UserManager.instance.getCurrentUser().getUsername());
        descriptionText.setText(""+UserManager.instance.getCurrentUser().getUserDescription());

        rewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.navigationController.navigate(R.id.navigation_reward);

                //replaceFragment(new RewardFragment());
            }
        });
      
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigationController.navigate(R.id.navigation_settings);
            }
        });

        postBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //PostDataManager.instance.createNewPost(UserManager.instance.getCurrentUser(), "pog you #pog", "asdf", "jurong", ()->{
                    //Toast.makeText(getActivity(), "Created Post", Toast.LENGTH_SHORT).show();
                //});
            }
        });



    }

}