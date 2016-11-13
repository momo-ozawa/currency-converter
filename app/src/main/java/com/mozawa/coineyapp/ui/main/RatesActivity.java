package com.mozawa.coineyapp.ui.main;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.mozawa.coineyapp.R;
import com.mozawa.coineyapp.ui.base.BaseActivity;
import com.mozawa.coineyapp.ui.conversion.ConversionDialogFragment;
import com.mozawa.coineyapp.ui.widgets.DividerItemDecoration;

import java.util.HashMap;
import java.util.Set;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatesActivity extends BaseActivity implements RatesMvpView {

    @Inject
    RatesPresenter presenter;

    @Inject
    RatesAdapter ratesAdapter;

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.ratesRecyclerView)
    RecyclerView ratesRecyclerView;
    @BindView(R.id.lastUpdatedTextView)
    TextView lastUpdatedTextView;
    @BindView(R.id.baseCurrencySpinner)
    Spinner baseCurrencySpinner;

    private HashMap<String, HashMap<String, Double>> exchangeRates;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivityComponent().inject(this);
        setContentView(R.layout.activity_rates);
        ButterKnife.bind(this);

        // Set up toolbar.
        setSupportActionBar(toolbar);

        // Always need to attach the view before calling any presenter methods.
        presenter.attachView(this);

        // Load exchange rates.
        presenter.loadExchangeRates();

        // Set up a item selected listener so we can update the recycler view data
        // when a user makes a selects a base currency in the spinner.
        baseCurrencySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                updateRecylerView(exchangeRates);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_rates, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_convert:
                presenter.onCalculateConversionClicked();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // Helper method to update recycler view data based on the selected base currency.
    private void updateRecylerView(HashMap<String, HashMap<String, Double>> exchangeRates) {
        String selectedCurrency = baseCurrencySpinner.getSelectedItem().toString();
        ratesAdapter.setData(exchangeRates.get(selectedCurrency));
        ratesAdapter.notifyDataSetChanged();
    }


    /*******
     * RatesMvpView implementation
     *******/

    @Override
    public void showProgressBar(boolean b) {

    }

    @Override
    public void showExchangeRates(HashMap<String, HashMap<String, Double>> exchangeRates) {
        this.exchangeRates = exchangeRates;

        // Convert map keySet to a string array.
        Set<String> keys = exchangeRates.keySet();
        String[] currencyArray = keys.toArray(new String[keys.size()]);

        // Set adapter to spinner.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, currencyArray);
        baseCurrencySpinner.setAdapter(adapter);

        // Set up recycler view.
        ratesRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        ratesRecyclerView.setAdapter(ratesAdapter);
        ratesRecyclerView.addItemDecoration(new DividerItemDecoration(this));

        updateRecylerView(exchangeRates);
    }

    @Override
    public void showError() {
        Toast.makeText(this, "Error!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showExchangeRatesEmpty() {

    }

    @Override
    public void showConversionDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ConversionDialogFragment dialogFragment = ConversionDialogFragment.newInstance(exchangeRates);
        dialogFragment.show(fm, "fragment_conversion_dialog");
    }

}
