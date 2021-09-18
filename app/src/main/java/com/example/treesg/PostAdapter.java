package com.example.treesg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder> {

    Post[] allPosts;
    Context context;

    public PostAdapter(Post[] p){
        allPosts = p;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {

        if(context == null){
            context = parent.getContext();
        }

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.post_layout,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.postImage.setImageResource(allPosts[position].getPostImage());
        holder.postDescription.setText(allPosts[position].getDescription());
    }

    @Override
    public int getItemCount() {
        return allPosts.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView postImage;
        TextView postDescription;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.imageView);
            postDescription = itemView.findViewById(R.id.textView3);

        }
    }
}
