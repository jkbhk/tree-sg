package com.example.treesg;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class RewardFragment extends Fragment {

    final String [] sectors = {"1", "2", "3", "4", "5", "6", "7"};
    final int [] sectorDegrees = new int[sectors.length];
    final Random random = new Random();
    int degree = 0;
    boolean isSpinning = false;
    //Integer tempPoint = UserManager.instance.getCurrentUser().getPoints();
    //Integer tempSpin = UserManager.instance.getCurrentUser().getSpins();
    static Button spinBtn;
    ImageView rewardShopBtn;
    TextView pointHolder1;
    TextView spinHolder;
    TextView shopTitle;

    ImageView wheel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v;
        v = inflater.inflate(R.layout.fragment_reward, container, false);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull @NotNull View view, @Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        spinBtn = view.findViewById(R.id.spinButton);
        wheel = view.findViewById(R.id.luckySpin);
        rewardShopBtn = view.findViewById(R.id.rewardShopBtn);

        pointHolder1 = view.findViewById(R.id.pointHolder1);
        pointHolder1.setText(""+UserManager.instance.getCurrentUser().getPoints());

        spinHolder = view.findViewById(R.id.spinNumber);
        spinHolder.setText(""+UserManager.instance.getCurrentUser().getSpins());

        shopTitle = view.findViewById(R.id.shopTitle);

        getDegreeForSectors();

        if (UserManager.instance.getCurrentUser().getSpins() == 0)
            spinBtn.setEnabled(false);

        spinBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isSpinning && UserManager.instance.getCurrentUser().getSpins() > 0)
                {
                    //spinBtn.setEnabled(true);
                    UserManager.instance.incrementSpinsAsync(-1, null);
                    spin();
                    pointHolder1.setText(""+UserManager.instance.getCurrentUser().getPoints());
                    isSpinning = true;
                }
                else
                    spinBtn.setEnabled(false);
            }
        });

        rewardShopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.navigationController.navigate(R.id.navigation_rewardShop);
            }
        });

    }

    private void getDegreeForSectors(){
        int sectorDegree = 360/sectors.length;

        for(int i = 0; i < sectors.length; i++)
        {
            sectorDegrees[i] = (i+1) * sectorDegree;
        }
    }

    private void spin()
    {
        degree = random.nextInt(sectors.length-1);

        spinHolder.setText(""+UserManager.instance.getCurrentUser().getSpins());

        RotateAnimation rotateAnimation = new RotateAnimation(0, (360 * sectors.length) + sectorDegrees[degree],
                RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);

        rotateAnimation.setDuration(3600);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new DecelerateInterpolator());
        rotateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                spinBtn.setEnabled(false);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Toast.makeText(getActivity().getApplicationContext(), "You've got " + sectors[sectors.length - (degree+1)] + "points", Toast.LENGTH_SHORT).show();
                Treedebugger.log(""+sectors[sectors.length - (degree+1)]);

                isSpinning = false;
                String result = sectors[sectors.length - (degree+1)];
                showResult(result);

                if (UserManager.instance.getCurrentUser().getSpins() <= 0)
                    spinBtn.setEnabled(false);
                else
                    spinBtn.setEnabled(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        wheel.startAnimation(rotateAnimation);
    }

    private void showResult(String result)
    {
        if (result == "1")
        {
            Toast.makeText(getActivity().getApplicationContext(), "Congratulations! You've gained 1 spins", Toast.LENGTH_SHORT).show();
            UserManager.instance.incrementSpinsAsync(1, null);
            spinHolder.setText(""+UserManager.instance.getCurrentUser().getSpins());
        }
        else if (result == "2")
        {
            Toast.makeText(getActivity().getApplicationContext(), "Congratulations! You've gained the new MacBook Pro 16", Toast.LENGTH_SHORT).show();
        }
        else if (result == "3")
        {
            Toast.makeText(getActivity().getApplicationContext(), "Better Luck Next Time!", Toast.LENGTH_SHORT).show();
        }
        else if (result == "4")
        {
            Toast.makeText(getActivity().getApplicationContext(), "Congratulations! You've gained 2 spins", Toast.LENGTH_SHORT).show();
            UserManager.instance.incrementSpinsAsync(2, null);
            spinHolder.setText(""+UserManager.instance.getCurrentUser().getSpins());
        }
        else if (result == "5")
        {
            Toast.makeText(getActivity().getApplicationContext(), "Congratulations! You've gained a chance to plant 1 tree!", Toast.LENGTH_SHORT).show();
        }
        else if (result == "6")
        {
            Toast.makeText(getActivity().getApplicationContext(), "Better Luck Next Time!", Toast.LENGTH_SHORT).show();
        }
        else if (result == "7")
        {
            Toast.makeText(getActivity().getApplicationContext(), "Better Luck Next Time!", Toast.LENGTH_SHORT).show();
        }
    }

}