package com.example.treesg;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

public class TrendingFragment extends Fragment {

    private TrendingViewModel mViewModel;


    public static TrendingFragment newInstance() {
        return new TrendingFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.trending_fragment, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TrendingViewModel.class);
        // TODO: Use the ViewModel

    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        RecyclerView rview = (RecyclerView) (view.findViewById(R.id.recyclerView1));
        rview.setHasFixedSize(true);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));

        String m = "Look at nature! Isn't it pretty?";
        Post[] test = {
                new Post(m,R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,10,5),
                new Post("Dam, Singapore is pretty cool!",R.drawable.nature_placeholder_2,"simuliu","Marina Bay",R.drawable.simu_liu,1232338,52102),
                new Post("wowzers i love nature",R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,10,5),
                new Post("wowzers i love nature",R.drawable.nature_placeholder,"alice_rox","Whanganui River",R.drawable.profile_placeholder,10,5)
        };

        PostAdapter pa = new PostAdapter(test);
        rview.setAdapter(pa);

    }
}