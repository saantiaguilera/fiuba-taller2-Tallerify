package com.u.tallerify.presenter.base.cards;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.base.cards.ContactCardContract;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.user.UserInteractor;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.view.base.cards.ContactCardView;
import rx.Observable;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

import static com.u.tallerify.view.base.cards.ContactCardView.ACTION_ADD;
import static com.u.tallerify.view.base.cards.ContactCardView.ACTION_DELETE;

/**
 * Created by saguilera on 3/12/17.
 */
public class ContactCardPresenter extends GenericAdapter.ItemPresenter<ContactCardContract.View>
        implements ContactCardContract.Presenter {

    @Nullable User me;
    @NonNull User him;
    @ContactCardView.Action int status;

    boolean requesting;

    public ContactCardPresenter(@NonNull User him, @ContactCardView.Action int status) {
        this.him = him;
        this.status = status;
        this.me = MeInteractor.instance().userSnapshot();
    }

    @Override
    protected void onAttach(@NonNull final ContactCardContract.View view) {
        Observable.merge(view.observeAddClicks(), view.observeRemoveClicks())
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<Void>bindToLifecycle())
            .subscribe(new Action1<Void>() {
                @Override
                public void call(final Void aVoid) {
                    if (requesting) return;

                    requesting = true;

                    Observable<?> observable = status == ACTION_ADD ?
                        UserInteractor.instance().follow(getContext(), me, him) :
                        UserInteractor.instance().unfollow(getContext(), me, him);

                    observable
                        .subscribeOn(Schedulers.io())
                        .observeOn(Schedulers.io())
                        .compose(bindToLifecycle())
                        .doOnError(new Action1<Throwable>() {
                            @Override
                            public void call(final Throwable throwable) {
                                requesting = false;
                            }
                        })
                        .subscribe(new Action1<Object>() {
                            @Override
                            public void call(final Object user) {
                                status = status == ContactCardView.ACTION_ADD ?
                                    ACTION_DELETE : ACTION_ADD;
                                requestRender();
                                requesting = false;
                            }
                        }, Interactors.ACTION_ERROR);
                }
            });
    }

    protected void onRender(@NonNull final ContactCardContract.View view) {
        view.setAction(status);
        view.setImage(him.pictures().get(0));
    }

}
