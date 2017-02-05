package com.sg.musicapp2.login;

import android.provider.SyncStateContract;

import com.napster.cedar.session.AuthToken;
import com.sg.musicapp2.Constants;
import com.sg.musicapp2.MyLocalAppInfo;

import retrofit.Callback;
import retrofit.RestAdapter;

/**
 * Created by samgh on 2/4/2017.
 */

public class Authentication {
    AuthenticationService authenticationService;
    MyLocalAppInfo appInfo;

    public Authentication(MyLocalAppInfo appInfo) {
        RestAdapter adapter = new RestAdapter.Builder().setEndpoint(Constants.ENDPOINT_HTTPS).build();
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

