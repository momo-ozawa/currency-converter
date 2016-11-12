package com.mozawa.coineyapp.ui.main;

import com.mozawa.coineyapp.ui.base.MvpView;

import java.util.Map;

public interface RatesMvpView extends MvpView {

    void showProgressBar(boolean b);
    void showResult(Map<String, Double> map);
    void showError();
    void showResultEmpty();

    void showConversionDialog();

}
