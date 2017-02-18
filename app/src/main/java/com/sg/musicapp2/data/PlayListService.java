package com.sg.musicapp2.data;

/**
 * Created by samgh on 2/8/2017.
 */



import com.sg.musicapp2.playlist.PlayLists;

import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;


public interface PlayListService {

    @GET("/v2.1/me/library/playlists")
    public void getPlayLists(
            @Header(Constants.AUTHORIZAION) String authorization,
            Callback<PlayLists> callback);

}
