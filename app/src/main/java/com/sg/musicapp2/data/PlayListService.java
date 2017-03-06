package com.sg.musicapp2.data;

/**
 * Created by samgh on 2/8/2017.
 */



import com.sg.musicapp2.models.PlayLists;
import com.sg.musicapp2.models.Tracks;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Path;


public interface PlayListService {

    @GET("/v2.1/me/library/playlists")
    public void getPlayLists(
            @Header(Constants.AUTHORIZAION) String authorization,
            Callback<PlayLists> callback);

    @GET("/v2.1/me/library/playlists/{playlistId}")
    public void getPlayListDetails(
            @Header(Constants.AUTHORIZAION) String authorization,
            @Path("playlistId") String playlistId,
            Callback<PlayLists> callback);

    @GET("/v2.1/me/library/playlists/{playlistId}/tracks?limit=10")
    public void getPlayListTracks(
            @Header(Constants.AUTHORIZAION) String authorization,
            @Path("playlistId") String playlistId,
            Callback<Tracks> callback);
}
