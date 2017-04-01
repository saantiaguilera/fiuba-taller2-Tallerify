package com.u.tallerify.presenter.base.music_player;

import android.app.Application;
import android.database.ContentObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;
import com.u.tallerify.contract.base.music_player.MusicPlayerContract;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.utils.CurrentPlay;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import rx.Observable;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/15/17.
 */
@SuppressWarnings("ConstantConditions")
public class MusicPlayerPresenter extends Presenter<MusicPlayerContract.View>
        implements MusicPlayerContract.Presenter {

    private @Nullable ContentObserver contentObserver;

    @NonNull List<Long> favorites;
    @NonNull Map<Long, Integer> rateds;
    boolean logged;

    public MusicPlayerPresenter() {
        favorites = new ArrayList<>();
        rateds = new HashMap<>();
    }

    @Override
    protected void onAttach(@NonNull final MusicPlayerContract.View view) {
        render(view);

        observeProducers();
        observeView(view);
    }

    @Override
    protected void onDetach(@NonNull final MusicPlayerContract.View view) {
        super.onDetach(view);
        RxPlayerHelper.unbindAudioSystem((Application) getContext().getApplicationContext(), contentObserver);
    }

    @Override
    protected void onViewRequested(@NonNull final MusicPlayerContract.View view) {
        super.onViewRequested(view);
        render(view);
    }

    private void render(@NonNull final MusicPlayerContract.View view) {
        if (CurrentPlay.instance() != null) {
            if (CurrentPlay.instance() != null) {
                CurrentPlay currentPlay = CurrentPlay.instance();
                view.setShuffleEnabled(currentPlay.shuffle());
                view.setImage(currentPlay.currentSong().pictures().get(0));
                view.setName(currentPlay.currentSong().name(), currentPlay.currentSong().artistsName());
                view.setRepeatMode(currentPlay.repeat());
                view.setTime((int) currentPlay.currentTime(), (int) currentPlay.currentSong().duration());
                view.setTrackBarMax((int) currentPlay.currentSong().duration());
                view.setTrackBarProgress((int) currentPlay.currentTime());
                view.setVolume(currentPlay.volume());
                switch (currentPlay.playState()) {
                    case PLAYING:
                        view.setPlaying();
                        break;
                    case PAUSED:
                        view.setPaused();
                        break;
                }

                final List<String> names = new ArrayList<>();
                final List<String> urls = new ArrayList<>();
                Observable.from(CurrentPlay.instance().playlist())
                    .take(currentPlay.playlist().size() > 10 ? 10 :
                        currentPlay.playlist().size())
                    .doOnNext(new Action1<Song>() {
                        @Override
                        public void call(final Song song) {
                            names.add(song.name() + " - " + song.artistsName());
                            urls.add(song.pictures().get(0));
                        }
                    })
                    .doOnCompleted(new Action0() {
                        @Override
                        public void call() {
                            view.setQueue(names, urls);
                        }
                    })
                    .toBlocking()
                    .subscribe();

                if (favorites.contains(currentPlay.currentSong().id())) {
                    view.setFavorite(true, true);
                } else {
                    view.setFavorite(false, true);
                }

                if (rateds.containsKey(currentPlay.currentSong().id())) {
                    view.setRating(rateds.get(currentPlay.currentSong().id()), true);
                } else {
                    view.setRating(0, true);
                }
            }
        }
    }

    private void observeProducers() {
        // Observe for changes in the current play, we want to always request a render pass whenever our model changes
        CurrentPlay.observeCurrentPlay()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<CurrentPlay>bindToLifecycle())
            .subscribe(new Action1<CurrentPlay>() {
                @Override
                public void call(final CurrentPlay currentPlay) {
                    requestView();
                }
            });

        CredentialsInteractor.instance().observeToken()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<AccessToken>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<AccessToken>>() {
                @Override
                public void call(final ReactiveModel<AccessToken> reactiveModel) {
                    logged = reactiveModel.model() != null && !reactiveModel.hasError();
                    requestView();
                }
            });

        MeInteractor.instance().observeSongs()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Song>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Song>>>() {
                @Override
                public void call(final ReactiveModel<List<Song>> reactiveModel) {
                    if (reactiveModel.model() == null || reactiveModel.hasError())
                        return;

                    favorites = Observable.from(reactiveModel.model())
                        .observeOn(Schedulers.immediate())
                        .map(new Func1<Song, Long>() {
                            @Override
                            public Long call(final Song song) {
                                return song.id();
                            }
                        })
                        .toList()
                        .toBlocking()
                        .first();

                    requestView();
                }
            });


        // Register a content resolver for the music volume changes, whenever it changes, change the current play
        contentObserver = RxPlayerHelper.bindAudioSystem((Application) getContext().getApplicationContext());
    }

    private void observeView(@NonNull MusicPlayerContract.View view) {
        RxPlayerHelper.observePlayStateClicks(view);
        RxPlayerHelper.observeBackwardClicks(view);
        RxPlayerHelper.observeForwardClicks(view);
        RxPlayerHelper.observePlaylistSkips(view);
        RxPlayerHelper.observeRepeatClicks(view);
        RxPlayerHelper.observeShuffleClicks(view);
        RxPlayerHelper.observeTimeSeeks(view);
        RxPlayerHelper.observeVolumeSeeks((Application) getContext().getApplicationContext(), view);

        RxPlayerHelper.observeFavoriteClicks((Application) getContext().getApplicationContext(), view)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<Song>bindToView((View) view))
            .subscribe(new Action1<Song>() {
                @Override
                public void call(final Song song) {
                    if (favorites.contains(song.id())) {
                        favorites.remove(song.id());
                    } else {
                        favorites.add(song.id());
                    }

                    requestView();
                }
            });

        // We need the result because this comunicates with a backend
        RxPlayerHelper.observeRatingSeeks(view)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<Pair<Long, Integer>>bindToView((View) view))
            .subscribe(new Action1<Pair<Long, Integer>>() {
                @Override
                public void call(final Pair<Long, Integer> pair) {
                    rateds.put(pair.first, pair.second);
                    requestView();
                }
            });
    }

}
