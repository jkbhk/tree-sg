package com.example.treesg;

import androidx.activity.OnBackPressedCallback;
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

import com.google.firebase.database.core.utilities.Tree;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class TrendingFragment extends Fragment {

    private TrendingViewModel mViewModel;

    public static String filterBy = "";

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

        // set the like status of all posts before rendering them
        loadPreliked();

        RecyclerView rview = (RecyclerView) (view.findViewById(R.id.recyclerView1));
        rview.setHasFixedSize(true);
        rview.setLayoutManager(new LinearLayoutManager(getContext()));

        PostAdapter pa;
        ArrayList<Post> postsToRender = ExploreController.instance.getBundle(filterBy);
        if(postsToRender == null)//no filter, show all posts
            pa = new PostAdapter(PostDataManager.instance.posts.toArray(new Post[PostDataManager.instance.posts.size()]));
        else{
            pa = new PostAdapter(postsToRender.toArray(new Post[postsToRender.size()]));
        }

        rview.setAdapter(pa);

    }

    private void loadPreliked(){
        if(UserManager.instance.getCurrentUser() == null){
            Treedebugger.log("current user not loaded yet, could not load preliked posts");
            return;
        }

        for(Post p : PostDataManager.instance.posts){
            p.setLiked(UserManager.instance.getCurrentUser().getLikedPosts().contains(p.getPostID()));
        }
    }

}