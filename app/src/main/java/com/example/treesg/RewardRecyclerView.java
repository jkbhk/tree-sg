package com.example.treesg;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import org.jetbrains.annotations.NotNull;


public class RewardRecyclerView extends RecyclerView.Adapter<RewardRecyclerView.ViewHolder> {

    Context context;
    Reward[] rewards;

    public RewardRecyclerView(Reward[] rewards){
        this.rewards = rewards;
        Treedebugger.log(""+rewards.length);
    }

    @NonNull
    @Override
    public RewardRecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(context == null){
            context = parent.getContext();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.reward_list,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RewardRecyclerView.ViewHolder holder, int position) {
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
                    Treedebugger.log(""+rewardName.getText().toString());
                    RewardDialog rewardDialog = new RewardDialog(reward);
                    rewardDialog.show(MainActivity.fragmentSupportManager, "reward dialog");
                }
            });
        }
    }


}
