package com.example.treesg;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PointsAdapter extends RecyclerView.Adapter<PointsAdapter.PointsViewHolder> {
    ArrayList<Points> Ulist = null;
    Context context;
    int i = 1;

    public PointsAdapter(Context context, ArrayList<Points> list) {
        this.Ulist = list;
        this.context = context;
    }

    @NonNull
    @Override
    public PointsAdapter.PointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.points_items, parent, false);
        return new PointsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull PointsViewHolder holder, int position) {
        Points user = Ulist.get(position);
        holder.nameText.setText(user.getUsername());
        holder.pointsText.setText(String.valueOf(user.getPoints()));
        holder.rank.setText(String.valueOf(position+1));
    }

    @Override
    public int getItemCount() {
        return Ulist.size();
    }

    public class PointsViewHolder extends RecyclerView.ViewHolder {
        TextView nameText;
        TextView pointsText;
        TextView rank;
        public PointsViewHolder(@NonNull View itemView){
            super(itemView);

            nameText = itemView.findViewById(R.id.userName);
            pointsText = itemView.findViewById(R.id.userPoints);
            rank = itemView.findViewById(R.id.pointsRank);
        }
    }
    public void sorting(ArrayList<Points> mPoints) {
        int i;
        int j;
        for (i=1; i<mPoints.size(); i++){
            for (j=i; j>0; j--){
                if (mPoints.get(j).getPoints() > mPoints.get(j-1).getPoints()){
                    int tempP1 = mPoints.get(j).getPoints();
                    String tempU1 = mPoints.get(j).getUsername();
                    int tempP2 = mPoints.get(j-1).getPoints();
                    String tempU2 = mPoints.get(j-1).getUsername();
                    mPoints.get(j-1).setPoints(tempP1);
                    mPoints.get(j-1).setUsername(tempU1);
                    mPoints.get(j).setPoints(tempP2);
                    mPoints.get(j).setUsername(tempU2);
                }
                else break;
            }
        }
    }
}
