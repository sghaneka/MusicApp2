package com.sg.musicapp2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.napster.cedar.AuthorizedRequest;
import com.napster.cedar.Napster;
import com.napster.cedar.NapsterError;
import com.napster.cedar.player.data.Track;
import com.napster.cedar.session.SessionManager;
import com.sg.musicapp2.data.DataService;
import com.sg.musicapp2.models.PlayList;
import com.sg.musicapp2.models.PlayLists;
import com.sg.musicapp2.models.Tracks;

import java.util.ArrayList;

import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.sg.musicapp2.helpers.RetroFitUtil.getStringFromRetrofitResponse;

public class TracksActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYLIST_ID = "playlist_id";

    private PlayList mPlayList;

    private TextView mTextView;

    private Napster napster;
    private SessionManager sessionManager;
    MusicAppInfo mMusicAppInfo;
    protected DataService dataService;


    public static Intent newIntent(Context packageContext, PlayList item){
        Intent i = new Intent(packageContext, TracksActivity.class);
        i.putExtra(EXTRA_PLAYLIST_ID, item);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);

        MusicApplication app = (MusicApplication) getApplication();
        napster = app.getNapster();
        mMusicAppInfo = app.getAppInfo();
        sessionManager = app.getSessionManager();
        dataService = new DataService(app.getAppInfo().getApiKey());


        mPlayList = (PlayList) getIntent().getSerializableExtra(EXTRA_PLAYLIST_ID);
        mTextView = (TextView) findViewById(R.id.txtTitle);
        mTextView.setText(mPlayList.Name);

        if (sessionManager.isSessionOpen()){
            loadPlaylistDetails();
        }
    }

    public void loadPlaylistDetails(){
        new AuthorizedRequest<Tracks>(Napster.getInstance().getSessionManager()) {
            @Override
            protected void onSessionValid() {
                Log.d("tracksactivity", "invoking... playlistid " + mPlayList.Id);
                dataService.getPlayListService().getPlayListTracks(getAuthorizationBearer(), mPlayList.Id, this);
            }

            @Override
            protected void onError(NapsterError napsterError, RetrofitError retrofitError) {
                Toast.makeText(getBaseContext(), R.string.login_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(Tracks tracks, Response response) {
                String res = getStringFromRetrofitResponse(response);
                ArrayList<Track> tmpTracks = new ArrayList<Track>(tracks.tracks);
                for (Track p: tmpTracks){
                    Log.d("tracksActivity", "from loadtracksfromplaylist..." + p.name + ":  " +  p.artistName);
                }

            }
        }.execute();
    }
}
