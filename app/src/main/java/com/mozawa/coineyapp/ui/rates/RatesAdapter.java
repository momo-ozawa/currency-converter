package com.mozawa.coineyapp.ui.rates;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mozawa.coineyapp.R;
import com.mozawa.coineyapp.data.model.Exchange;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RatesAdapter extends RecyclerView.Adapter<RatesAdapter.ViewHolder> {

    Context context;
    List<String> currencyList;

    public RatesAdapter(Context context) {
        this.context = context;
    }

    public void setData(Map<String, Exchange> exchangeRateMap) {
        currencyList = new ArrayList<>(exchangeRateMap.keySet());
    }

    public void setContext(Context context) {
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_exchange_rate, null);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String currency = currencyList.get(position);

        holder.currencyTextView.setText(currency);
        holder.exchangeRateTextView.setText(currency);
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
