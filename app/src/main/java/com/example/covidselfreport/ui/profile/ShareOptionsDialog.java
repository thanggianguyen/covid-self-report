package com.example.covidselfreport.ui.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import com.example.covidselfreport.R;


public class ShareOptionsDialog extends DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.share_options);
        builder.setItems(R.array.share_options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String shareStr;
                if (which == 0)
                    shareStr = ProfileFragment.getProfileText(false);
                else
                    shareStr = ProfileFragment.getProfileText(true);

                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, shareStr);
                sendIntent.setType("text/plain");
                startActivity(Intent.createChooser(sendIntent, "Share using..."));
            }
        });

        return builder.create();
    }
}
