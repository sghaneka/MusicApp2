package com.sg.musicapp2;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sg.musicapp2.data.DataService;
import com.sg.musicapp2.playlist.Contact;
import com.sg.musicapp2.models.PlayList;
import com.sg.musicapp2.playlist.PlayListAdapter;

import java.util.ArrayList;


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
        PlayListFragment playListFragment = new PlayListFragment();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PLAYLIST_ID, playLists);
        playListFragment.setArguments(args);
        return playListFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        MusicApplication app = (MusicApplication) getActivity().getApplication();
        dataService = new DataService(app.getAppInfo().getApiKey());
        mPlayList = (ArrayList<PlayList>) getArguments().getSerializable(ARG_PLAYLIST_ID);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_play_list, container, false);
        TextView msgTxt = (TextView) view.findViewById(R.id.textLoginMessage);
        if (mPlayList != null){
            RecyclerView rvPlayLists = (RecyclerView) view.findViewById(R.id.rvPlayLists);
            PlayListAdapter pa = new PlayListAdapter(getActivity(), mPlayList);
            rvPlayLists.setAdapter(pa);
            rvPlayLists.setLayoutManager(new LinearLayoutManager(getActivity()));
            msgTxt.setVisibility(View.GONE);
        } else{
            msgTxt.setVisibility(View.VISIBLE);
        }
        return view;
    }

    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
    }


}
