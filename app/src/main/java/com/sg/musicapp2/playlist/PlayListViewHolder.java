package com.sg.musicapp2.playlist;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.musicapp2.R;
import com.sg.musicapp2.TracksActivity;

/**
 * Created by samgh on 2/18/2017.
 */

public class PlayListViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public TextView playlistTextView;
    public Button playListGoButton;
    private PlayList mPlayList;

    public PlayListViewHolder(View itemView) {
        super(itemView);
        playlistTextView = (TextView) itemView.findViewById(R.id.playlist_name);
        playListGoButton = (Button) itemView.findViewById(R.id.playlist_open_button);
        playListGoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("playlistviewholder", "playlist: id " + mPlayList.Id + " name: " + mPlayList.Name);
               Toast.makeText(v.getContext(),
                        "playlist: id " + mPlayList.Id + " name: " + mPlayList.Name,
                        Toast.LENGTH_SHORT).show();
                Intent i = TracksActivity.newIntent(v.getContext(), mPlayList);
                v.getContext().startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {

    }

    public void bindPlayList(PlayList playList){
        playlistTextView.setText(playList.Name);
        playListGoButton.setText("GO");
        mPlayList = playList;
    }
}
