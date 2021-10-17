package com.example.treesg;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.jetbrains.annotations.NotNull;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ExploreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ExploreFragment extends Fragment {

    private static final int NUMBER_OF_PREVIEW_BUNDLES = 4;


    public static ExploreFragment newInstance() {
        return new ExploreFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_explore, container, false);
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        RecyclerView rview = (RecyclerView) (view.findViewById(R.id.rv_explore_fragment));
        rview.setHasFixedSize(true);
        GridLayoutManager glm = new GridLayoutManager(getContext(),2);
        rview.setLayoutManager(glm);


        // fetch all posts and prep all the posts for rendering
        PostDataManager.instance.retreiveAllPosts(()->{
            ExploreController.instance.mapHashTagsToPosts();
            ExploreController.instance.loadPreliked();

            String[] trendingTags = ExploreController.instance.getTopTrendingHashTags(NUMBER_OF_PREVIEW_BUNDLES);
            ExploreAdapter exploreAdapter = new ExploreAdapter(trendingTags);
            rview.setAdapter(exploreAdapter);

            Treedebugger.log("loaded prelikes");
        });


    }
}