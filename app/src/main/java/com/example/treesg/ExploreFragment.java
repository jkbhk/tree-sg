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
 *
 *
 * Everytime we visit this page we will do a fresh retrieval of all posts from PostDataManager.
 * A manual refresh for retrieving all posts can also be triggered from here.
 *
 * In order for this page to show the trending hashtags, it will call the ExploreController to start mapping
 * hashtags as soon as the posts are retrieved via callback.
 *
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


        // do a clean fetch for all posts
        PostDataManager.instance.retreiveAllPosts(()->{

            ExploreController.instance.mapHashTagsToPosts();

            String[] trendingTags = ExploreController.instance.getTopTrendingHashTags(NUMBER_OF_PREVIEW_BUNDLES);
            ExploreAdapter exploreAdapter = new ExploreAdapter(trendingTags);
            rview.setAdapter(exploreAdapter);

        });


    }
}