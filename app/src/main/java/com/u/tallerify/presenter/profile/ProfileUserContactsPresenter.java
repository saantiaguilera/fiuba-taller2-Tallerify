package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.profile.ProfileUserContactsContract;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
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
        MeInteractor.instance().observeUser()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<User>>bindToLifecycle())
            .subscribe(new Action1<ReactiveModel<User>>() {
                @Override
                public void call(final ReactiveModel<User> userReactiveModel) {
                    if (userReactiveModel.model() != null && !userReactiveModel.hasError()) {
                        users = userReactiveModel.model().contacts();
                        requestView();
                    }
                }
            });
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
                    // TODO go to chat. with users.get(integer)
                }
            });
    }

    @Override
    protected void onViewRequested(@NonNull final ProfileUserContactsContract.View view) {
        super.onViewRequested(view);

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
                        return user.pictures().get(0);
                    }
                })
                .toList()
                .toBlocking()
                .firstOrDefault(new ArrayList<String>());

            view.setContacts(names, images);
        }
    }
}
