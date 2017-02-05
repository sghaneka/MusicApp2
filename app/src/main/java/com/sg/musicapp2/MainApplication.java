package com.sg.musicapp2;

import android.app.Application;

import com.napster.cedar.Napster;
import com.napster.cedar.player.Player;
import com.napster.cedar.session.SessionManager;

/**
 * Created by samgh on 2/4/2017.
 */

public class MainApplication extends Application {

    protected Napster napster;
    protected Player player;
    protected SessionManager sessionManager;

    @Override
    public void onCreate() {
        super.onCreate();
        try {
            napster = Napster.register(this, getAppInfo().getApiKey(), getAppInfo().getSecret());
        } catch (IllegalStateException e) {
            return;
        }
        player = napster.getPlayer();
//        player.setNotificationProperties(new NotificationProperties());
//        player.registerNotificationActionListener(this);
        sessionManager = napster.getSessionManager();
    }

    public MyLocalAppInfo getAppInfo(){
        return mTopTenSampleMyLocalAppInfo;
    }

    public Napster getNapster() {
        return napster;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private MyLocalAppInfo mTopTenSampleMyLocalAppInfo = new MyLocalAppInfo() {
        @Override
        public String getApiKey() {
            return "NjQzYThjNTEtNmUwOS00NjA1LTg1Y2MtMzkwNTcyZWIyZjk4";
        }

        @Override
        public String getSecret() {
            return "YjE3MGNmZWItZDU3NS00ZjJiLWIwYmMtZjliOTBlNmU4ODBm";
        }

        @Override
        public String getRedirectUrl() {
            return "sample://authorize";
        }
    };
}
