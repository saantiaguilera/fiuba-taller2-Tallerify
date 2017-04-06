package com.u.tallerify.presenter.search;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.abstracts.GenericGridContract;
import com.u.tallerify.model.entity.Album;
import com.u.tallerify.model.entity.Artist;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.album.AlbumInteractor;
import com.u.tallerify.networking.interactor.artist.ArtistInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.supplier.home.card.HeaderCardSupplier;
import com.u.tallerify.supplier.home.card.HorizontalCardSupplier;
import com.u.tallerify.supplier.home.card.PlayableCardSupplier;
import com.u.tallerify.utils.adapter.GenericAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import rx.subjects.BehaviorSubject;

/**
 * Created by saguilera on 3/26/17.
 */
public class SearchPresenter extends Presenter<GenericGridContract.View>
        implements GenericGridContract.Presenter {

    @Nullable List<Song> songs;
    @Nullable List<Album> albums;
    @Nullable List<Artist> artists;

    @NonNull BehaviorSubject<Void> notifier;

    @Nullable List<GenericAdapter.ItemSupplier> dataSnapshot;

    @Override
    protected void onAttach(@NonNull final GenericGridContract.View view) {
        notifier = BehaviorSubject.create((Void) null);
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
            })
            .subscribe(new Action1<List<GenericAdapter.ItemSupplier>>() {
                @Override
                public void call(final List<GenericAdapter.ItemSupplier> itemSuppliers) {
                    dataSnapshot = itemSuppliers;
                    requestView();
                }
            });
    }

    private void observeRepositories() {
        SongInteractor.instance().observeSearches()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Song>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Song>>>() {
                @Override
                public void call(final ReactiveModel<List<Song>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        songs = listReactiveModel.model();
                    } else if (listReactiveModel.action() == SongInteractor.ACTION_EMPTY_SEARCH) {
                        songs = null;
                    }

                    notifier.onNext(null);
                }
            });
        ArtistInteractor.instance().observeSearches()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Artist>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Artist>>>() {
                @Override
                public void call(final ReactiveModel<List<Artist>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        artists = listReactiveModel.model();
                    } else if (listReactiveModel.action() == ArtistInteractor.ACTION_EMPTY_SEARCH) {
                        artists = null;
                    }

                    notifier.onNext(null);
                }
            });
        AlbumInteractor.instance().observeSearches()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<Album>>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<List<Album>>>() {
                @Override
                public void call(final ReactiveModel<List<Album>> listReactiveModel) {
                    if (listReactiveModel.model() != null) {
                        albums = listReactiveModel.model();
                    } else if (listReactiveModel.action() == AlbumInteractor.ACTION_EMPTY_SEARCH) {
                        albums = null;
                    }

                    notifier.onNext(null);
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
        data.addAll(inflate("Artistas", artists));
        data.addAll(inflate("Albums", albums));
        data.addAll(inflate("Canciones", songs));

        if (data.isEmpty()) {
            data.add(new HeaderCardSupplier(getContext(), "Un placeholder para que no este tan vacio"));
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
