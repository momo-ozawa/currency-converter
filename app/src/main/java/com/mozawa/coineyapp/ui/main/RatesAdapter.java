package com.mozawa.coineyapp.ui.main;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mozawa.coineyapp.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> {

    private List<String> currencyList;
    private List<Double> exchangeRateList;

    @Inject
    public RatesAdapter() {
        this.currencyList = new ArrayList<>();
        this.exchangeRateList = new ArrayList<>();
    }

    public void setData(HashMap<String, Double> rates) {
        this.currencyList = new ArrayList<>(rates.keySet());
        this.exchangeRateList = new ArrayList<>(rates.values());

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_exchange_rate, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String currency = currencyList.get(position);
        Double exchangeRate = exchangeRateList.get(position);

        holder.currencyTextView.setText(currency);
        holder.exchangeRateTextView.setText(exchangeRate + "");
    }

    @Override
    public int getItemCount() {
        return currencyList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.currencyTextView)
        TextView currencyTextView;
        @BindView(R.id.exchangeRateTextView)
        TextView exchangeRateTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
