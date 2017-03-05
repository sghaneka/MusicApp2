package com.sg.musicapp2.tracklistplayer.sequence;


import com.sg.musicapp2.tracklistplayer.RepeatMode;
import com.sg.musicapp2.tracklistplayer.TrackList;

public interface TrackSequencer extends TrackList.ChangeListener{

    boolean canSkipBackward(RepeatMode repeatMode);
    boolean canSkipForward(RepeatMode repeatMode);
    boolean canSkipBackward();
    boolean canSkipForward();

    void skipBackward();
    void skipForward();

    void skipToFirst();
    void skipToLast();

    void setCurrentTrack(int index);
    SequenceItem getCurrentTrack();
    int getCurrentTrackIndex();

    void setupInitialTrack();
}
