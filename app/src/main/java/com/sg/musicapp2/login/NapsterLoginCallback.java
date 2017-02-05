package com.sg.musicapp2.login;

import com.napster.cedar.session.AuthToken;

/**
 * Created by samgh on 2/4/2017.
 */

public interface NapsterLoginCallback {
    public void onLoginSuccess(AuthToken authToken);

    public void onLoginError(String url, Throwable e);
}
