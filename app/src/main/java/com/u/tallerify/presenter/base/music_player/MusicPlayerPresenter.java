package com.u.tallerify.presenter.base.music_player;

import android.app.Application;
import android.database.ContentObserver;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.view.View;
import com.u.tallerify.contract.base.music_player.MusicPlayerContract;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.model.Rating;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
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
    @Nullable Rating rating;
    boolean logged;

    boolean justAttached;

    public MusicPlayerPresenter() {
        favorites = new ArrayList<>();
    }

    @Override
    protected void onAttach(@NonNull final MusicPlayerContract.View view) {
        observeProducers();
        observeView(view);
        justAttached = true;
    }

    @Override
    protected void onDetach(@NonNull final MusicPlayerContract.View view) {
        super.onDetach(view);
        // We need to check it to avoid when app closes (because of last backpressed)
        if (getContext() != null) {
            RxPlayerHelper.unbindAudioSystem((Application) getContext().getApplicationContext(), contentObserver);
        }
    }

    protected void onRender(@NonNull final MusicPlayerContract.View view) {
        if (CurrentPlay.instance() != null) {
            CurrentPlay currentPlay = CurrentPlay.instance();

            if (justAttached || currentPlay.hasValueChanged(CurrentPlay.KEY_SHUFFLE)) {
                view.setShuffleEnabled(currentPlay.shuffle());
            }

            if (justAttached || currentPlay.hasValueChanged(CurrentPlay.KEY_SONG)) {
                view.setImage(currentPlay.song().pictures().get(0));
                view.setName(currentPlay.song().name(), currentPlay.song().artistsName());
            }

            if (justAttached || currentPlay.hasValueChanged(CurrentPlay.KEY_REPEAT)) {
                view.setRepeatMode(currentPlay.repeat());
            }

            if (justAttached || currentPlay.hasValueChanged(CurrentPlay.KEY_TIME) ||
                currentPlay.hasValueChanged(CurrentPlay.KEY_DURATION)) {
                view.setTime((int) currentPlay.time(), (int) currentPlay.duration());
                view.setTrackBarProgress((int) currentPlay.time());
                view.setTrackBarMax((int) currentPlay.duration());
            }

            if (justAttached || currentPlay.hasValueChanged(CurrentPlay.KEY_VOLUME)) {
                view.setVolume(currentPlay.volume());
            }

            if (justAttached || currentPlay.hasValueChanged(CurrentPlay.KEY_PLAYSTATE)) {
                switch (currentPlay.playState()) {
                    case PLAYING:
                        view.setPaused();
                        break;
                    case PAUSED:
                        view.setPlaying();
                        break;
                }
            }

            if (justAttached || currentPlay.hasValueChanged(CurrentPlay.KEY_PLAYLIST)) {
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
            }

            if (favorites.contains(currentPlay.song().id())) {
                view.setFavorite(true);
            } else {
                view.setFavorite(false);
            }

            if (rating != null && rating.song().id() == currentPlay.song().id()) {
                view.setRating(rating.rating());
            } else {
                view.setRating(0);
                requestRatingFor(currentPlay.song());
            }

            justAttached = false;
        }
    }

    private void observeProducers() {
        // Observe for changes in the current play, we want to always request a onRender pass whenever our model changes
        CurrentPlay.observeCurrentPlay()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<CurrentPlay>bindToLifecycle())
            .subscribe(new Action1<CurrentPlay>() {
                @Override
                public void call(final CurrentPlay currentPlay) {
                    requestRender();
                }
            });

        CredentialsInteractor.instance().observeToken()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<ReactiveModel<AccessToken>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<AccessToken>>() {
                @Override
                public void call(final ReactiveModel<AccessToken> reactiveModel) {
                    logged = reactiveModel.model() != null && !reactiveModel.hasError();
                    requestRender();
                }
            });

        MeInteractor.instance().observeSongs()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
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

                    requestRender();
                }
            });


        // Register a content resolver for the music volume changes, whenever it changes, change the current play
        contentObserver = RxPlayerHelper.bindAudioSystem((Application) getContext().getApplicationContext());
    }

    void requestRatingFor(@NonNull Song song) {
        SongInteractor.instance().rate(getContext(), song)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<Rating>bindToLifecycle())
            .subscribe(new Action1<Rating>() {
                @Override
                public void call(final Rating response) {
                    rating = response;
                    requestRender();
                }
            }, Interactors.ACTION_ERROR);
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

        RxPlayerHelper.observeFavoriteClicks(getContext(), view)
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Song>bindToView((View) view))
            .subscribe(new Action1<Song>() {
                @Override
                public void call(final Song song) {
                    if (favorites.contains(song.id())) {
                        favorites.remove(song.id());
                    } else {
                        favorites.add(song.id());
                    }

                    requestRender();
                }
            });

        // We need the result because this comunicates with a backend
        RxPlayerHelper.observeRatingSeeks(getContext(), view)
            .observeOn(Schedulers.computation())
            .compose(this.<Pair<Song, Integer>>bindToView((View) view))
            .subscribe(new Action1<Pair<Song, Integer>>() {
                @Override
                public void call(final Pair<Song, Integer> pair) {
                    rating = new Rating.Builder()
                        .rating(pair.second)
                        .song(pair.first)
                        .build();
                    requestRender();
                }
            });
    }

}
