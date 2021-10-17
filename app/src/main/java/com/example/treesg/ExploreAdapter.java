package com.example.treesg;

import android.content.Context;
import android.media.Image;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class ExploreAdapter extends RecyclerView.Adapter<ExploreAdapter.ViewHolder> {

    Context context;
    String[] mostTrendingTags;
    ArrayList<Post> currentBundle;

    public ExploreAdapter(String[] trendingTags){

        mostTrendingTags = trendingTags;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if(context == null){
            context = parent.getContext();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // viewholders are the individual layouts  (eg. trending_bundle_layout)
        // our adapter creates these layouts for each item we pass into our array of choice in the contructor
        View view = layoutInflater.inflate(R.layout.trending_bundle_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        Treedebugger.log("inside adapt");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        currentBundle = ExploreController.instance.getBundle(mostTrendingTags[position]);


        if(currentBundle != null) {
            if (currentBundle.size() >=3){
                Glide.with(context).load(currentBundle.get(0).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.top);
                Glide.with(context).load(currentBundle.get(1).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.mid);
                Glide.with(context).load(currentBundle.get(2).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.bot);
            }else if(currentBundle.size() == 2){
                Glide.with(context).load(currentBundle.get(0).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.top);
                Glide.with(context).load(currentBundle.get(1).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.mid);
                Glide.with(context).load(currentBundle.get(0).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.bot);
            }else{
                Glide.with(context).load(currentBundle.get(0).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.top);
                Glide.with(context).load(currentBundle.get(0).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.mid);
                Glide.with(context).load(currentBundle.get(0).getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.bot);
            }

            holder.hashtag.setText(mostTrendingTags[position]);
        }

    }

    @Override
    public int getItemCount() {
        return mostTrendingTags.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView top;
        ImageView mid;
        ImageView bot;
        TextView hashtag;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            top = itemView.findViewById(R.id.iv_bundle_top);
            mid = itemView.findViewById(R.id.iv_bundle_middle);
            bot = itemView.findViewById(R.id.iv_bundle_bot);
            hashtag = itemView.findViewById(R.id.tv_bundle_hashtag);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.navigationController.navigate(R.id.navigation_trending);

                }
            });

        }
    }
}
