package com.u.tallerify.contract.base;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.model.entity.Picture;
import com.u.tallerify.utils.CurrentPlay;
import java.util.List;
import rx.Observable;

/**
 * Created by saguilera on 3/14/17.
 */
public interface MusicPlayerContract {

    /**
     * Ideally instead of setData and setUser it should have
     * setName/setImage/setFavoriteEnabled/etc.. But this is for university...
     */
    interface View extends ContractView {

        void setQueue(@NonNull List<String> names, @NonNull List<String> urls);
        void setImage(@NonNull Picture picture);
        void setPlaying();
        void setPaused();
        void setName(@NonNull String songName, @NonNull String artistName);
        void setTrackBarMax(int max);
        void setTrackBarProgress(int progress);
        void setTime(int time, int duration);
        void setVolume(int volume);
        void setShuffleEnabled(boolean enabled);
        void setRepeatMode(CurrentPlay.RepeatMode repeatMode);
        void setRating(int rating, boolean enabled);
        void setFavorite(boolean favved, boolean enabled);
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

    }

    interface Presenter extends ContractPresenter {

    }

}
