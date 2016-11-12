package com.mozawa.coineyapp.ui.rates;

import android.util.Log;

import com.mozawa.coineyapp.data.DataManager;
import com.mozawa.coineyapp.data.model.Exchange;
import com.mozawa.coineyapp.ui.base.BasePresenter;
import com.mozawa.coineyapp.util.RxUtil;

import java.util.Map;

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

    public void loadMap() {
        checkViewAttached();
        RxUtil.unsubscribe(subscription);

        getMvpView().showProgressBar(true);

        subscription = dataManager.getMap()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<Map<String, Exchange>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressBar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO: 11/12/16 Don't forget to remove Log statement during unit tests
                        Log.d("onError", e.getLocalizedMessage());
                        getMvpView().showProgressBar(false);
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(Map<String, Exchange> map) {
                        if (map != null) {
                            // FIXME: 11/12/16 Just making sure I can fetch/display data
                            getMvpView().showResult(map);
                        }
                    }
                });
    }

}
