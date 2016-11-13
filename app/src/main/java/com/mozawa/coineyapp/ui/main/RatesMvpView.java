package com.mozawa.coineyapp.ui.main;

import com.mozawa.coineyapp.ui.base.MvpView;

import java.util.HashMap;

public interface RatesMvpView extends MvpView {

    void showExchangeRates(HashMap<String, HashMap<String, Double>> exchangeRates);
    void showError();
    void showExchangeRatesEmpty();
    void showConversionDialog();

}
