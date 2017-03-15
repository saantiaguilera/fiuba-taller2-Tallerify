package com.u.tallerify.contract.base;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.utils.CurrentPlay;
import rx.Observable;

/**
 * Created by saguilera on 3/14/17.
 */
public interface MusicPlayerContract {

    interface View extends ContractView {

        void setCurrentPlay(final @NonNull CurrentPlay currentPlay);
        @NonNull Observable<Void> observeNextSongClicks();
        @NonNull Observable<Void> observePlayStateClicks();
        @NonNull Observable<Void> observePreviousSongClicks();
        @NonNull Observable<Integer> observeVolumeSeeks();
        @NonNull Observable<Integer> observeSongSeeks();
        @NonNull Observable<Void> observeShuffleClicks();
        @NonNull Observable<Void> observeRepeatClicks();

    }

    interface Presenter extends ContractPresenter {

    }

}
