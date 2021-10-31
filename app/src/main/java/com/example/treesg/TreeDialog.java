package com.example.treesg;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.api.Distribution;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.collection.LLRBNode;
import com.google.firebase.database.core.utilities.Tree;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class TreeDialog extends DialogFragment {

    private static final String TAG = "TreeDialog";
    private static final int PICK_IMAGE_REQUEST = 1;
    private static final int RESULT_OK = 2;

    private TextView mActionCancel;
    StorageReference storagePost;
    static Uri imageUri;




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
        LinearLayout information = view.findViewById(R.id.information);
        LinearLayout inputs = view.findViewById(R.id.inputs);
        LinearLayout bottom = view.findViewById(R.id.bottom);
        ImageView toPost = view.findViewById(R.id.IV_treeDialog_photoToPost);
        ImageButton imageButton = view.findViewById(R.id.imageButton);
        EditText postDescription = view.findViewById(R.id.ET_postDescription);
        TextView dialogText = view.findViewById(R.id.Dialog_text);
        Button upload = view.findViewById(R.id.upload);
        storagePost = FirebaseStorage.getInstance().getReference();




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
                UserManager.instance.incrementPointsAsync(2,null);
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
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
                Treedebugger.log("Edittext: " + postDescription.getText().toString());
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PostDataManager.instance.createNewPost(UserManager.instance.getCurrentUser(), postDescription.getText().toString(), imageUri.toString(), "Jurong", null);
                //Toast.makeText(getContext(), "Post Uploading..." , Toast.LENGTH_SHORT).show();
                dialogText.setText("Post Uploaded! You have been awarded an additional 3 points.");
                UserManager.instance.incrementPointsAsync(3,null);
                postDescription.setVisibility(View.GONE);
                imageButton.setVisibility(View.GONE);
                upload.setVisibility(View.GONE);

            }
        });



        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            imageUri = selectedImage;
            //ProfilePic.setImageURI(selectedImage);
            uploadImageToFirebase(selectedImage);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadImageToFirebase(Uri imageUri) {
        Treedebugger.log(imageUri.toString());
        StorageReference fileRef = storagePost.child("users/" + "1." + getFileExtension(imageUri)); //UUID.randomUUID().toString()
            fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getActivity(), "Image Uploaded", Toast.LENGTH_SHORT).show();
                    fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                        }
                    });
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(getActivity(), "Image Upload Failed", Toast.LENGTH_SHORT).show();

                }
            });

    }



}
