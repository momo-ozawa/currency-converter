package com.mozawa.coineyapp.ui.main;

import com.mozawa.coineyapp.ui.base.MvpView;

public interface MainMvpView extends MvpView {

    void showProgressBar(boolean b);
    void showResult();
    void showError();
    void showResultEmpty();

}
