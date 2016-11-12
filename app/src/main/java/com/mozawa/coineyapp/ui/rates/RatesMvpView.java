package com.mozawa.coineyapp.ui.rates;

import com.mozawa.coineyapp.ui.base.MvpView;

import java.util.Map;

public interface RatesMvpView extends MvpView {

    void showProgressBar(boolean b);
    void showResult(Map<String, Double> map);
    void showError();
    void showResultEmpty();

}
