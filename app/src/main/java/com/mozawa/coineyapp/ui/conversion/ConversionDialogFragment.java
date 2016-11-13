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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
        editText.setText("1");
        targetResultTextView.setText("1");
        editText.addTextChangedListener(this);

        // Configure the dialog.
        builder.setView(root)
                // Add action buttons
                .setPositiveButton("Dismiss", new DialogInterface.OnClickListener() {
                    @Override
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
        // Convert map keySet to a List<String>.
        List<String> currencyList = new ArrayList<>(exchangeRates.keySet());

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, currencyList);
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
        if (s.length() == 0) {
            // Really strange but setText("") wouldn't work... but setText(" ") does...
            targetResultTextView.setText(" ");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        String baseCurrency = baseCurrencySpinner.getSelectedItem().toString();
        String targetCurrency = targetCurrencySpinner.getSelectedItem().toString();
        String editTextString = s.toString();

        if (s.length() != 0) {
            if (baseCurrency.equals(targetCurrency)) {
                targetResultTextView.setText(editTextString);
            } else {
                Double rate = exchangeRates.get(baseCurrency).get(targetCurrency);
                Double editTextValue = Double.parseDouble(editTextString);
                Double targetResult = rate * editTextValue;
                targetResultTextView.setText(targetResult + "");
            }
        }
    }
}
