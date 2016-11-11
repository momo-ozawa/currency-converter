package com.mozawa.coineyapp.data;

import com.mozawa.coineyapp.data.remote.Service;

public class DataManager {

    private static DataManager instance = null;
    private Service service;

    // Private constructor to prevent any other class from instantiating.
    private DataManager() {
        this.service = Service.Creator.newService();
    }

    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }

//    public Observable<List<FlickrEntry>> getFlickrEntries(String tags, String tagMode) {
//        return service.getFlickrFeed(tags, tagMode).flatMap(new Func1<FlickrFeed, Observable<List<FlickrEntry>>>() {
//            @Override
//            public Observable<List<FlickrEntry>> call(FlickrFeed flickrFeed) {
//                return Observable.just(flickrFeed.items);
//            }
//        });
//    }

}