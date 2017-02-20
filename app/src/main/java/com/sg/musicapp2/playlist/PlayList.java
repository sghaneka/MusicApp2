package com.sg.musicapp2.playlist;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by samgh on 2/18/2017.
 */

public class PlayList implements Serializable {

    @SerializedName("name")
    public String Name;

    @SerializedName("id")
    public String Id;

}
