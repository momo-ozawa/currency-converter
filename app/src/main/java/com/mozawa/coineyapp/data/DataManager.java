package com.mozawa.coineyapp.data;

import com.mozawa.coineyapp.data.remote.CoineyService;

import javax.inject.Inject;

public class DataManager {

    private final CoineyService coineyService;

    @Inject
    public DataManager(CoineyService coineyService) {
        this.coineyService = coineyService;
    }

//    public Observable<List<FlickrEntry>> getFlickrEntries(String tags, String tagMode) {
//        return coineyService.getFlickrFeed(tags, tagMode).flatMap(new Func1<FlickrFeed, Observable<List<FlickrEntry>>>() {
//            @Override
//            public Observable<List<FlickrEntry>> call(FlickrFeed flickrFeed) {
//                return Observable.just(flickrFeed.items);
//            }
//        });
//    }

}