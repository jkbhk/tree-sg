package com.example.treesg;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProfileRecyclerViewAdapter extends RecyclerView.Adapter<ProfileRecyclerViewAdapter.ViewHolder> {

    Context context;
    Post[] postArray;

    public ProfileRecyclerViewAdapter(Post[] postArray){
        this.postArray = postArray;
    }

    @Override
    public ProfileRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (context == null){
            context = parent.getContext();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.cardview_post, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecyclerViewAdapter.ViewHolder holder, int position) {
        //holder.postPic.setImageResource(postList.get(position).getPostImage());
        //Glide.with(context).load(PostDataManager.instance.placeholder(R.drawable.default_profile).into(profileImage);
        Treedebugger.log(""+postArray[position].getPostImage());
        holder.post = postArray[position];
        Glide.with(context).load(postArray[position].getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.postPic);

    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView postPic;
        Post post;

        public ViewHolder (View itemView){
            super(itemView);
            postPic = itemView.findViewById(R.id.cv_profile_post_image);

        }
    }

    @Override
    public int getItemCount() {
        return postArray.length;
    }
}
