package com.sg.musicapp2.models;

import com.google.gson.annotations.SerializedName;
import com.napster.cedar.player.data.Track;

import java.util.List;

/**
 * Created by samgh on 3/3/2017.
 */

public class Tracks {

    @SerializedName("tracks")
    public final List<Track> tracks;

    public Tracks(List<Track> tracks) {
        this.tracks = tracks;
    }

}
