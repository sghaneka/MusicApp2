package com.sg.musicapp2.login;

import com.napster.cedar.session.AuthToken;
import com.sg.musicapp2.Constants;
import com.sg.musicapp2.MusicAppInfo;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by samgh on 2/4/2017.
 */

public class Authentication {
    AuthenticationService authenticationService;
    MusicAppInfo appInfo;

    public Authentication(MusicAppInfo appInfo) {
        RestAdapter adapter = new RestAdapter.Builder()
                .setEndpoint(Constants.ENDPOINT_HTTPS)
                //.setLog(new AndroidLog("resty"))
                .build();
        adapter.setLogLevel(RestAdapter.LogLevel.FULL);

        authenticationService = adapter.create(AuthenticationService.class);
        this.appInfo = appInfo;
    }

    public void swapCodeForToken(String code, Callback<AuthToken> callback) {
        authenticationService.authenticate(
                appInfo.getApiKey(), appInfo.getSecret(),
                AuthenticationService.RESPONSE_TYPE_CODE, AuthenticationService.GRANT_TYPE_CODE,
                appInfo.getRedirectUrl(), code, callback);
    }
}

