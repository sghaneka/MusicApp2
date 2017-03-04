package com.sg.musicapp2;

import android.app.Application;

import com.napster.cedar.Napster;
import com.napster.cedar.player.Player;
import com.napster.cedar.session.SessionManager;

/**
 * Created by samgh on 2/4/2017.
 */

public class MusicApplication extends Application {

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

    public MusicAppInfo getAppInfo(){
        return mTopTenSampleMusicAppInfo;
    }

    public Napster getNapster() {
        return napster;
    }

    public Player getPlayer() {
        return player;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    private MusicAppInfo mTopTenSampleMusicAppInfo = new MusicAppInfo() {
        @Override
        public String getApiKey() {
            return "YzA0NzQ2MzYtMTg3OC00YmRiLTg1ZjItMmVkYjgwOTRmMDUx";
        }

        @Override
        public String getSecret() {
            return "MzQ4ZDg0NjMtMTliZS00Y2UwLThlZjMtMzU5OTg1ODJkYTg0";
        }

        @Override
        public String getRedirectUrl() {
            return "sample://authorize";
        }
    };
}
