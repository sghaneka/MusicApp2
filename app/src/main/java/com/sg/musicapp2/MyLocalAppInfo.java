package com.sg.musicapp2;

import java.io.Serializable;

/**
 * Created by samgh on 2/4/2017.
 */

public abstract class MyLocalAppInfo implements Serializable {

    public abstract String getApiKey();
    public abstract String getSecret();
    public abstract String getRedirectUrl();

}