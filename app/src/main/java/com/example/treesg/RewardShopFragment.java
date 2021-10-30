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

        String[] name = {"1 Spin", "3 Spin", "2 Day 1 Night Staycation", "$100 Fairprice Coupon", "$100 Lazada Coupon", "$100 Shopee Coupon", "Plant Tree Opportunity"};
        String[] id = {"001", "002", "003", "004", "005", "006", "007",};
        String[] desc = {"Purchase 1 spin", "Purchase 3 spins", "Enjoy your day with a 2 day 1 night staycation at Genting Hotel!",
                "Enjoy a shopping spree at fairprice with this $100 coupon!", "Enjoy a shopping spree at Lazada with this $100 coupon!",
                "Enjoy a shopping spree at Shopee with this $100 coupon!", "An opportunity to plant your own personal tree in Singapore and call it yours!"};
        int[] price = {100, 250, 1000, 500, 500, 500, 1000};

        ArrayList<Reward> rewardArrayList = new ArrayList<Reward>();
        Reward[] rewardArray = new Reward[name.length];
        for (int i = 0; i < name.length; i++)
        {
            Reward reward = new Reward(name[i], id[i], desc[i], price[i]);
            rewardArray[i] = reward;
            //rewardArrayList.add(reward);
        }

        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        pointHolder2 = view.findViewById(R.id.pointHolder2);
        pointHolder2.setText(tempPoint.toString());

        String[] name = {"1 Spin", "3 Spin", "2D1N Staycation", "$100 Fairprice Coupon", "$100 Lazada Coupon", "$100 Shopee Coupon", "Plant Tree Opportunity"};
        String[] id = {"001", "002", "003", "004", "005", "006", "007",};
        String[] desc = {"Purchase 1 spin", "Purchase 3 spins", "Enjoy your day with a 2 day 1 night staycation at Genting Hotel!",
                "Enjoy a shopping spree at fairprice with this $100 coupon!", "Enjoy a shopping spree at Lazada with this $100 coupon!",
                "Enjoy a shopping spree at Shopee with this $100 coupon!", "An opportunity to plant your own personal tree in Singapore and call it yours!"};
        int[] price = {100, 250, 1000, 500, 500, 500, 1000};

        ArrayList<Reward> rewardArrayList = new ArrayList<Reward>();
        Reward[] rewardArray = new Reward[name.length];
        for (int i = 0; i < name.length; i++)
        {
            Reward reward = new Reward(name[i], id[i], desc[i], price[i]);
            rewardArray[i] = reward;
            //rewardArrayList.add(reward);
        }
        Treedebugger.log(""+ rewardArray.length);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.listView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        RewardRecyclerView rewardRecyclerView = new RewardRecyclerView(rewardArray);

        recyclerView.setAdapter(rewardRecyclerView);
    }

}