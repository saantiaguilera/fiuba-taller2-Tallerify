package com.u.tallerify.utils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import com.trello.rxlifecycle.android.RxLifecycleAndroid;
import com.u.tallerify.networking.interactor.artist.ArtistInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.networking.interactor.user.UserInteractor;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.u.tallerify.networking.interactor.Interactors.ACTION_ERROR;
import static com.u.tallerify.networking.interactor.Interactors.ACTION_NEXT;

/**
 * Created by saguilera on 3/20/17.
 */

public class BussinessUtils {

    public static void requestBasicInfo(@NonNull Context context) {
        UserInteractor.instance().currentUser(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(ACTION_NEXT, new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        UserInteractor.instance().artists(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(ACTION_NEXT, new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        UserInteractor.instance().songs(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(ACTION_NEXT, new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
        UserInteractor.instance().playlists(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(ACTION_NEXT, new Action1<Throwable>() {
                @Override
                public void call(final Throwable throwable) {
                    throwable.printStackTrace();
                }
            });
    }

    public static void requestTrendings(@NonNull Context context) {
        ArtistInteractor.instance().trendingArtists(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(ACTION_NEXT, ACTION_ERROR);
        SongInteractor.instance().trendingSongs(context)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .subscribe(ACTION_NEXT, ACTION_ERROR);
    }

}
