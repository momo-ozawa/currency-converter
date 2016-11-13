package com.mozawa.coineyapp.ui.conversion;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.mozawa.coineyapp.R;

import java.util.HashMap;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ConversionDialogFragment extends DialogFragment implements TextWatcher {

    public static final String KEY_EXCHANGE_RATE_MAP = "conversion_dialog_fragment.KEY_EXCHANGE_RATE_MAP";

    @BindView(R.id.baseCurrencySpinner)
    Spinner baseCurrencySpinner;
    @BindView(R.id.targetCurrencySpinner)
    Spinner targetCurrencySpinner;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.targetResultTextView)
    TextView targetResultTextView;

    private HashMap<String, HashMap<String, Double>> exchangeRates;

    public ConversionDialogFragment() {
        // Empty constructor is required for DialogFragment.
        // Make sure not to add arguments to the constructor.
        // Use `newInstance` instead as shown below.
    }

    public static ConversionDialogFragment newInstance(HashMap<String, HashMap<String, Double>> exchangeRates) {
        ConversionDialogFragment fragment = new ConversionDialogFragment();
        Bundle args = new Bundle();
//        args.putStringArray(KEY_CURRENCY_ARRAY, currencyArray);
        args.putSerializable(KEY_EXCHANGE_RATE_MAP, exchangeRates);
        fragment.setArguments(args);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        // Inflate the layout and bind views using ButterKnife.
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View root = View.inflate(getActivity(), R.layout.fragment_conversion_dialog, null);
        ButterKnife.bind(this, root);

        getArgs();
        setUpSpinners();
        editText.addTextChangedListener(this);

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

    private void getArgs() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(KEY_EXCHANGE_RATE_MAP)) {
                exchangeRates = (HashMap<String, HashMap<String, Double>>) args.getSerializable(KEY_EXCHANGE_RATE_MAP);
            }
        }
    }

    private void setUpSpinners() {
        // Convert map keySet to a string array.
        Set<String> keys = exchangeRates.keySet();
        String[] currencyArray = keys.toArray(new String[keys.size()]);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, currencyArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        baseCurrencySpinner.setAdapter(adapter);
        targetCurrencySpinner.setAdapter(adapter);
    }

    /*******
     * TextWatcher implementation
     *******/

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        String baseCurrency = baseCurrencySpinner.getSelectedItem().toString();
        String targetCurrency = targetCurrencySpinner.getSelectedItem().toString();
        String editTextString = s.toString();

        if (baseCurrency.equals(targetCurrency)) {
            targetResultTextView.setText(editTextString);
        } else {
            Double editTextValue = Double.parseDouble(editTextString);
            Double rate = exchangeRates.get(baseCurrency).get(targetCurrency);
            Double result = editTextValue * rate;
            targetResultTextView.setText(result + "");
        }
    }
}
