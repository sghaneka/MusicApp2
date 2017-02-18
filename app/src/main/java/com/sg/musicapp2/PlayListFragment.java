package com.sg.musicapp2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.napster.cedar.AuthorizedRequest;
import com.napster.cedar.Napster;
import com.napster.cedar.NapsterError;
import com.sg.musicapp2.data.DataService;
import com.sg.musicapp2.playlist.Contact;
import com.sg.musicapp2.playlist.ContactsAdapter;
import com.sg.musicapp2.playlist.PlayList;
import com.sg.musicapp2.playlist.PlayListAdapter;
import com.sg.musicapp2.playlist.PlayLists;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

import static com.sg.musicapp2.helpers.RetroFitUtil.getStringFromRetrofitResponse;


/**
 * A simple {@link Fragment} subclass.
 */
public class PlayListFragment extends Fragment {

    ArrayList<Contact> contacts;

    ArrayList<PlayList> playLists;

    protected DataService dataService;

    public PlayListFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MusicApplication app = (MusicApplication) getActivity().getApplication();
        dataService = new DataService(app.getAppInfo().getApiKey());
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        loadPlaylists(view);
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }

    private void loadPlaylists(final View view){

       new AuthorizedRequest<PlayLists>(Napster.getInstance().getSessionManager()) {
            @Override
            protected void onSessionValid() {
                dataService.getPlayListService().getPlayLists(getAuthorizationBearer(), this);
            }

            @Override
            protected void onError(NapsterError napsterError, RetrofitError retrofitError) {
                Toast.makeText(getActivity(), R.string.login_error, Toast.LENGTH_LONG).show();
            }

            @Override
            public void success(PlayLists playLists, Response response) {
                String res = getStringFromRetrofitResponse(response);
                //Log.d("MainActivity", "from rest... " + res);
                for (PlayList p: playLists.playLists){
                    Log.d("playlistfragment", p.Name + ":  " +  p.Id);
                }

                RecyclerView rvPlayLists = (RecyclerView) view.findViewById(R.id.rvPlayLists);
                PlayListAdapter pa = new PlayListAdapter(getActivity(), playLists.playLists);
                rvPlayLists.setAdapter(pa);
                rvPlayLists.setLayoutManager(new LinearLayoutManager(getActivity()));
            }
        }.execute();

    }



}
