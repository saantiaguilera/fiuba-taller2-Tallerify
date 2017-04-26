package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.profile.ProfileUserActivityContract;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.user.UserInteractor;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.supplier.card.PlayableCardSupplier;
import com.u.tallerify.utils.adapter.GenericAdapter;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.u.tallerify.presenter.base.cards.PlayableCardPresenter.ACTION_DISABLED;

/**
 * Created by saguilera on 3/30/17.
 */
public class ProfileUserActivityPresenter extends Presenter<ProfileUserActivityContract.View>
        implements ProfileUserActivityContract.Presenter {

    @Nullable List<Song> activity;
    @Nullable User me;

    public ProfileUserActivityPresenter() {
        me = MeInteractor.instance().userSnapshot();

        MeInteractor.instance().observeUser()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<ReactiveModel<User>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<User>>() {
                @Override
                public void call(final ReactiveModel<User> rxModel) {
                    if (!rxModel.hasError() && rxModel.model() != null) {
                        me = rxModel.model();
                        requestActivity();
                    }
                }
            });

        requestActivity();
    }

    void requestActivity() {
        if (me != null) {
            UserInteractor.instance().activity(getContext(), me.id())
                .observeOn(Schedulers.io())
                .subscribeOn(Schedulers.io())
                .compose(ProfileUserActivityPresenter.this.<List<Song>>bindToLifecycle())
                .subscribe(new Action1<List<Song>>() {
                    @Override
                    public void call(final List<Song> songs) {
                        activity = songs;
                        requestRender();
                    }
                }, Interactors.ACTION_ERROR);
        }
    }

    @Override
    protected void onRender(@NonNull final ProfileUserActivityContract.View view) {
        if (activity != null) {
            List<GenericAdapter.ItemSupplier> songCards = new ArrayList<>();
            songCards.addAll(Observable.from(activity)
                .map(new Func1<Song, PlayableCardSupplier>() {
                    @Override
                    public PlayableCardSupplier call(final Song song) {
                        return new PlayableCardSupplier(getContext(), song, ACTION_DISABLED);
                    }
                })
                .toList()
                .toBlocking()
                .firstOrDefault(new ArrayList<PlayableCardSupplier>()));

            view.setActivities(songCards);
        }
    }

}
