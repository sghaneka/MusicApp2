package com.sg.musicapp2.tracks;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.napster.cedar.player.data.Track;
import com.sg.musicapp2.R;

/**
 * Created by samgh on 3/4/2017.
 */

public class TracksViewHolder extends RecyclerView.ViewHolder {

    public TextView trackNameTextView;
    public Button playBtn;
    private Track mTrack;


    public TracksViewHolder(View itemView) {
        super(itemView);
        trackNameTextView = (TextView) itemView.findViewById(R.id.track_name);
        playBtn = (Button) itemView.findViewById(R.id.playBtn);
        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(),
                        "track: id" + mTrack.id + " track: name: " +
                                mTrack.name, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void bindTrack(Track track){
        trackNameTextView.setText(track.name);
        playBtn.setText("Play");
        mTrack = track;
    }
}
