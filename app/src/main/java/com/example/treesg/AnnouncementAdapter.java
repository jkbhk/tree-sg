package com.example.treesg;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class AnnouncementAdapter extends RecyclerView.Adapter<AnnouncementAdapter.AnnouncementViewHolder> {
    private Context mContext;
    private ArrayList<Announcement> mAnns = null;

    public AnnouncementAdapter(Context context, ArrayList<Announcement> anns){
        this.mContext = context;
        this.mAnns= anns;
    }
    @NonNull
    @Override
    public AnnouncementAdapter.AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.announcement_image_item, parent, false);
        return new AnnouncementViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        Announcement annCurrent = mAnns.get(position);
        holder.annText.setText(annCurrent.getAnnouncementPost());
        Glide.with(mContext).load(mAnns.get(position).getImageURL()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mAnns.size();
    }

    public class AnnouncementViewHolder extends RecyclerView.ViewHolder {

        TextView annText;
        ImageView imageView;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);

            annText = itemView.findViewById(R.id.ann_text);
            imageView = itemView.findViewById(R.id.image_view_announcement);

        }
    }
}
