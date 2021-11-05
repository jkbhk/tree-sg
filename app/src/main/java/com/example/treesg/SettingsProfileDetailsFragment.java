package com.example.treesg;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.treesg.Treedebugger;
import com.example.treesg.User;
import com.example.treesg.UserManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.treesg.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import de.hdodenhof.circleimageview.CircleImageView;

public class SettingsProfileDetailsFragment extends Fragment {
    Button Save,ChangePP;
    EditText textName, textUsername, textDescription;
    CircleImageView ProfilePic;
    private User user;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    StorageReference storageProfilePicture;
    String stringURI;
    Uri temp;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v;
        v = inflater.inflate(R.layout.fragment_settings_profiledetails, container, false);
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        return v;
    }
    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = UserManager.instance.getCurrentUser();
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        storageProfilePicture = FirebaseStorage.getInstance().getReference();
        StorageReference profileRef = storageProfilePicture.child("profile.jpg");


        Save = getView().findViewById(R.id.button9);
        textName = getView().findViewById(R.id.Name);
        textUsername = getView().findViewById(R.id.Username);
        textDescription = getView().findViewById(R.id.Description);
        ChangePP = getView().findViewById(R.id.cpp);
        ProfilePic = getView().findViewById(R.id.pp);

        String imageUri = user.getProfilePic();
        temp = Uri.parse(imageUri);
        //Picasso.get().load(imageUri).into(ProfilePic);
        Glide.with(getContext()).load(UserManager.instance.getCurrentUser().getProfilePic()).placeholder(R.drawable.default_profile).into(ProfilePic);

        textName.setText(user.getFullName());
        textUsername.setText(user.getUsername());
        textDescription.setText(user.getUserDescription());

        ChangePP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(openGalleryIntent, 1000);
            }
        });


        Save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = textUsername.getText().toString();
                String fullname = textName.getText().toString();


                if (TextUtils.isEmpty(fullname)) {
                    textName.setError("Name cannot be empty");
                    return;
                }

                if (TextUtils.isEmpty(username)) {
                    textUsername.setError("Username cannot be empty");
                    return;
                }

                if (username.contains(" ")) {
                    textUsername.setError("Username cannot have spaces");
                    return;
                }

                user.setProfilePic(temp.toString());
                user.setUsername(username);
                user.setFullName(fullname);
                user.setUserDescription(textDescription.getText().toString());
                // disable the button so they cant spam firebase
                // then enable it back on callback
                Save.setEnabled(false);
                UserManager.instance.updateUserAsync(user.getUserID(), ()->{
                    Toast.makeText(getActivity(), "profile updated", Toast.LENGTH_SHORT).show();
                    Save.setEnabled(true);});
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == Activity.RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            //ProfilePic.setImageURI(selectedImage);
            uploadImageToFirebase(selectedImage);
        }
    }

    private void uploadImageToFirebase(Uri imageUri){
        StorageReference fileRef = storageProfilePicture.child("users/"+user.getUserID()+"profile.jpg");
        fileRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(getActivity(),"Image Uploaded",Toast.LENGTH_SHORT).show();
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        temp = uri;
                        //Picasso.get().load(uri).into(ProfilePic);
                        Glide.with(getContext()).load(temp).placeholder(R.drawable.default_profile).into(ProfilePic);
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getActivity(),"Image Upload Failed",Toast.LENGTH_SHORT).show();
            }
        });
    }
}
