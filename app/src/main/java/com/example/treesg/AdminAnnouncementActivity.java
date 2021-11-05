//adminAnnouncement
package com.example.treesg;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Timestamp;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.HashMap;


public class AdminAnnouncementActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    Button back_button;
    Button post_button;
    ImageButton select_image_button;
    EditText post_text;
    ImageView tree_image_view;
    ProgressBar post_progress;
    Uri imageUri;
    Calendar calendar;

    StorageReference storageRef;
    DatabaseReference databaseRef;

    private StorageTask uploadPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_announcement);

        back_button = findViewById(R.id.backButton);
        post_button = findViewById(R.id.postButton);
        select_image_button = findViewById(R.id.imageButton);
        post_text = findViewById(R.id.textInputEditAnnouncement);
        tree_image_view = findViewById(R.id.uploadedImage);
        post_progress = findViewById(R.id.postProgress);
        post_progress.setVisibility(View.INVISIBLE);

        storageRef = FirebaseStorage.getInstance().getReference("uploads");
        databaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), AdminActivity.class));
            }
        });

        select_image_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });

        post_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (uploadPost != null && uploadPost.isInProgress()) {
                    Toast.makeText(AdminAnnouncementActivity.this, "Upload in progress", Toast.LENGTH_SHORT).show();
                } else {
                    uploadFile();
                }
            }
        });
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
        && data != null && data.getData() != null) {
            imageUri = data.getData();
            Picasso.get().load(imageUri).into(tree_image_view);
        }
    }

    private String getFileExtension(Uri uri) {
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }

    private void uploadFile() {
        if (imageUri != null) {
            StorageReference fileReference = storageRef.child(System.currentTimeMillis()
                + "." + getFileExtension(imageUri));

            uploadPost = fileReference.putFile(imageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            post_progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(AdminAnnouncementActivity.this, "Upload Successful", Toast.LENGTH_LONG).show();
                            fileReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String url = uri.toString();
                                    //Upload upload = new Upload(et_localization, url);
                                   // String uploadId = mDataBaseRef.push().getKey();
                                    //mDataBaseRef.child(uploadId).setValue(upload);
                                    Announcement announcement = new Announcement(post_text.getText().toString().trim(),
                                            url);
                                    String uploadId = databaseRef.push().getKey();
                                    String getAnnouncement = announcement.getAnnouncementPost().toString();
                                    String getUrl = announcement.getImageURL().toString();
                                    HashMap<String, Object> hashMap =  new HashMap<>();
                                    hashMap.put("announcementPost", getAnnouncement);
                                    hashMap.put("imageURL", getUrl);
                                    hashMap.put("notificationDate", Timestamp.now().toDate());
                                    FirebaseFirestore.getInstance().collection("Announcement")
                                            .document(uploadId)
                                            .set(hashMap)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void unused) {
                                                    Log.d("databaseAUploadS", "Successful upload");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.d("databaseAUploadF", "Unsuccessful upload");
                                                }
                                            });
                                }
                            });
                            /*Announcement announcement = new Announcement(post_text.getText().toString().trim(),
                                    taskSnapshot.getStorage().getDownloadUrl().getResult().toString());
                            String uploadId = databaseRef.push().getKey();
                            String getAnnouncement = announcement.getAnnouncementPost().toString();
                            String getUrl = announcement.getImageURL().toString();*/
                            /*
                            HashMap<String, Object> hashMap =  new HashMap<>();
                            hashMap.put("announcementPost", getAnnouncement);
                            hashMap.put("imageURL", getUrl);
                            FirebaseFirestore.getInstance().collection("Announcement")
                                    .document(uploadId)
                                    .set(hashMap)
                                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void unused) {
                                            Log.d("databaseAUploadS", "Successful upload");
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d("databaseAUploadF", "Unsuccessful upload");
                                        }
                                    });*/
                            final Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    // Do something after 5s = 5000ms
                                    startActivity(new Intent(getApplicationContext(), AdminActivity.class));
                                }
                            }, 500);
                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            post_progress.setVisibility(View.INVISIBLE);
                            Toast.makeText(AdminAnnouncementActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            post_progress.setVisibility(View.VISIBLE);
                        }
                    });
        } else {
            Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show();
        }
    }
}



