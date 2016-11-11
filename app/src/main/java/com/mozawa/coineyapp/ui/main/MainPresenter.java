package com.mozawa.coineyapp.ui.main;

import com.mozawa.coineyapp.ui.base.BasePresenter;

import rx.Subscription;

public class MainPresenter extends BasePresenter<MainMvpView> {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private Subscription subscription;

    @Override
    public void attachView(MainMvpView mvpView) {
        super.attachView(mvpView);
    }

    @Override
    public void detachView() {
        if (subscription != null) {
            subscription.unsubscribe();
        }
        super.detachView();
    }

    public void loadData() {
        checkViewAttached();

        if (subscription != null) {
            subscription.unsubscribe();
        }

        getMvpView().showProgressBar(true);

        /*
        subscription = dataManager.getFlickrEntries(formattedTags, tagMode)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Subscriber<List<FlickrEntry>>() {
                    @Override
                    public void onCompleted() {
                        getMvpView().showProgressBar(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, e.getLocalizedMessage());
                        getMvpView().showProgressBar(false);
                        getMvpView().showError();
                    }

                    @Override
                    public void onNext(List<FlickrEntry> flickrEntries) {
                        if (flickrEntries.size() > 0) {
                            getMvpView().showFlickrEntries(flickrEntries);
                        } else {
                            getMvpView().showResultsEmpty(tags);
                        }
                    }
                });
         */
    }
}
