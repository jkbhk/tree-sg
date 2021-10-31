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
import com.google.firebase.database.core.utilities.Tree;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    ImageView rewardBtn;
    ImageView settingsBtn;
    ImageView postBtn;
    CircleImageView profileImage;
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

        profileImage = getView().findViewById(R.id.tv_profile_profileimage);
        usernameText = getView().findViewById(R.id.tv_profile_username);
        descriptionText = getView().findViewById(R.id.tv_profile_description);

        Glide.with(getContext()).load(UserManager.instance.getCurrentUser().getProfilePic()).placeholder(R.drawable.default_profile).into(profileImage);
        usernameText.setText(""+UserManager.instance.getCurrentUser().getUsername());
        descriptionText.setText(""+UserManager.instance.getCurrentUser().getUserDescription());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rv_profile_recyclerview);

        PostDataManager.instance.retreiveAllPosts(()->{
            Post[] temp = PostDataManager.instance.getPostArrayByUser(UserManager.instance.getCurrentUser());
            Treedebugger.log(""+temp.length);
            ProfileRecyclerViewAdapter profileRecyclerViewAdapter = new ProfileRecyclerViewAdapter(temp);
            recyclerView.setLayoutManager(new GridLayoutManager(getContext(), 3));
            recyclerView.setAdapter(profileRecyclerViewAdapter);
        });

        rewardBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                MainActivity.navigationController.navigate(R.id.navigation_reward);

            }
        });
      
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigationController.navigate(R.id.navigation_settings);
            }
        });

    }

}