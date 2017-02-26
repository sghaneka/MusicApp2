package com.sg.musicapp2.playlist;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.sg.musicapp2.R;

import java.util.List;

/**
 * Created by samgh on 2/18/2017.
 */

public class PlayListAdapter extends RecyclerView.Adapter<PlayListViewHolder> {

    private Context mContext;
    private List<PlayList> mPlayLists;

    public PlayListAdapter(Context context, List<PlayList> playLists){
        mContext = context;
        mPlayLists = playLists;
    }

    @Override
    public PlayListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View playListView = inflater.inflate(R.layout.item_playlist, parent, false);
        PlayListViewHolder viewHolder = new PlayListViewHolder(playListView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PlayListViewHolder holder, int position) {
        PlayList playList = mPlayLists.get(position);
        TextView textView = holder.playlistTextView;
        textView.setText(playList.Name);
        Button button = holder.playListGoButton;
        button.setText("GO");
    }

    @Override
    public int getItemCount() {
        return mPlayLists.size();
    }
}
