package com.u.tallerify.presenter.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.abstracts.GenericGridContract;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.artist.ArtistInteractor;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.supplier.home.card.HeaderCardSupplier;
import com.u.tallerify.supplier.home.card.HorizontalCardSupplier;
import com.u.tallerify.supplier.home.card.NoAccountCardSupplier;
import com.u.tallerify.supplier.home.card.PlayableCardSupplier;
import com.u.tallerify.utils.adapter.GenericAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/12/17.
 */
public class HomePresenter extends Presenter<GenericGridContract.View>
        implements GenericGridContract.Presenter {

    @Nullable List<Artist> userArtists;
    @Nullable List<Song> userSongs;
    @Nullable List<Playlist> userPlaylists;
    @Nullable List<Song> trendingSongs;
    @Nullable List<Artist> trendingArtists;
    boolean loggedIn;

    @NonNull BehaviorSubject<Void> notifierArtists;
    @NonNull BehaviorSubject<Void> notifierSongs;
    @NonNull BehaviorSubject<Void> notifierPlaylists;
    @NonNull BehaviorSubject<Void> notifierTrendingSongs;
    @NonNull BehaviorSubject<Void> notifierTrendingArtists;
    @NonNull BehaviorSubject<Void> notifierLogin;

    @Nullable List<GenericAdapter.ItemSupplier> dataSnapshot;

    @Override
    protected void onAttach(@NonNull final GenericGridContract.View view) {
        notifierArtists = BehaviorSubject.create((Void) null);
        notifierSongs = BehaviorSubject.create((Void) null);
        notifierPlaylists = BehaviorSubject.create((Void) null);
        notifierTrendingSongs = BehaviorSubject.create((Void) null);
        notifierTrendingArtists = BehaviorSubject.create((Void) null);
        notifierLogin = BehaviorSubject.create((Void) null);
        observeNotifier();
        observeRepositories();
    }

    @Override
    protected void onViewRequested(@NonNull final GenericGridContract.View view) {
        super.onViewRequested(view);
        consumeSnapshot(view);
    }

    /**
     * Consume a snapshot of data and draw the view with it
     */
    private void consumeSnapshot(@NonNull final GenericGridContract.View view) {
        if (dataSnapshot != null) {
            view.setData(dataSnapshot);
            dataSnapshot = null;
        }
    }

    /**
     * Observe the data we want to react from
     */
    private void observeRepositories() {
        MeInteractor.instance().observeArtists()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Artist>>>bindToLifecycle())
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<ReactiveModel<List<Artist>>>() {
                @Override
                public void call(final ReactiveModel<List<Artist>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        userArtists = listReactiveModel.model();
                        notifierArtists.onNext(null);
                    }
                }
            });
        MeInteractor.instance().observeSongs()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Song>>>bindToLifecycle())
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<ReactiveModel<List<Song>>>() {
                @Override
                public void call(final ReactiveModel<List<Song>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        userSongs = listReactiveModel.model();
                        notifierSongs.onNext(null);
                    }
                }
            });
        MeInteractor.instance().observePlaylists()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Playlist>>>bindToLifecycle())
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<ReactiveModel<List<Playlist>>>() {
                @Override
                public void call(final ReactiveModel<List<Playlist>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        userPlaylists = listReactiveModel.model();
                        notifierPlaylists.onNext(null);
                    }
                }
            });
        SongInteractor.instance().observeTrendingSongs()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Song>>>bindToLifecycle())
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<ReactiveModel<List<Song>>>() {
                @Override
                public void call(final ReactiveModel<List<Song>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        trendingSongs = listReactiveModel.model();
                        notifierTrendingSongs.onNext(null);
                    }
                }
            });
        ArtistInteractor.instance().observeTrendingArtists()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Artist>>>bindToLifecycle())
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<ReactiveModel<List<Artist>>>() {
                @Override
                public void call(final ReactiveModel<List<Artist>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        trendingArtists = listReactiveModel.model();
                        notifierTrendingArtists.onNext(null);
                    }
                }
            });
        CredentialsInteractor.instance().observeToken()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<AccessToken>>bindToLifecycle())
            .debounce(200, TimeUnit.MILLISECONDS)
            .subscribe(new Action1<ReactiveModel<AccessToken>>() {
                @Override
                public void call(final ReactiveModel<AccessToken> accessTokenReactiveModel) {
                    loggedIn = accessTokenReactiveModel.model() != null &&
                        accessTokenReactiveModel.action() == ReactiveModel.NO_ACTION;

                    notifierLogin.onNext(null);
                }
            });
    }

    /**
     * Starts observing the notifier with a debounce of half a second to avoid backpressure from backend.
     * When notified it flatmaps all the data and saves a snapshot asking the view to be redrawn from it.
     */
    private void observeNotifier() {
        Observable.merge(
                notifierArtists,
                notifierSongs,
                notifierPlaylists,
                notifierTrendingArtists,
                notifierTrendingSongs,
                notifierLogin
            )
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle())
            .debounce(500, TimeUnit.MILLISECONDS) // To avoid drawing all the time if repositories are active
            .map(new Func1<Void, List<GenericAdapter.ItemSupplier>>() {
                @Override
                public List<GenericAdapter.ItemSupplier> call(final Void aVoid) {
                    return flatMap();
                }
            })
            .subscribe(new Action1<List<GenericAdapter.ItemSupplier>>() {
                @Override
                public void call(final List<GenericAdapter.ItemSupplier> itemSuppliers) {
                    dataSnapshot = itemSuppliers;
                    requestView();
                }
            });
    }

    /**
     * Flat maps a snapshot of all the data in the moment, creating accordingly the headers for each type of data
     * @return a list of suppliers for a recycler view to show the data snapshot collected
     */
    @NonNull List<GenericAdapter.ItemSupplier> flatMap() {
        List<GenericAdapter.ItemSupplier> data = new ArrayList<>();

        //TODO remove hardcodes
        data.addAll(inflate("Especialmente para ti", trendingArtists));
        data.addAll(inflate("Pensamos en vos", trendingSongs));
        data.addAll(inflate("Tus playlists cerca tuyo", userPlaylists));
        data.addAll(inflate("Tus artistas favoritos", userArtists));
        data.addAll(inflate("Tus canciones preferidas", userSongs));

        if (!loggedIn) {
            data.add(new HeaderCardSupplier(getContext(), "Maeame no es lo mismo sin vos!"));
            data.add(new NoAccountCardSupplier(getContext()));
        }

        return data;
    }

    /**
     * Inflates for a given header and a list of playable objects a list of item suppliers for a recycler view
     */
    @NonNull List<GenericAdapter.ItemSupplier> inflate(@NonNull String header, @Nullable List<? extends Playable> list) {
        List<GenericAdapter.ItemSupplier> aux = new ArrayList<>();

        if (list != null) {
            aux.add(new HeaderCardSupplier(getContext(), header));

            List<GenericAdapter.ItemSupplier> inners = new ArrayList<>();
            for (Playable playable : list) {
                inners.add(new PlayableCardSupplier(getContext(), playable));
            }
            aux.add(new HorizontalCardSupplier(getContext(), inners));
        }

        return aux;
    }

}
