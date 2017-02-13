package com.sg.musicapp2.data;

/**
 * Created by samgh on 2/8/2017.
 */



import retrofit.Callback;
import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.Query;


public interface PlayListService {

    @GET("/v2.0/me/listens")
    public void getListeningHistory(
            @Header(Constants.AUTHORIZAION) String authorization,
            @Query(Constants.LIMIT) int limit,
            Callback<Response> callback);

    @GET("/v2.1/me/library/playlists")
    public void getPlayLists(
            @Header(Constants.AUTHORIZAION) String authorization,
            Callback<Response> callback);

}
