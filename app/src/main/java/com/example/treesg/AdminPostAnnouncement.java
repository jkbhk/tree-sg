package com.example.treesg;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AdminPostAnnouncement extends AppCompatActivity {
    Button backButton, postButton;
    ImageButton imageButton;
    ImageView uploadedImage;
    EditText textInputEditAnnouncement;
    StorageReference storageReference;
    ProgressDialog progressDialog;
    Uri imageUri;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_post_announcement);
        backButton = (Button)findViewById(R.id.backButton);
        postButton = (Button)findViewById(R.id.postButton);
        imageButton = (ImageButton)findViewById(R.id.imageButton);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });
        backButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                startActivity(new Intent(getApplicationContext(), Admin.class));
            }
        });
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                uploadAnnouncement();
            }
        });
    }

    private void uploadAnnouncement() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading Post...");
        progressDialog.show();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.UK);
        Date now = new Date();
        String fileName = formatter.format(now);
        storageReference = FirebaseStorage.getInstance().getReference("images/" + fileName);

        storageReference.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        String TempPost = textInputEditAnnouncement.getText().toString().trim();
                        uploadedImage.setImageURI(null);
                        Toast.makeText(AdminPostAnnouncement.this, "Successfully Posted", Toast.LENGTH_SHORT).show();
                        if (progressDialog.isShowing())
                            progressDialog.dismiss();
                        Announcement announcmentInfo = new Announcement(TempPost, taskSnapshot.getUploadSessionUri().toString());
                        String announcmentUploadID = databaseReference.push().getKey();
                        databaseReference.child(announcmentUploadID).setValue(announcmentInfo);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                if (progressDialog.isShowing())
                    progressDialog.dismiss();
                Toast.makeText(AdminPostAnnouncement.this, "Unsuccessful Post", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void selectImage() {
        Intent intent = new Intent();
        intent.setType("images/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, 100);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && data != null && data.getData() != null){
            imageUri = data.getData();
            uploadedImage.setImageURI(imageUri);
        }
    }
}
