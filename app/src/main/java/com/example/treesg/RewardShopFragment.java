package com.example.treesg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treesg.databinding.ActivityMainBinding;
import com.example.treesg.databinding.RewardListBinding;

import java.util.ArrayList;


public class RewardShopFragment extends Fragment {

    public static TextView pointHolder2;
    Integer tempPoint = UserManager.instance.getCurrentUser().getPoints();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_reward_shop, container, false);

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pointHolder2 = view.findViewById(R.id.pointHolder2);
        pointHolder2.setText(tempPoint.toString());

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RewardRecyclerView rewardRecyclerView = new RewardRecyclerView(RewardManager.instance.getRewardArray());

        recyclerView.setAdapter(rewardRecyclerView);
    }

}