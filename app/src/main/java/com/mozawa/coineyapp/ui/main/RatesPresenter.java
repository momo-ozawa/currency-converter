package com.mozawa.coineyapp.ui.main;

import com.mozawa.coineyapp.data.DataManager;
import com.mozawa.coineyapp.ui.base.BasePresenter;
import com.mozawa.coineyapp.util.RxUtil;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RatesPresenter extends BasePresenter<RatesMvpView> {

    private static final String TAG = RatesPresenter.class.getSimpleName();

    private DataManager dataManager;
    private Subscription subscription;

    @Inject
    public RatesPresenter(DataManager dataManager) {
        this.dataManager = dataManager;
    }

    @Override
    public void attachView(RatesMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.detachView();
    }

    public void loadExchangeRates() {
        checkViewAttached();
        RxUtil.unsubscribe(subscription);

        subscription = dataManager.getMap()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<HashMap<String, HashMap<String, Double>>>() {
                    @Override
                    public void onCompleted() {
                    }

                    @Override
                    public void onError(Throwable e) {
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(HashMap<String, HashMap<String, Double>> exchangeRates) {
                        if (exchangeRates != null || exchangeRates.size() != 0) {
                            getMvpView().showExchangeRates(exchangeRates);
                        }  else {
                            getMvpView().showExchangeRatesEmpty();
                        }
                    }
                });
    }

    public void onCalculateConversionClicked() {
        checkViewAttached();
        getMvpView().showConversionDialog();
    }
}
