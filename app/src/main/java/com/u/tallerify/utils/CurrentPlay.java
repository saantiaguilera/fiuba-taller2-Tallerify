package com.u.tallerify.utils;

import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.annotations.KeepName;
import com.u.tallerify.model.entity.Song;
import java.util.List;
import rx.Observable;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/14/17.
 */
@Keep
@SuppressWarnings("unchecked")
public class CurrentPlay {

    public static final int KEY_SHUFFLE = 0;
    public static final int KEY_REPEAT = 1;
    public static final int KEY_PLAYLIST = 2;
    public static final int KEY_SONG = 3;
    public static final int KEY_TIME = 4;
    public static final int KEY_PLAYSTATE = 5;
    public static final int KEY_VOLUME = 6;

    private @NonNull Bundle changedValuesBundle;

    private boolean shuffle;
    private @NonNull RepeatMode repeat;

    private @NonNull List<Song> playlist;
    private @NonNull Song currentSong;

    private long currentTime;

    private @NonNull PlayState playState;

    private int volume;

    public static @Nullable BehaviorSubject<CurrentPlay> currentPlayBehaviorSubject;

    private static @Nullable CurrentPlay instance;

    public static @Nullable CurrentPlay instance() {
        return instance;
    }

    public static @NonNull CurrentPlay.Builder defaults(@NonNull Context context) {
        // TODO for a next iteration save settings of the user controls
        AudioManager audioManager = (AudioManager) context.getApplicationContext()
            .getSystemService(Context.AUDIO_SERVICE);
        return new Builder()
            .currentTime(0)
            .playState(PlayState.PAUSED)
            .repeat(RepeatMode.NONE)
            .shuffle(false)
            .volume(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));
    }

    protected CurrentPlay() {
        instance = this;
        if (currentPlayBehaviorSubject != null) {
            currentPlayBehaviorSubject.onNext(this);
        }
    }

    protected CurrentPlay(@NonNull CurrentPlay.Builder builder) {
        instance = this;

        shuffle = builder.shuffle;
        repeat = builder.repeat;
        playlist = builder.playlist;
        currentTime = builder.currentTime;
        playState = builder.playState;
        volume = builder.volume;
        currentSong = builder.currentSong;
        changedValuesBundle = builder.changedBundle;

        if (currentPlayBehaviorSubject != null) {
            currentPlayBehaviorSubject.onNext(this);
        }
    }

    public @NonNull Song currentSong() {
        return currentSong;
    }

    public boolean shuffle() {
        return shuffle;
    }

    public @NonNull RepeatMode repeat() {
        return repeat;
    }

    public @NonNull List<Song> playlist() {
        return playlist;
    }

    public long currentTime() {
        return currentTime;
    }

    public @NonNull PlayState playState() {
        return playState;
    }

    public int volume() {
        return volume;
    }

    public boolean hasValueChanged(int key) {
        return changedValuesBundle.getBoolean(String.valueOf(key), false);
    }

    public @NonNull Builder newBuilder() {
        return new Builder(this);
    }

    public static @NonNull Observable<CurrentPlay> observeCurrentPlay() {
        return currentPlayBehaviorSubject == null ?
            currentPlayBehaviorSubject = BehaviorSubject.create() :
            currentPlayBehaviorSubject;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CurrentPlay)) {
            return false;
        }

        final CurrentPlay that = (CurrentPlay) o;

        if (shuffle != that.shuffle) {
            return false;
        }
        if (currentTime != that.currentTime) {
            return false;
        }
        if (volume != that.volume) {
            return false;
        }
        if (repeat != that.repeat) {
            return false;
        }
        if (!playlist.equals(that.playlist)) {
            return false;
        }
        if (!currentSong.equals(that.currentSong)) {
            return false;
        }
        return playState == that.playState;
    }

    @Override
    public int hashCode() {
        int result = (shuffle ? 1 : 0);
        result = 31 * result + repeat.hashCode();
        result = 31 * result + playlist.hashCode();
        result = 31 * result + currentSong.hashCode();
        result = 31 * result + (int) (currentTime ^ (currentTime >>> 32));
        result = 31 * result + playState.hashCode();
        result = 31 * result + volume;
        return result;
    }

    @KeepName
    public static class Builder {

        boolean shuffle;
        @Nullable RepeatMode repeat;
        @Nullable List<Song> playlist;
        long currentTime = -1;
        @Nullable PlayState playState;
        int volume = -1;
        @Nullable Song currentSong;

        @NonNull Bundle changedBundle = new Bundle();

        public Builder() {
            super();
        }

        public Builder(@NonNull CurrentPlay play) {
            shuffle = play.shuffle();
            repeat = play.repeat();
            playlist = play.playlist();
            currentTime = play.currentTime();
            playState = play.playState();
            volume = play.volume();
            currentSong = play.currentSong();
        }

        public final @NonNull Builder shuffle(final boolean shuffle) {
            this.shuffle = shuffle;
            changedBundle.putBoolean(String.valueOf(KEY_SHUFFLE), true);
            return this;
        }

        public final @NonNull Builder repeat(@NonNull final RepeatMode repeat) {
            this.repeat = repeat;
            changedBundle.putBoolean(String.valueOf(KEY_REPEAT), true);
            return this;
        }

        public final @NonNull Builder currentSong(@NonNull final Song currentSong) {
            this.currentSong = currentSong;
            changedBundle.putBoolean(String.valueOf(KEY_SONG), true);
            return this;
        }

        public final @NonNull Builder playlist(@NonNull final List<Song> playlist) {
            this.playlist = playlist;
            changedBundle.putBoolean(String.valueOf(KEY_PLAYLIST), true);
            return this;
        }

        public final @NonNull Builder currentTime(@NonNull final long currentTime) {
            this.currentTime = currentTime;
            changedBundle.putBoolean(String.valueOf(KEY_TIME), true);
            return this;
        }

        public final @NonNull Builder playState(@NonNull final PlayState playState) {
            this.playState = playState;
            changedBundle.putBoolean(String.valueOf(KEY_PLAYSTATE), true);
            return this;
        }

        public final @NonNull Builder volume(@NonNull final int volume) {
            this.volume = volume;
            changedBundle.putBoolean(String.valueOf(KEY_VOLUME), true);
            return this;
        }

        public @NonNull CurrentPlay build() {
            if (buildable())
                return new CurrentPlay(this);
            else throw new IllegalStateException("Builder has an illegal state");
        }

        public boolean buildable() {
            boolean buildable = true;
            buildable &= repeat != null;
            buildable &= currentTime != -1;
            buildable &= playState != null;
            buildable &= volume != -1;
            buildable &= currentSong != null;
            buildable &= playlist != null;
            return buildable;
        }

    }

    @Keep
    public enum RepeatMode {
        NONE,
        SINGLE,
        ALL
    }

    @Keep
    public enum PlayState {
        PLAYING,
        PAUSED
    }

}
