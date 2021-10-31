package com.example.treesg;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

public class TreeDialog extends DialogFragment {

    private static final String TAG = "TreeDialog";
    private static final int GET_FROM_GALLERY = 3;
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
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        LinearLayout information = view.findViewById(R.id.information);
        LinearLayout inputs = view.findViewById(R.id.inputs);
        LinearLayout bottom = view.findViewById(R.id.bottom);



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
                UserManager.instance.incrementPointsAsync(20,null);
                hugTree.setBackgroundColor(Color.GRAY);
                hugTree.setEnabled(false);
                pointsEarned.setVisibility(View.VISIBLE);
                makePost.setVisibility(View.VISIBLE);
            }
        });

        makePost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                information.setVisibility(View.GONE);
                bottom.setVisibility(View.GONE);
                inputs.setVisibility(View.VISIBLE);
            }
        });

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI), GET_FROM_GALLERY);
            }
        });



        return view;

    }



}
