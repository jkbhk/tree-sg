package com.example.treesg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

public class RewardDialog extends AppCompatDialogFragment {
    TextView dialogText;
    Reward reward;
    TextView pointHolder2;
    int currentPoints = UserManager.instance.getCurrentUser().getPoints();

    public RewardDialog(Reward reward)
    {
        this.reward = reward;
    }


    public Dialog onCreateDialog (Bundle savedInstancedState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reward_dialog, null);



        builder.setView(view).setTitle("Confirm Purchase").setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        }).setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(getContext(), "Successful Purchase!", Toast.LENGTH_SHORT).show();
                reward.applyContract();
                UserManager.instance.updateUserAsync(UserManager.instance.getCurrentUser().getUserID(), ()->{
                    String toUpdate = "" + UserManager.instance.getCurrentUser().getPoints();
                    RewardShopFragment.pointHolder2.setText(toUpdate)
                    ;});
            }
        });

        dialogText = view.findViewById(R.id.dialog_prompt);
        dialogText.setText(reward.itemName + " at " + reward.pointsRequired + " points ?");
        return builder.create();
    }




}
