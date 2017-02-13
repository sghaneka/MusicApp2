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
import com.sg.musicapp2.data.Metadata;
import com.sg.musicapp2.login.NapsterLoginCallback;
import com.sg.musicapp2.login.NapsterLoginDialogFragment;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    private Napster napster;
    private SessionManager sessionManager;
    MyLocalAppInfo mMyLocalAppInfo;
    NapsterLoginDialogFragment loginDialog;
    protected Metadata metadata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainApplication app = (MainApplication) getApplication();
        napster = app.getNapster();
        mMyLocalAppInfo = app.getAppInfo();
        sessionManager = app.getSessionManager();
        metadata = new Metadata(app.getAppInfo().getApiKey());
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
        String loginUrl = napster.getLoginUrl(mMyLocalAppInfo.getRedirectUrl());
        loginDialog = NapsterLoginDialogFragment.newInstance(loginUrl, mMyLocalAppInfo);
        loginDialog.setLoginCallback(loginCallback);
        loginDialog.show(getSupportFragmentManager(), "login");

    }

    private void showPlayList(){
        new AuthorizedRequest<Response>(Napster.getInstance().getSessionManager()) {
            @Override
            protected void onSessionValid() {
                // metadata.getPlayListService().getListeningHistory(getAuthorizationBearer(), 5, this);
                metadata.getPlayListService().getPlayLists(getAuthorizationBearer(), this);
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

    public static String getStringFromRetrofitResponse(Response response) {
        //Try to get response body
        BufferedReader reader = null;
        StringBuilder sb = new StringBuilder();
        try {

            reader = new BufferedReader(new InputStreamReader(response.getBody().in()));

            String line;

            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return sb.toString();

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
