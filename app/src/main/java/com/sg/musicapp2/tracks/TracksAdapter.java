package com.sg.musicapp2.tracks;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.napster.cedar.player.data.Track;
import com.sg.musicapp2.R;
import com.sg.musicapp2.models.Tracks;

import java.util.List;

/**
 * Created by samgh on 3/4/2017.
 */

public class TracksAdapter extends RecyclerView.Adapter<TracksViewHolder> {

    private Context mContext;
    private List<Track> mTracks;
    private TracksFragment.OnTrackSelectedListener mListener;

    public TracksAdapter(Context context, List<Track> tracks, TracksFragment.OnTrackSelectedListener listener){
        mContext = context;
        mTracks = tracks;
        mListener = listener;
    }

    @Override
    public TracksViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View trackView = inflater.inflate(R.layout.item_track, parent, false);
        TracksViewHolder tracksViewHolder = new TracksViewHolder(trackView);
        return tracksViewHolder;
    }

    @Override
    public void onBindViewHolder(TracksViewHolder holder, int position) {
        Track track = mTracks.get(position);
        holder.bindTrack(track, mListener);
    }

    @Override
    public int getItemCount() {
        return mTracks.size();
    }
}
