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

    //RewardListBinding rewardListBinding;
    //private ListView listView;
    TextView pointHolder2;
    Integer tempPoint = 100;

    public static void Initialize(){
        ArrayList<Reward> rewards = new ArrayList<Reward>();
        Reward reward1 = new Reward("1 Spin", "001", "Purchase for 1 additional spin.", 100);
        Reward reward2 = new Reward("3 Spin", "002", "Purchase for 3 additional spin.", 250);
        Reward reward3 = new Reward("1 Spin", "003", "Purchase for 1 additional spin.", 100);
        Reward reward4 = new Reward("1 Spin", "004", "Purchase for 1 additional spin.", 100);
        Reward reward5 = new Reward("1 Spin", "005", "Purchase for 1 additional spin.", 100);
        Reward reward6 = new Reward("1 Spin", "006", "Purchase for 1 additional spin.", 100);
        Reward reward7 = new Reward("1 Spin", "007", "Purchase for 1 additional spin.", 100);
        Reward reward8 = new Reward("1 Spin", "008", "Purchase for 1 additional spin.", 100);
        Reward reward9 = new Reward("1 Spin", "009", "Purchase for 1 additional spin.", 100);
        Reward reward10 = new Reward("1 Spin", "010", "Purchase for 1 additional spin.", 100);

        rewards.add(reward1);
        rewards.add(reward1);
        rewards.add(reward1);
        rewards.add(reward1);
        rewards.add(reward1);
        rewards.add(reward1);
        rewards.add(reward1);
        rewards.add(reward1);
        rewards.add(reward1);
        rewards.add(reward1);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //Initialize();
        View rootView = inflater.inflate(R.layout.fragment_reward_shop, container, false);
        //rewardListBinding = RewardListBinding.inflate(getActivity().getLayoutInflater(), container, false);
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
        //RewardShopAdapter rewardShopAdapter = new RewardShopAdapter(getActivity().getApplicationContext(), rewardArrayList);
        //RewardShopAdapter rewardShopAdapter = new RewardShopAdapter(getActivity(), rewardArray);
        //ListView listView = (ListView) rootView.findViewById(R.id.listView);

        //String[] m = {"LOLOLOL", "CBCBCBCB"};

        //ArrayAdapter<Reward> testing = new ArrayAdapter<Reward>(getActivity(), R.layout.reward_list, rewardArrayList);

        //listView.setAdapter(rewardShopAdapter);
        /*listView.setClickable(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity().getApplicationContext(), "test", Toast.LENGTH_SHORT).show();
            }
        });*/

        return rootView;
        //return inflater.inflate(R.layout.fragment_reward_shop, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
        //pointHolder2 = view.findViewById(R.id.pointHolder2);
        //pointHolder2.setText(tempPoint.toString());
    }
}