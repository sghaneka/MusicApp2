package com.sg.musicapp2;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.napster.cedar.player.PlaybackState;
import com.napster.cedar.player.data.Track;
import com.sg.musicapp2.tracklistplayer.RepeatMode;
import com.sg.musicapp2.tracklistplayer.TrackList;
import com.sg.musicapp2.tracklistplayer.TrackListPlayer;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class DemoPlayerFragment extends PlayerFragmentBase implements TrackListPlayer.SequenceChangeListener, AdapterView.OnItemLongClickListener  {

    TrackListPlayer trackListPlayer;
    public static final String ARG_TRACKS = "tracks";

    View vShuffle;
    ImageButton ibRepeat;
    int colorNormal, colorHighlighted;

    Drawable iconRepeat;
    Drawable iconRepeatSingle;
    ArrayList<Track> mTracks;
    MusicAppInfo appInfo;

    public DemoPlayerFragment() {
        // Required empty public constructor
    }

    public static DemoPlayerFragment newInstance(ArrayList<Track> tracks){
        DemoPlayerFragment demoPlayerFragment = new DemoPlayerFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRACKS, tracks);
        demoPlayerFragment.setArguments(args);
        return demoPlayerFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MusicApplication app = (MusicApplication) getActivity().getApplication();
        trackListPlayer = app.getTrackListPlayer();
        appInfo = app.getAppInfo();
        Resources res = getResources();
        colorHighlighted = res.getColor(R.color.accent_light);
        colorNormal = res.getColor(android.R.color.transparent);

        iconRepeat = res.getDrawable(R.drawable.repeat);
        iconRepeatSingle = res.getDrawable(R.drawable.repeat_one);
        mTracks = (ArrayList<Track>) getArguments().getSerializable(ARG_TRACKS);
        Log.d("demoplayerfragment", "let's see if the tracks are here...");
        for (Track p: mTracks){
            Log.d("demoplayerfragment", "from loadtracksfromplaylist..." + p.name + ":  " +  p.artistName);
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        trackListPlayer.setOnTrackChangeListener(this);
    }

    @Override
    public void onPause() {
        trackListPlayer.setOnTrackChangeListener(TrackListPlayer.SequenceChangeListener.EMPTY);
        super.onPause();
    }

    @Override
    protected void onPlaybackStopped() {

    }

    @Override
    protected void play() {
        if (player.getCurrentTrack() == null || player.getPlaybackState() == PlaybackState.STOPPED) {
            trackListPlayer.play();
        } else {
            player.resume();
        }
        updateTrackInfo();
    }

    @Override
    protected void stop() {
        player.stop();
    }

    @Override
    protected void pause() {
        player.pause();
    }

    @Override
    protected void playNextTrack() {
        trackListPlayer.playNextTrack();
    }

    protected void playPreviousTrack() {
        trackListPlayer.playPreviousTrack();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        LinearLayout playerButtonRow = (LinearLayout) view.findViewById(R.id.mini_player_button_row);
        appendAdditionalViews(playerButtonRow);

        tracksListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
                playTrack(pos);
                updateTrackInfo();
            }
        });

        trackAdapter.updateTracks(mTracks);
        trackListPlayer.setTrackList(new TrackList(mTracks));

        ibRepeat.setOnClickListener(this);
        vShuffle.setOnClickListener(this);

        tracksListView.setOnItemLongClickListener(this);
    }

    private void playTrack(int trackIndex) {
        trackListPlayer.playTrackAtIndex(trackIndex);
    }

    private void appendAdditionalViews(LinearLayout root) {
        Activity activity = getActivity();
        vShuffle = activity.getLayoutInflater().inflate(R.layout.btn_shuffle, root, false);
        root.addView(vShuffle, 0);
        View btnRepeat = activity.getLayoutInflater().inflate(R.layout.btn_repeat, root, false);
        root.addView(btnRepeat, root.getChildCount());
        ibRepeat = (ImageButton) btnRepeat.findViewById(R.id.repeat);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        if(view == ibRepeat) {
            trackListPlayer.setRepeatMode(getNextRepeatMode(trackListPlayer.getRepeatMode()));
        } else if(view == vShuffle) {
            trackListPlayer.toggleShuffleEnabled();
        }
    }

    private RepeatMode getNextRepeatMode(RepeatMode currentRepeatMode) {
        if (currentRepeatMode == RepeatMode.None) {
            return RepeatMode.All;
        }
        if (currentRepeatMode == RepeatMode.All) {
            return RepeatMode.Single;
        }
        return RepeatMode.None;
    }

    private void updateViewHighlighted(View view, boolean highlighted) {
        if(highlighted) {
            view.setBackgroundColor(colorHighlighted);
        } else {
            view.setBackgroundColor(colorNormal);
        }
    }

    @Override
    public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
        AlertDialog editTrackDialog = new AlertDialog.Builder(getActivity())
                .setPositiveButton(getString(R.string.delete), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        boolean shouldSkip = pos == trackListPlayer.getCurrentTrackIndex();
                        trackListPlayer.getTrackList().remove(pos);
                        trackAdapter.updateTracks(trackListPlayer.getTrackList().getTracks());
                        if(shouldSkip && trackListPlayer.canSkipForward()) {
                            trackListPlayer.playNextTrack();
                        } else if(shouldSkip) {
                            trackListPlayer.play();
                        }
                    }
                })
                .setNegativeButton(getString(R.string.duplicate), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        TrackList trackList = trackListPlayer.getTrackList();
                        trackList.add(trackList.getTracks().get(pos));
                        trackAdapter.updateTracks(trackListPlayer.getTrackList().getTracks());
                    }
                })
                .create();
        editTrackDialog.show();
        return true;
    }

    @Override
    public void onSequenceChanged() {
        Utils.updateViewVisibility(trackListPlayer.canSkipBackward(), btnPrevious);
        Utils.updateViewVisibility(trackListPlayer.canSkipForward(), btnNext);

        RepeatMode currentRepeatMode = trackListPlayer.getRepeatMode();

        updateRepeatIcon(currentRepeatMode);
        updateViewHighlighted(ibRepeat, isRepeating(currentRepeatMode));
        updateViewHighlighted(vShuffle, trackListPlayer.isShuffleEnabled());

        int currentTrackIndex = trackListPlayer.getCurrentTrackIndex();
        trackAdapter.setCurrentItem(currentTrackIndex);
        tracksListView.smoothScrollToPosition(currentTrackIndex);
    }

    private void updateRepeatIcon(RepeatMode currentRepeatMode) {
        if(currentRepeatMode == RepeatMode.Single) {
            ibRepeat.setImageDrawable(iconRepeatSingle);
        } else {
            ibRepeat.setImageDrawable(iconRepeat);
        }
    }

    private boolean isRepeating(RepeatMode repeatMode) {
        return repeatMode == RepeatMode.Single || repeatMode == RepeatMode.All;
    }
}
