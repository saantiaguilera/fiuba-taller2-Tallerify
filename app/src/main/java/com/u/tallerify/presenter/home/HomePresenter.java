package com.u.tallerify.presenter.home;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.home.HomeContract;
import com.u.tallerify.model.AccessToken;
import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.model.entity.Playlist;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.artist.ArtistInteractor;
import com.u.tallerify.networking.interactor.credentials.CredentialsInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.networking.interactor.user.MeInteractor;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.supplier.home.card.HeaderCardSupplier;
import com.u.tallerify.supplier.home.card.NoAccountCardSupplier;
import com.u.tallerify.supplier.home.card.PlayableCardSupplier;
import com.u.tallerify.supplier.home.card.HorizontalCardSupplier;
import com.u.tallerify.utils.adapter.GenericAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.PublishSubject;

/**
 * Created by saguilera on 3/12/17.
 */
public class HomePresenter extends Presenter<HomeContract.View>
        implements HomeContract.Presenter {

    @Nullable List<Artist> userArtists;
    @Nullable List<Song> userSongs;
    @Nullable List<Playlist> userPlaylists;
    @Nullable List<Song> trendingSongs;
    @Nullable List<Artist> trendingArtists;
    boolean loggedIn;

    @NonNull PublishSubject<Void> notifier;

    @Nullable List<GenericAdapter.ItemSupplier> dataSnapshot;

    @Override
    protected void onAttach(@NonNull final HomeContract.View view) {
        notifier = PublishSubject.create();
        observeNotifier();
        observeRepositories();

        notifier.onNext(null);
    }

    @Override
    protected void onViewRequested(@NonNull final HomeContract.View view) {
        super.onViewRequested(view);
        consumeSnapshot(view);
    }

    private void consumeSnapshot(@NonNull final HomeContract.View view) {
        if (dataSnapshot != null) {
            view.setData(dataSnapshot);
            dataSnapshot = null;
        }
    }

    private void observeRepositories() {
        MeInteractor.instance().observeArtists()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Artist>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Artist>>>() {
                @Override
                public void call(final ReactiveModel<List<Artist>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        userArtists = listReactiveModel.model();
                        notifier.onNext(null);
                    }
                }
            });
        MeInteractor.instance().observeSongs()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Song>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Song>>>() {
                @Override
                public void call(final ReactiveModel<List<Song>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        userSongs = listReactiveModel.model();
                        notifier.onNext(null);
                    }
                }
            });
        MeInteractor.instance().observePlaylists()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Playlist>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Playlist>>>() {
                @Override
                public void call(final ReactiveModel<List<Playlist>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        userPlaylists = listReactiveModel.model();
                        notifier.onNext(null);
                    }
                }
            });
        SongInteractor.instance().observeTrendingSongs()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Song>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Song>>>() {
                @Override
                public void call(final ReactiveModel<List<Song>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        trendingSongs = listReactiveModel.model();
                        notifier.onNext(null);
                    }
                }
            });
        ArtistInteractor.instance().observeTrendingArtists()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Artist>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Artist>>>() {
                @Override
                public void call(final ReactiveModel<List<Artist>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        trendingArtists = listReactiveModel.model();
                        notifier.onNext(null);
                    }
                }
            });
        CredentialsInteractor.instance().observeToken()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<AccessToken>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<AccessToken>>() {
                @Override
                public void call(final ReactiveModel<AccessToken> accessTokenReactiveModel) {
                    loggedIn = accessTokenReactiveModel.model() != null &&
                        accessTokenReactiveModel.action() == ReactiveModel.NO_ACTION;

                    notifier.onNext(null);
                }
            });
    }

    private void observeNotifier() {
        notifier.observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<Void>bindToLifecycle())
            .debounce(500, TimeUnit.MILLISECONDS) // To avoid drawing all the time if repositories are active
            .map(new Func1<Void, List<GenericAdapter.ItemSupplier>>() {
                @Override
                public List<GenericAdapter.ItemSupplier> call(final Void aVoid) {
                    return flatMap();
                }
            }).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(new Action1<List<GenericAdapter.ItemSupplier>>() {
                @Override
                public void call(final List<GenericAdapter.ItemSupplier> itemSuppliers) {
                    dataSnapshot = itemSuppliers;
                    requestView();
                }
            });
    }

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
