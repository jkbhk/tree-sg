package com.example.treesg;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class UserAnnouncement extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private AnnouncementAdapter mAdapter;

    //private DatabaseReference mDatabaseRef;
    private ArrayList<Announcement> mAnns = null;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_announcement);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        mRecyclerView = findViewById(R.id.ann_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        db = FirebaseFirestore.getInstance();
        mAnns = new ArrayList<Announcement>();
        mAdapter = new AnnouncementAdapter(UserAnnouncement.this, mAnns);

        mRecyclerView.setAdapter(mAdapter);

        EventChangeListener();
        /*
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("Announcement");

        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot postSnapshot : snapshot.getChildren()){
                    Announcement announcement = postSnapshot.getValue(Announcement.class);
                    mAnns.add(announcement);
                }
                mAdapter = new AnnouncementAdapter(UserAnnouncement.this, mAnns);

                mRecyclerView.setAdapter(mAdapter);
            };

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserAnnouncement.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void EventChangeListener() {
        db.collection("Announcement")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (error != null) {

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc: value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                mAnns.add(dc.getDocument().toObject(Announcement.class));
                            }

                            mAdapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }

}