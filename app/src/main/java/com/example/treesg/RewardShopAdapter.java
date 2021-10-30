package com.example.treesg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class RewardShopAdapter extends ArrayAdapter<Reward> {

    Context currentContext;

    public RewardShopAdapter(Context context, Reward[] rewardArrayList){
        //Reward[] shit = rewardArrayList.toArray();
        super(context, R.layout.supersimple, rewardArrayList);
        this.currentContext = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        //Reward reward = getItem(position);
        if (convertView == null)
        {
            //currentContext = getContext();
            convertView = LayoutInflater.from(currentContext).inflate(R.layout.supersimple, parent, false);
        }

        /*
        TextView rewardName = convertView.findViewById(R.id.rewardName);
        TextView rewardDesc = convertView.findViewById(R.id.rewardDescription);
        TextView rewardPrice = convertView.findViewById(R.id.rewardPrice);
        if (reward != null) {
            rewardName.setText(reward.itemName);
            rewardDesc.setText(reward.description);
            rewardPrice.setText(reward.pointsRequired);
        }


         */

        return super.getView(position, convertView, parent);
    }

}

