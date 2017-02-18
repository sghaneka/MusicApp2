package com.sg.musicapp2;

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
            showPlayList();
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

    private void showPlayList(){
        new AuthorizedRequest<Response>(Napster.getInstance().getSessionManager()) {
            @Override
            protected void onSessionValid() {
                // dataService.getPlayListService().getListeningHistory(getAuthorizationBearer(), 5, this);
             //   dataService.getPlayListService().getPlayLists(getAuthorizationBearer(), this);
            }

            @Override
            protected void onError(NapsterError napsterError, RetrofitError retrofitError) {
                Toast.makeText(MainActivity.this, R.string.login_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(Response tracks, Response response) {
                String res = getStringFromRetrofitResponse(tracks);
                Log.d("MainActivity", "from rest... " + res);
            }
        }.execute();
    }

    private void logout() {
        sessionManager.closeSession();
    }


    NapsterLoginCallback loginCallback = new NapsterLoginCallback() {
        @Override
        public void onLoginSuccess(AuthToken authToken) {
            sessionManager.openSession(authToken, new SessionCallback() {
                @Override
                public void onSuccess() {
                    loginDialog.dismiss();
                    invalidateOptionsMenu();
                    //sessionManager.getUser().getSubscriptionState().
                    //onLogin();
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
