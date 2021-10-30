package com.example.treesg;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

public class RewardErrorDialog extends AppCompatDialogFragment {

    TextView dialogText;
    Reward reward;

    public RewardErrorDialog(Reward reward)
    {
        this.reward = reward;
    }


    public Dialog onCreateDialog (Bundle savedInstancedState){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.reward_dialog, null);

        builder.setView(view).setTitle("Not Enough Points").setPositiveButton("confirm", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });

        dialogText = view.findViewById(R.id.dialog_prompt);
        dialogText.setText("Not enough points for " + reward.itemName);
        return builder.create();
    }

}
