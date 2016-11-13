package com.mozawa.coineyapp.ui.conversion;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.mozawa.coineyapp.R;


public class ConversionDialogFragment extends DialogFragment {

    public static final String KEY_CURRENCY_ARRAY = "conversion_dialog_fragment.KEY_CURRENCY_ARRAY";

    private String[] currencyArray;

    public ConversionDialogFragment() {
        // Empty constructor is required for DialogFragment.
        // Make sure not to add arguments to the constructor.
        // Use `newInstance` instead as shown below.
    }

    public static ConversionDialogFragment newInstance(String[] currencyArray) {
        ConversionDialogFragment fragment = new ConversionDialogFragment();
        Bundle args = new Bundle();
        args.putStringArray(KEY_CURRENCY_ARRAY, currencyArray);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Get arguments.
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(KEY_CURRENCY_ARRAY)) {
                currencyArray = args.getStringArray(KEY_CURRENCY_ARRAY);
            }
        }

        // Get the layout inflater and inflate the layout.
        LayoutInflater inflater = getActivity().getLayoutInflater();
        RelativeLayout root = (RelativeLayout) inflater.inflate(R.layout.fragment_conversion_dialog, null);

        // Initialize spinners.
        Spinner fromCurrencySpinner = (Spinner) root.findViewById(R.id.fromCurrencySpinner);
        Spinner toCurrencySpinner = (Spinner) root.findViewById(R.id.toCurrencySpinner);

        // Set adapter to spinner.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, currencyArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromCurrencySpinner.setAdapter(adapter);
        toCurrencySpinner.setAdapter(adapter);

        // Configure the dialog.
        builder.setView(root)
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
