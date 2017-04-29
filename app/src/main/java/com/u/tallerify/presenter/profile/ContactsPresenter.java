package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.abstracts.GenericGridContract;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.ReactiveModel;
import com.u.tallerify.networking.interactor.me.MeInteractor;
import com.u.tallerify.networking.interactor.user.UserInteractor;
import com.u.tallerify.presenter.Presenter;
import com.u.tallerify.supplier.card.ContactCardSupplier;
import com.u.tallerify.utils.adapter.GenericAdapter;
import es.dmoral.toasty.Toasty;
import java.util.ArrayList;
import java.util.List;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

import static com.u.tallerify.view.base.cards.ContactCardView.ACTION_ADD;
import static com.u.tallerify.view.base.cards.ContactCardView.ACTION_DELETE;

/**
 * Created by saguilera on 4/27/17.
 */
public class ContactsPresenter extends Presenter<GenericGridContract.View>
        implements GenericGridContract.Presenter {

    @Nullable User me;

    @Nullable List<GenericAdapter.ItemSupplier> data;

    @Override
    protected void onAttach(@NonNull final GenericGridContract.View view) {
        super.onAttach(view);

        me = MeInteractor.instance().userSnapshot();
        MeInteractor.instance().observeUser()
            .observeOn(Schedulers.computation())
            .subscribeOn(Schedulers.computation())
            .compose(this.<ReactiveModel<User>>bindToLifecycle())
            .filter(new Func1<ReactiveModel<User>, Boolean>() {
                @Override
                public Boolean call(final ReactiveModel<User> userReactiveModel) {
                    return userReactiveModel.isModelSafe();
                }
            })
            .subscribe(new Action1<ReactiveModel<User>>() {
                @Override
                public void call(final ReactiveModel<User> userReactiveModel) {
                    me = userReactiveModel.model();
                }
            });

        UserInteractor.instance().observeSearches()
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<ReactiveModel<List<User>>>bindToLifecycle())
            .filter(new Func1<ReactiveModel<List<User>>, Boolean>() {
                @Override
                public Boolean call(final ReactiveModel<List<User>> listReactiveModel) {
                    return listReactiveModel.isModelSafe() || listReactiveModel.action() == UserInteractor.ACTION_EMPTY_SEARCH;
                }
            })
            .map(new Func1<ReactiveModel<List<User>>, List<User>>() {
                @Override
                public List<User> call(final ReactiveModel<List<User>> listReactiveModel) {
                    return listReactiveModel.model();
                }
            })
            .subscribe(new Action1<List<User>>() {
                @Override
                public void call(final List<User> users) {
                    if (users != null) {
                        data = Observable.from(users)
                            .map(new Func1<User, GenericAdapter.ItemSupplier>() {
                                @Override
                                public GenericAdapter.ItemSupplier call(final User user) {
                                    int status = isContact(user) ? ACTION_DELETE : ACTION_ADD;

                                    return new ContactCardSupplier(getContext(), user, status);
                                }
                            })
                            .toList()
                            .toBlocking()
                            .first();

                        if (data.isEmpty()) {
                            Toasty.warning(getContext(), "No se encontraron resultados para tu busqueda").show();
                        }
                    } else {
                        data = new ArrayList<>();
                    }

                    requestRender();
                }
            });
    }

    @Override
    protected void onRender(@NonNull final GenericGridContract.View view) {
        if (data != null) {
            view.setData(data);
            data = null;
        }
    }

    boolean isContact(@NonNull User user) {
        if (me == null || me.contacts() == null) {
            return false;
        }

        for (User contact : me.contacts()) {
            if (contact.id() == user.id()) {
                return true;
            }
        }

        return false;
    }

}
