package com.u.tallerify.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.networking.interactor.artist.ArtistInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.networking.interactor.user.UserInteractor;
import rx.Observable;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/20/17.
 */

public class BussinessUtils {

    public static void requestBasicInfo(@NonNull Context context) {
        UserInteractor.instance().currentUser(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe();
        UserInteractor.instance().artists(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe();
        UserInteractor.instance().songs(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe();
        UserInteractor.instance().playlists(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe();
    }

    public static void requestTrendings(@NonNull Context context) {
        ArtistInteractor.instance().trendingArtists(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe();
        SongInteractor.instance().trendingSongs(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe();
    }

}
