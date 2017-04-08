package com.u.tallerify.utils;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.annotation.NonNull;

/**
 * Created by saguilera on 4/7/17.
 */
public final class PlayManager {

    private static final PlayManager INSTANCE = new PlayManager();

    private @NonNull MediaPlayer mediaPlayer;

    private PlayManager() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    }

    public static final @NonNull PlayManager instance() {
        return INSTANCE;
    }

    public void resume() {
        mediaPlayer.start();
    }

    public void pause() {
        mediaPlayer.pause();
    }

    public void start() {

    }

    /**
     * Seek to a position
     * @param newTime in seconds
     */
    public void seek(long newTime) {
        mediaPlayer.seekTo((int) (newTime * 1000));
    }

}
