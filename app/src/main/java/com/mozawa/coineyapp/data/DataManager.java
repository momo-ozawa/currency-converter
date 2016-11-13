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
        return coineyService.getExchangeRates();
    }
}