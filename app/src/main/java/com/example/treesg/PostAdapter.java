package com.example.treesg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

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

        Post current = allPosts[position];

        //test


        holder.post = current;
        loadImage(holder.postImage,current.getPostImage());
        //holder.postImage.setImageResource(current.getPostImage());
        holder.postDescription.setText(Html.fromHtml(current.getDescriptionWithName()));
        holder.postCreator.setText(current.getFrom());
        holder.postLocation.setText(current.getLocation());
        loadImage(holder.postCreatorProfileImage, current.getProfilePic());
        //holder.postCreatorProfileImage.setImageResource(current.getProfilePic());
        holder.postLikes.setText(String.format("%,d",current.getLikes())+" likes");
        String commentTxt = current.getComments() <= 1 ? "View 1 comment" : "View all " + String.format("%,d",current.getComments()) + " comments";
        holder.postComments.setText(commentTxt);
    }

    private void loadImage(ImageView iv, String postfix){
        String tempURL = "uploads/" + postfix + ".jpg";
        StorageReference sr = FirebaseStorage.getInstance().getReference().child(tempURL);
        try {
            File localFile = File.createTempFile("treeretrievetest","jpg");
            sr.getFile(localFile).addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
                    Bitmap bitmap = BitmapFactory.decodeFile(localFile.getAbsolutePath());
                    iv.setImageBitmap(bitmap);
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return allPosts.length;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView postImage;
        TextView postDescription;
        TextView postCreator;
        TextView postLocation;
        ImageView postCreatorProfileImage;
        TextView postLikes;
        TextView postComments;
        CardView likeButton;

        Post post;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            postImage = itemView.findViewById(R.id.iv_post_image);
            postDescription = itemView.findViewById(R.id.tv_post_description);
            postCreator = itemView.findViewById(R.id.tv_post_profile_name);
            postLocation = itemView.findViewById(R.id.tv_post_location);
            postCreatorProfileImage = itemView.findViewById(R.id.iv_post_profile_pic);
            postLikes = itemView.findViewById(R.id.tv_post_likes);
            postComments = itemView.findViewById(R.id.tv_post_view_comments);
            likeButton = itemView.findViewById(R.id.cv_post_like);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ImageView iv = itemView.findViewById(R.id.iv_post_heart);
                    post.setLikes(post.getLikes() + (post.isLiked() ? -1 : 1));
                    iv.setImageResource(post.isLiked() ? R.drawable.like : R.drawable.heart);
                    postLikes.setText(String.format("%,d",post.getLikes())+" likes");
                    post.setLiked(!post.isLiked());

                }
            });

            postCreatorProfileImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MainActivity.navigationController.navigate(R.id.navigation_profile);
                    Log.println(Log.DEBUG,"debugging","hello!");
                }
            });

            postImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.println(Log.DEBUG,"debugging",postCreator.getText().toString());
                }
            });

        }
    }
}
