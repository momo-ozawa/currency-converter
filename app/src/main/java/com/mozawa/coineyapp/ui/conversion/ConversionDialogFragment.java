package com.mozawa.coineyapp.ui.conversion;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;

import com.mozawa.coineyapp.R;


public class ConversionDialogFragment extends DialogFragment {

    public ConversionDialogFragment() {
        // Empty constructor is required for DialogFragment.
        // Make sure not to add arguments to the constructor.
        // Use `newInstance` instead as shown below.
    }

    public static ConversionDialogFragment newInstance(String title) {
        ConversionDialogFragment fragment = new ConversionDialogFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.fragment_conversion_dialog, null))
                // Add action buttons
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ConversionDialogFragment.this.getDialog().cancel();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ConversionDialogFragment.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
}
