package com.sg.musicapp2;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.sg.musicapp2.playlist.PlayList;

public class TracksActivity extends AppCompatActivity {

    private static final String EXTRA_PLAYLIST_ID = "playlist_id";

    private PlayList mPlayList;

    private TextView mTextView;


    public static Intent newIntent(Context packageContext, PlayList item){
        Intent i = new Intent(packageContext, TracksActivity.class);
        i.putExtra(EXTRA_PLAYLIST_ID, item);
        return i;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracks);
        mPlayList = (PlayList) getIntent().getSerializableExtra(EXTRA_PLAYLIST_ID);
        mTextView = (TextView) findViewById(R.id.txtTitle);
        mTextView.setText(mPlayList.Name);
    }
}
