package com.sg.musicapp2.tracks;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napster.cedar.player.data.Track;
import com.sg.musicapp2.R;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TracksFragment extends Fragment {

    public static final String ARG_TRACKS = "tracks";
    ArrayList<Track> mTracks;
    private OnTrackSelectedListener listener;

    public TracksFragment() {
        // Required empty public constructor
    }

    public static TracksFragment newInstance(ArrayList<Track> tracks){
        TracksFragment tracksFragment = new TracksFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_TRACKS, tracks);
        tracksFragment.setArguments(args);
        return tracksFragment;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        if( context instanceof OnTrackSelectedListener){
            listener = (OnTrackSelectedListener) context;
        } else {
            throw new ClassCastException(context.toString() + " must implement TracksFragment.OnTrackSelectedListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        mTracks = (ArrayList<Track>) getArguments().getSerializable(ARG_TRACKS);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_tracks, container, false);
        RecyclerView rvTracks = (RecyclerView) view.findViewById(R.id.rvTracks);
        TracksAdapter tracksAdapter = new TracksAdapter(getActivity(), mTracks, listener);
        rvTracks.setAdapter(tracksAdapter);
        rvTracks.setLayoutManager(new LinearLayoutManager(getActivity()));
        return view;
    }

    public interface OnTrackSelectedListener {
        public void onTrackSelected(Track track);
    }

}
