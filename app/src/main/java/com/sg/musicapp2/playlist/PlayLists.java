package com.sg.musicapp2.playlist;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by samgh on 2/18/2017.
 */

public class PlayLists {
    @SerializedName("playlists")
    public final List<PlayList> playLists;

    public PlayLists(List<PlayList> playLists){
        this.playLists = playLists;
    }
}
