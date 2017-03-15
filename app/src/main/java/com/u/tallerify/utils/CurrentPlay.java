package com.u.tallerify.utils;

import android.support.annotation.Keep;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

    public @NonNull Builder newBuilder() {
        return new Builder()
            .currentSong(currentSong)
            .currentTime(currentTime)
            .playlist(playlist)
            .playState(playState)
            .repeat(repeat)
            .shuffle(shuffle)
            .volume(volume);
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

    public static class Builder {

        boolean shuffle;
        @Nullable RepeatMode repeat;
        @Nullable List<Song> playlist;
        long currentTime = -1;
        @Nullable PlayState playState;
        int volume = -1;
        @Nullable Song currentSong;

        public Builder() {
            super();
        }

        public Builder(@NonNull CurrentPlay play) {
            shuffle(play.shuffle());
            repeat(play.repeat());
            playlist(play.playlist());
            currentTime(play.currentTime());
            playState(play.playState());
            volume(play.volume());
            currentSong(play.currentSong());
        }

        public final @NonNull Builder shuffle(final boolean shuffle) {
            this.shuffle = shuffle;
            return this;
        }

        public final @NonNull Builder repeat(@NonNull final RepeatMode repeat) {
            this.repeat = repeat;
            return this;
        }

        public final @NonNull Builder currentSong(@NonNull final Song currentSong) {
            this.currentSong = currentSong;
            return this;
        }

        public final @NonNull Builder playlist(@NonNull final List<Song> playlist) {
            this.playlist = playlist;
            return this;
        }

        public final @NonNull Builder currentTime(@NonNull final long currentTime) {
            this.currentTime = currentTime;
            return this;
        }

        public final @NonNull Builder playState(@NonNull final PlayState playState) {
            this.playState = playState;
            return this;
        }

        public final @NonNull Builder volume(@NonNull final int volume) {
            this.volume = volume;
            return this;
        }

        public @NonNull CurrentPlay build() {
            if (buildable())
                return new CurrentPlay(this);
            else throw new IllegalStateException("Builder has an illegal state");
        }

        public boolean buildable() {
            boolean buildable = true;
            buildable &= shuffle;
            buildable &= repeat != null;
            buildable &= playlist != null;
            buildable &= currentTime != -1;
            buildable &= playState != null;
            buildable &= volume != -1;
            buildable &= currentSong != null;
            return buildable;
        }

    }

    public enum RepeatMode {
        NONE,
        SINGLE,
        ALL
    }

    public enum PlayState {
        PLAYING,
        PAUSED
    }

}
