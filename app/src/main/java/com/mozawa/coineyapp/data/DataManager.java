package com.mozawa.coineyapp.data;

import com.mozawa.coineyapp.data.remote.CoineyService;

import java.util.HashMap;

import javax.inject.Inject;

import rx.Observable;

public class DataManager {

    private final CoineyService coineyService;

    @Inject
    public DataManager(CoineyService coineyService) {
        this.coineyService = coineyService;
    }

    public Observable<HashMap<String, HashMap<String, Double>>> getMap() {
        // I couldn't really figure out how to map dynamic keys. The nested HashMap is so unwieldy.
        // Maybe I'll take up J.Wharton's suggestion and implement a Gson type adapter
        // some time later...
        // https://www.reddit.com/r/androiddev/comments/2znwcs/question_parsing_json_with_dynamic_key/cpkpnhl/
        return coineyService.getExchangeRates();
    }
}