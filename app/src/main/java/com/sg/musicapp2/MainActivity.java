package com.sg.musicapp2;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.napster.cedar.AuthorizedRequest;
import com.napster.cedar.Napster;
import com.napster.cedar.NapsterError;
import com.napster.cedar.session.AuthToken;
import com.napster.cedar.session.SessionCallback;
import com.napster.cedar.session.SessionManager;
import com.sg.musicapp2.data.DataService;
import com.sg.musicapp2.login.NapsterLoginCallback;
import com.sg.musicapp2.login.NapsterLoginDialogFragment;
import com.sg.musicapp2.models.PlayList;
import com.sg.musicapp2.models.PlayLists;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.sg.musicapp2.helpers.RetroFitUtil.getStringFromRetrofitResponse;

public class MainActivity extends AppCompatActivity {

    private Napster napster;
    private SessionManager sessionManager;
    MusicAppInfo mMusicAppInfo;
    NapsterLoginDialogFragment loginDialog;
    protected DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MusicApplication app = (MusicApplication) getApplication();
        napster = app.getNapster();
        mMusicAppInfo = app.getAppInfo();
        sessionManager = app.getSessionManager();
        dataService = new DataService(app.getAppInfo().getApiKey());

        if (sessionManager.isSessionOpen()){
            loadPlaylists();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        menu.findItem(R.id.menu_item_login).setVisible(!sessionManager.isSessionOpen());
        menu.findItem(R.id.menu_item_logout).setVisible(sessionManager.isSessionOpen());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_item_login) {
            login();
            return true;
        } else if (id == R.id.menu_item_logout) {
            logout();
            invalidateOptionsMenu();
            return true;
        } else if (id == R.id.menu_item_listening_history) {
            return true;
        } else if(id == R.id.menu_item_top_tracks) {
            return true;
        }
        return false;
    }

    private void login() {
        Toast.makeText(this, "login clicked...", Toast.LENGTH_SHORT).show();
        String loginUrl = napster.getLoginUrl(mMusicAppInfo.getRedirectUrl());
        loginDialog = NapsterLoginDialogFragment.newInstance(loginUrl, mMusicAppInfo);
        loginDialog.setLoginCallback(loginCallback);
        loginDialog.show(getSupportFragmentManager(), "login");

    }

    private void logout() {
        sessionManager.closeSession();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        PlayListFragment fragment = PlayListFragment.newIntance(null);
        fragmentTransaction.replace(R.id.playListFrame, fragment);
        fragmentTransaction.commit();
    }

    public void loadPlaylists(){

        new AuthorizedRequest<PlayLists>(Napster.getInstance().getSessionManager()) {
            @Override
            protected void onSessionValid() {
                dataService.getPlayListService().getPlayLists(getAuthorizationBearer(), this);
            }

            @Override
            protected void onError(NapsterError napsterError, RetrofitError retrofitError) {
                Toast.makeText(getBaseContext(), R.string.login_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(PlayLists playLists, Response response) {
                String res = getStringFromRetrofitResponse(response);
                ArrayList<PlayList> tmpPlayList = new ArrayList<PlayList>(playLists.playLists);
                for (PlayList p: tmpPlayList){
                    Log.d("mainactivity", "from loadPlaylists..." + p.Name + ":  " +  p.Id);
                }
                Log.d("mainactivity","hooking up fragment with data...");
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                PlayListFragment fragment = PlayListFragment.newIntance(tmpPlayList);
                fragmentTransaction.replace(R.id.playListFrame, fragment);
                fragmentTransaction.commit();
            }
        }.execute();

    }

    NapsterLoginCallback loginCallback = new NapsterLoginCallback() {
        @Override
        public void onLoginSuccess(AuthToken authToken) {
            sessionManager.openSession(authToken, new SessionCallback() {
                @Override
                public void onSuccess() {
                    loginDialog.dismiss();
                    invalidateOptionsMenu();
                    loadPlaylists();

                }

                @Override
                public void onError(NapsterError error) {
                    loginDialog.dismiss();
                    invalidateOptionsMenu();
                }
            });
        }

        @Override
        public void onLoginError(String url, Throwable e) {
            Toast.makeText(MainActivity.this, getString(R.string.login_error), Toast.LENGTH_LONG).show();
            loginDialog.dismiss();
        }

    };
}
