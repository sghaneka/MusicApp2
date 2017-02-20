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

    ArrayList<PlayList> mPlayList;

    protected DataService dataService;

    private static final String ARG_PLAYLIST_ID = "playlist_id";

    public PlayListFragment() {
        // Required empty public constructor
    }

    public static PlayListFragment newIntance(ArrayList<PlayList> playLists){
        PlayListFragment f = new PlayListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLAYLIST_ID, playLists);
        f.setArguments(args);
        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MusicApplication app = (MusicApplication) getActivity().getApplication();
        dataService = new DataService(app.getAppInfo().getApiKey());
        Log.d("playlistfragment", "inside onCreate");
        if (savedInstanceState != null) {
            Log.d("playlistfragment", "on create savedinstancestate is not null");
            mPlayList = (ArrayList<PlayList>) savedInstanceState.getSerializable(ARG_PLAYLIST_ID);
            if (mPlayList != null){
                Log.d("playlistfragment", "mPlayList is not null");
            }else {
                Log.d("playlistfragment", "mPlayList is null");
            }
        } else{
            Log.d("playlistfragment", "saved instance state is null... oops");
        }

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("playlistfragment", "inside onCreateView");
        View view = inflater.inflate(R.layout.fragment_play_list, container, false);

        if (savedInstanceState != null){
            Log.d("playlistfragment", "savedinstancestate is not null");
            ArrayList<PlayList> p = (ArrayList<PlayList>) savedInstanceState.getSerializable(ARG_PLAYLIST_ID);

            if (p != null){
                Log.d("playlistfragment", "p is not null");
                RecyclerView rvPlayLists = (RecyclerView) view.findViewById(R.id.rvPlayLists);
                PlayListAdapter pa = new PlayListAdapter(getActivity(), p);
                rvPlayLists.setAdapter(pa);
                rvPlayLists.setLayoutManager(new LinearLayoutManager(getActivity()));
            }else{
                Log.d("playlistfragment", "p is null");
            }
        }
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }


}
