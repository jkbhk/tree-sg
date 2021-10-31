package com.example.treesg.ui.home;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.treesg.Announcement;
import com.example.treesg.AnnouncementAdapter;
import com.example.treesg.Points;
import com.example.treesg.PointsAdapter;
import com.example.treesg.R;
import com.example.treesg.databinding.FragmentHomeBinding;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;
    private RecyclerView mRecyclerView, pRV;
    private AnnouncementAdapter mAdapter;
    private PointsAdapter pointsAdapter;
    private ArrayList<Announcement> mAnns = null;
    private ArrayList<Points> mPoints = null;
    FirebaseFirestore db;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        /*
        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });*/
        return root;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();
        mRecyclerView = (RecyclerView) view.findViewById(R.id.ann_recycler_view1);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        pRV = (RecyclerView) view.findViewById(R.id.leaderboard_rv);
        pRV.setHasFixedSize(true);
        pRV.setLayoutManager(new LinearLayoutManager(getContext()));

        db = FirebaseFirestore.getInstance();
        mAnns = new ArrayList<Announcement>();
        mAdapter = new AnnouncementAdapter(getContext(), mAnns);
        mPoints = new ArrayList<Points>();
        pointsAdapter = new PointsAdapter(getContext(), mPoints);

        mRecyclerView.setAdapter(mAdapter);
        pRV.setAdapter(pointsAdapter);
        EventChangeListener();
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
        db.collection("user_test")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        for (DocumentChange dc: value.getDocumentChanges()){
                            if (dc.getType() == DocumentChange.Type.ADDED){
                                Points p = new Points(dc.getDocument().getString("username"),
                                        Math.toIntExact(dc.getDocument().getLong("points")));
                                mPoints.add(p);
                            }

                            pointsAdapter.notifyDataSetChanged();
                        }
                        pointsAdapter.sorting(mPoints);
                    }
                });
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}