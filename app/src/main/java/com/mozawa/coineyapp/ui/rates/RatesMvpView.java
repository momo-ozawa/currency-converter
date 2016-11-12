package com.mozawa.coineyapp.ui.rates;

import com.mozawa.coineyapp.data.model.Exchange;
import com.mozawa.coineyapp.ui.base.MvpView;

import java.util.Map;

public interface RatesMvpView extends MvpView {

    void showProgressBar(boolean b);
    void showResult(Map<String, Exchange> map);
    void showError();
    void showResultEmpty();

}
