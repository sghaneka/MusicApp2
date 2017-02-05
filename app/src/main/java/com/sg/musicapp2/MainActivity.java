package com.sg.musicapp2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.napster.cedar.Napster;
import com.napster.cedar.NapsterError;
import com.napster.cedar.session.AuthToken;
import com.napster.cedar.session.SessionCallback;
import com.napster.cedar.session.SessionManager;
import com.sg.musicapp2.login.NapsterLoginCallback;
import com.sg.musicapp2.login.NapsterLoginDialogFragment;

public class MainActivity extends AppCompatActivity {

    private Napster napster;
    private SessionManager sessionManager;
    MyLocalAppInfo mMyLocalAppInfo;
    NapsterLoginDialogFragment loginDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainApplication app = (MainApplication) getApplication();
        napster = app.getNapster();
        mMyLocalAppInfo = app.getAppInfo();
        sessionManager = app.getSessionManager();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        menu.findItem(R.id.menu_item_login).setVisible(true);
        menu.findItem(R.id.menu_item_logout).setVisible(false);
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

    private void logout() {

    }

    NapsterLoginCallback loginCallback = new NapsterLoginCallback() {
        @Override
        public void onLoginSuccess(AuthToken authToken) {
            sessionManager.openSession(authToken, new SessionCallback() {
                @Override
                public void onSuccess() {
                    loginDialog.dismiss();
                    invalidateOptionsMenu();
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
