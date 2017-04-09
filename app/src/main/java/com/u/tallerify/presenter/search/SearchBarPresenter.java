package com.u.tallerify.presenter.search;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.search.SearchBarContract;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.album.AlbumInteractor;
import com.u.tallerify.networking.interactor.artist.ArtistInteractor;
import com.u.tallerify.networking.interactor.song.SongInteractor;
import com.u.tallerify.presenter.Presenter;
import java.util.concurrent.TimeUnit;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/24/17.
 */

public class SearchBarPresenter extends Presenter<SearchBarContract.View>
        implements SearchBarContract.Presenter {

    @Override
    protected void onAttach(@NonNull final SearchBarContract.View view) {
        view.observeInputs()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<String>bindToLifecycle())
            .debounce(500, TimeUnit.MILLISECONDS) // To avoid backpressure on the api
            .subscribe(new Action1<String>() {
                @Override
                public void call(final String inputText) {
                    dispatch(SongInteractor.instance().search(getContext(), inputText));
                    dispatch(AlbumInteractor.instance().search(getContext(), inputText));
                    dispatch(ArtistInteractor.instance().search(getContext(), inputText));
                }
            });
    }

    @Override
    protected void onRender(@NonNull final SearchBarContract.View view) {}

    void dispatch(Observable<?> observable) {
        observable.observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(bindToLifecycle())
            .subscribe(Interactors.ACTION_NEXT, Interactors.ACTION_ERROR);
    }

}
