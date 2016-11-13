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
import android.view.WindowManager;
import android.widget.AdapterView;
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


public class ConversionDialogFragment extends DialogFragment implements TextWatcher,
        AdapterView.OnItemSelectedListener {

    public static final String KEY_EXCHANGE_RATE_MAP = "conversion_dialog_fragment.KEY_EXCHANGE_RATE_MAP";
    public static final String KEY_SELECTED_CURRENCY = "conversion_dialog_fragment.KEY_SELECTED_CURRENCY";

    @BindView(R.id.baseCurrencySpinner)
    Spinner baseCurrencySpinner;
    @BindView(R.id.targetCurrencySpinner)
    Spinner targetCurrencySpinner;
    @BindView(R.id.editText)
    EditText editText;
    @BindView(R.id.targetResultTextView)
    TextView targetResultTextView;

    private HashMap<String, HashMap<String, Double>> exchangeRates;
    private String selectedCurrency;


    public ConversionDialogFragment() {
        // Empty constructor is required for DialogFragment.
        // Make sure not to add arguments to the constructor.
        // Use `newInstance` instead as shown below.
    }

    public static ConversionDialogFragment newInstance(HashMap<String, HashMap<String, Double>> exchangeRates,
                                                       String selectedCurrency) {
        ConversionDialogFragment fragment = new ConversionDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(KEY_EXCHANGE_RATE_MAP, exchangeRates);
        args.putString(KEY_SELECTED_CURRENCY, selectedCurrency);
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

        // Set up variables and views.
        getArgs();
        initViews();

        // Configure the dialog.
        builder.setView(root)
                // Add action buttons
                .setPositiveButton(R.string.dismiss, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        ConversionDialogFragment.this.getDialog().cancel();
                    }
                });

        Dialog dialog = builder.create();
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        return dialog;
    }

    private void getArgs() {
        Bundle args = getArguments();
        if (args != null) {
            if (args.containsKey(KEY_EXCHANGE_RATE_MAP)) {
                exchangeRates = (HashMap<String, HashMap<String, Double>>) args.getSerializable(KEY_EXCHANGE_RATE_MAP);
            }

            if (args.containsKey(KEY_SELECTED_CURRENCY)) {
                selectedCurrency = args.getString(KEY_SELECTED_CURRENCY);
            }
        }
    }

    private void initViews() {
        // Convert map keySet to a List<String>.
        List<String> currencyList = new ArrayList<>(exchangeRates.keySet());

        initSpinners(currencyList);
        initTextFields();
    }

    private void initTextFields() {
        editText.append("1");  // Using 'append' will move the cursor to the end of text.
        editText.addTextChangedListener(this);
        targetResultTextView.setText("1");
    }

    private void initSpinners(List<String> currencyList) {
        // Set adapters.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(),
                android.R.layout.simple_spinner_item, currencyList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        baseCurrencySpinner.setAdapter(adapter);
        targetCurrencySpinner.setAdapter(adapter);

        // Set item selected listeners.
        baseCurrencySpinner.setOnItemSelectedListener(this);
        targetCurrencySpinner.setOnItemSelectedListener(this);

        // Set selection.
        int index = currencyList.indexOf(selectedCurrency);
        if (index != -1) {
            baseCurrencySpinner.setSelection(index);
            targetCurrencySpinner.setSelection(index);
        }
    }

    private void updateTextFields(String editTextString) {
        // Get selected items for both spinners.
        String baseCurrency = baseCurrencySpinner.getSelectedItem().toString();
        String targetCurrency = targetCurrencySpinner.getSelectedItem().toString();

        if (editTextString.length() != 0) {
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

    /*******
     * TextWatcher implementation
     *******/

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() == 0) {
            // Really strange that setText("") doesn't work... but setText(" ") does...
            targetResultTextView.setText(" ");
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        updateTextFields(s.toString());
    }

    /*******
     * AdapterView.OnItemSelectedListener implementation
     *******/

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String editTextString = editText.getText().toString().trim();
        updateTextFields(editTextString);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
