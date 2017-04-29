package com.u.tallerify.contract.base.music_player;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.utils.CurrentPlay;
import com.u.tallerify.view.base.music_player.MusicPlayerView;
import java.util.List;
import rx.Observable;

/**
 * Created by saguilera on 3/14/17.
 */
public interface MusicPlayerContract {

    /**
     * Ideally instead of setData and setUser it should have
     * setName/setImage/setActionEnabled/etc.. But this is for university...
     */
    interface View extends ContractView {

        void setQueue(@NonNull List<String> names, @NonNull List<String> urls);
        void setImage(@NonNull String picture);
        void setPlaying();
        void setPaused();
        void setName(@NonNull String songName, @NonNull String artistName);
        void setTrackBarMax(int max);
        void setTrackBarProgress(int progress);
        void setTime(int time, int duration);
        void setVolume(int volume);
        void setShuffleEnabled(boolean enabled);
        void setRepeatMode(CurrentPlay.RepeatMode repeatMode);
        void setRating(int rating);
        void setFavorite(boolean favved);
        void setMode(@NonNull MusicPlayerView.MODE mode);
        @NonNull Observable<Void> observeNextSongClicks();
        @NonNull Observable<Void> observePlayStateClicks();
        @NonNull Observable<Void> observePreviousSongClicks();
        @NonNull Observable<Integer> observeVolumeSeeks();
        @NonNull Observable<Integer> observeSongSeeks();
        @NonNull Observable<Void> observeShuffleClicks();
        @NonNull Observable<Void> observeRepeatClicks();
        @NonNull Observable<Integer> observeRatingSeeks();
        @NonNull Observable<Boolean> observeFavoriteClicks();
        @NonNull Observable<Integer> observePlaylistSkipClicks();
        @NonNull MusicPlayerView.MODE currentMode();

    }

    interface Presenter extends ContractPresenter {

    }

}
