package com.sg.musicapp2.playlist;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sg.musicapp2.R;

/**
 * Created by samgh on 2/18/2017.
 */

public class PlayListViewHolder extends RecyclerView.ViewHolder
        implements View.OnClickListener {

    public TextView playlistTextView;
    public Button playListGoButton;

    public PlayListViewHolder(View itemView) {
        super(itemView);
        playlistTextView = (TextView) itemView.findViewById(R.id.playlist_name);
        playListGoButton = (Button) itemView.findViewById(R.id.playlist_open_button);
    }

    @Override
    public void onClick(View v) {

    }
}
