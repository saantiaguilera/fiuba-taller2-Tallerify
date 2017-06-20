package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.bluelinelabs.conductor.RouterTransaction;
import com.bluelinelabs.conductor.changehandler.FadeChangeHandler;
import com.u.tallerify.contract.profile.ProfileUserContactsContract;
import com.u.tallerify.controller.profile.ChatController;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.presenter.Presenter;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 3/30/17.
 */
public class ProfileUserContactsPresenter extends Presenter<ProfileUserContactsContract.View>
    implements ProfileUserContactsContract.Presenter {

    @Nullable List<User> users;

    public ProfileUserContactsPresenter() {
        if (MeInteractor.instance().userSnapshot() != null) {
            users = MeInteractor.instance().userSnapshot().contacts();
            MeInteractor.instance().observeUser()
                .observeOn(Schedulers.computation())
                .subscribeOn(Schedulers.computation())
                .compose(this.<ReactiveModel<User>>bindToLifecycle())
                .subscribe(new Action1<ReactiveModel<User>>() {
                    @Override
                    public void call(final ReactiveModel<User> rxModel) {
                        if (!rxModel.hasError() && rxModel.model() != null) {
                            users = rxModel.model().contacts();
                            requestRender();
                        }
                    }
                });
        }
    }

    @Override
    protected void onAttach(@NonNull final ProfileUserContactsContract.View view) {
        view.observeContactClicks()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .compose(this.<Integer>bindToLifecycle())
            .subscribe(new Action1<Integer>() {
                @Override
                public void call(final Integer integer) {
                    User him = users.get(integer);

                    getMainRouter().pushController(RouterTransaction.with(
                            new ChatController()
                                .with(him)
                        )
                        .popChangeHandler(new FadeChangeHandler(false))
                        .pushChangeHandler(new FadeChangeHandler()));
                }
            });
    }

    @Override
    protected void onRender(@NonNull final ProfileUserContactsContract.View view) {
        if (users != null) {
            List<String> names = Observable.from(users)
                .map(new Func1<User, String>() {
                    @Override
                    public String call(final User user) {
                        return user.name();
                    }
                })
                .toList()
                .toBlocking()
                .firstOrDefault(new ArrayList<String>());

            List<String> images = Observable.from(users)
                .map(new Func1<User, String>() {
                    @Override
                    public String call(final User user) {
                        return user.pictures() != null && !user.pictures().isEmpty() ?
                            user.pictures().get(0) :
                            null;
                    }
                })
                .toList()
                .toBlocking()
                .firstOrDefault(new ArrayList<String>());

            view.setContacts(names, images);
        }
    }
}
