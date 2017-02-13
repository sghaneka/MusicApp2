package com.sg.musicapp2.data;

import android.util.Log;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.client.Response;

/**
 * Created by samgh on 2/8/2017.
 */

public class Metadata {
    PlayListService playListService;
    String apiKey;

    public Metadata(String apiKey) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(com.sg.musicapp2.Constants.ENDPOINT_HTTP)
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .setLog(new RestAdapter.Log() {
                    @Override
                    public void log(String msg) {
                        Log.d("resty", msg);
                    }
                })
                .build();
        playListService = adapter.create(PlayListService.class);

        this.apiKey = apiKey;
    }


    public PlayListService getPlayListService() {
        return playListService;
    }
}
