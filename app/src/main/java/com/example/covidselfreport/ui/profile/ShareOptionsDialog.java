package com.example.covidselfreport.ui.profile;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;

import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.example.covidselfreport.R;

import java.io.File;
import java.util.List;


/**
 * The dialog fragment that prompts the user to choose between sharing just their Preferences Survey or their preferences survey and daily intake responses
 * Generates the Android app chooser (the share screen) to share the PDF file (or plain text) to other apps
 */
public class ShareOptionsDialog extends DialogFragment {

    /*@Override
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
    }*/

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.share_options);
        builder.setItems(R.array.share_options, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                boolean includeIntakes;
                includeIntakes = which != 0;

                //profileStr will be null if a PDF is successfully generated. It will contain the report info if a PDF is not successfully generated.
                String profileStr = ProfileFragment.generatePDF(includeIntakes);
                //If the PDF was generated, share the PDF using sharePDF():
                if (profileStr == null)
                    sharePDF();
                //If the PDF was not generated, share the PDF using a text-based approach (shareText() ):
                else
                    shareText(profileStr);
            }
        });

        return builder.create();
    }


    /**
     * Generates an app chooser that allows the user to share the PDF report
     * Grants other Android apps permission to access the PDF file stored by this app
     * A PDF will be generated as long as android is up-to-date enough (version is greater than or equal to KitKat)
     */
    private void sharePDF() {
        //The PDF file and the URI for that PDF file:
        File pdf = new File(requireActivity().getFilesDir().toString(), "profilereport.pdf");
        Uri uri = FileProvider.getUriForFile(requireContext(), "com.example.covidselfreport", pdf);

        //Initialize the sharing intent (used to insert the PDF into the android share screen):
        Intent shareIntent = new Intent();
        shareIntent.setAction(Intent.ACTION_SEND);
        shareIntent.setType("application/pdf");
        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);

        //The app grants other Android apps permission access the PDF file:
        PackageManager packageManager = requireActivity().getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(shareIntent, PackageManager.MATCH_DEFAULT_ONLY);
        if (list.size() < 1) {
            return;
        }
        String packageName = list.get(0).activityInfo.packageName;
        requireActivity().grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);

        //Display the Android share screen/chooser:
        startActivity(Intent.createChooser(shareIntent, "Share using..."));
    }


    /**
     * Generates an app chooser that allows the user to share a text-based profile report
     */
    private void shareText(String shareStr) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, shareStr);
        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share using..."));
    }
}
