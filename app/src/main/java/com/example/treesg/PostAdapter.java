package com.example.treesg;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {

        Post current = allPosts[position];

        //test
        holder.post = current;
        UserManager.instance.getUserByID(current.getFrom(),(User u)->{
            // elements that depend on user
            holder.postCreator.setText(u.getFullName());
            holder.postDescription.setText(Html.fromHtml("<b>"+u.getFullName()+"</b>" + " "+current.getDescription()));
        });

        Glide.with(context).load(current.getPostImage()).placeholder(R.drawable.nature_placeholder).into(holder.postImage);
        holder.postLocation.setText(current.getLocation());
        Glide.with(context).load(current.getProfilePic()).placeholder(R.drawable.simu_liu).into(holder.postCreatorProfileImage);
        holder.postLikes.setText(String.format("%,d",current.getLikes())+" likes");
        String commentTxt = current.getComments() <= 1 ? "View 1 comment" : "View all " + String.format("%,d",current.getComments()) + " comments";
        if(current.getComments()<1)
            commentTxt = "";

        holder.postComments.setText(commentTxt);
        holder.likeImage.setImageResource(current.isLiked() ? R.drawable.heart : R.drawable.like);
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
        ImageView likeImage;
        CardView messageButton;
        CardView shareButton;

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
            likeImage = itemView.findViewById(R.id.iv_post_heart);
            messageButton = itemView.findViewById(R.id.cv_post_message);
            shareButton = itemView.findViewById(R.id.cv_post_share);

            likeButton.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onClick(View v) {

                    if(!post.isLiked()){
                        likeImage.setImageResource(R.drawable.heart);
                        post.setLiked(true);
                        UserManager.instance.addToLikes(post.getPostID());

                        postLikes.setText(String.format("%,d",post.getLikes()+1)+" likes");
                        post.setLikes(post.getLikes()+1);
                        PostDataManager.instance.incrementLikes(post.getPostID(),1);
                    }else{
                        likeImage.setImageResource(R.drawable.like);
                        post.setLiked(false);
                        UserManager.instance.removeFromLikes(post.getPostID());

                        postLikes.setText(String.format("%,d",post.getLikes()-1)+" likes");
                        post.setLikes(post.getLikes()-1);
                        PostDataManager.instance.incrementLikes(post.getPostID(),-1);

                    }
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

            messageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Treedebugger.log("looking at message");
                    //PostDataManager.instance.createNewPost(UserManager.instance.getCurrentUser(),"test post #nosleep #dead","1634408739998","HQ",()->{Treedebugger.log("post created.");});
                }
            });

            shareButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Treedebugger.log("sharing this message");

                    // keep for reference, use this implementation for creating posts
                    FirebaseStorage.getInstance().getReference("uploads/simu_liu.png").getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            String m = task.getResult().toString();
                            Treedebugger.log(m);
                        }
                    });

                }



            });



        }
    }
}
