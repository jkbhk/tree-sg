package com.example.treesg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


public class RewardShopAdapter extends RecyclerView.Adapter<RewardShopAdapter.ViewHolder> {

    Context context;
    Reward[] rewards;

    public RewardShopAdapter(Reward[] rewards){
        this.rewards = rewards;
        Treedebugger.log(""+rewards.length);
    }

    @NonNull
    @Override
    public RewardShopAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(context == null){
            context = parent.getContext();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.reward_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RewardShopAdapter.ViewHolder holder, int position) {
        Reward current = rewards[position];

        if (current != null) {
            holder.reward = current;
            String rewardPrice = "" + current.pointsRequired + " points";
            holder.rewardName.setText(current.itemName.toString());
            holder.rewardDesc.setText(current.description.toString());
            holder.rewardPrice.setText(rewardPrice);
        }

    }

    @Override
    public int getItemCount() {
        return rewards.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView rewardName;
        TextView rewardDesc;
        TextView rewardPrice;
        LinearLayout rewardContainer;
        Reward reward;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            rewardName = itemView.findViewById(R.id.rewardName);
            rewardDesc = itemView.findViewById(R.id.rewardDescription);
            rewardPrice = itemView.findViewById(R.id.rewardPrice);
            rewardContainer = itemView.findViewById(R.id.rewardContainer);

            rewardContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    if (UserManager.instance.getCurrentUser().getPoints() < reward.pointsRequired)
                    {
                        RewardErrorDialog rewardErrorDialog = new RewardErrorDialog(reward);
                        rewardErrorDialog.show(MainActivity.fragmentSupportManager, "reward error dialog");
                    }
                    else
                    {
                        Treedebugger.log(""+rewardName.getText().toString());
                        RewardDialog rewardDialog = new RewardDialog(reward);
                        rewardDialog.show(MainActivity.fragmentSupportManager, "reward dialog");
                    }
                }
            });
        }
    }


}
