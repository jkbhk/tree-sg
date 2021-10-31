package com.example.treesg;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import androidx.fragment.app.DialogFragment;

import com.google.firebase.database.collection.LLRBNode;

import org.w3c.dom.Text;

public class TreeDialog extends DialogFragment {

    private static final String TAG = "TreeDialog";
    private TextView mActionCancel;


    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tree_dialog, container, false);
        mActionCancel = view.findViewById(R.id.txtclose);
        TextView speciesName = view.findViewById(R.id.speciesName);
        TextView age = view.findViewById(R.id.age);
        TextView girth = view.findViewById(R.id.girth);
        TextView height = view.findViewById(R.id.height);
        TextView commonName = view.findViewById(R.id.common_name);
        Button hugTree = view.findViewById(R.id.hug_tree);
        Button makePost = view.findViewById(R.id.make_post);
        TextView pointsEarned = view.findViewById(R.id.points_earned);


        Bundle mArgs = getArguments();
        String TreeName = mArgs.getString("species_name");
        String TreeCommonName = mArgs.getString("common_name");
        String TreeHeight = mArgs.getString("height");
        String TreeAge = mArgs.getString("age");
        String TreeGirth = mArgs.getString("girth");
        Boolean withinRange = mArgs.getBoolean("WithinRange");
        speciesName.setText(TreeName);
        age.setText(TreeAge);
        height.setText(TreeHeight);
        girth.setText(TreeGirth);
        if (!withinRange) {
            hugTree.setVisibility(View.GONE);
        }
        if (TreeCommonName != null) {
            commonName.setText("Common name: " + TreeCommonName);
        }


        mActionCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG, "onClick: closing dialog");
                getDialog().dismiss();
            }
        });

        hugTree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                hugTree.setBackgroundColor(Color.GRAY);
                hugTree.setEnabled(false);
                pointsEarned.setVisibility(View.VISIBLE);
                makePost.setVisibility(View.VISIBLE);
            }
        });

        return view;


    }



}
