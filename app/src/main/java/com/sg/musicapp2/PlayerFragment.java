package com.sg.musicapp2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napster.cedar.player.PlaybackState;
import com.napster.cedar.player.Player;
import com.napster.cedar.player.data.Track;
import com.sg.musicapp2.tracklistplayer.TrackListPlayer;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayerFragment extends Fragment {

    Player player;
    Track mTrack;

    public PlayerFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MusicApplication app = (MusicApplication) getActivity().getApplication();
        player = app.getPlayer();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_player2, container, false);
    }

    public void setTrack(Track t){
        mTrack = t;
        Log.d("playerfragment", "from playerfragment..." + t.name + ":  " +  t.id);
        player.play(mTrack);
    }



}
