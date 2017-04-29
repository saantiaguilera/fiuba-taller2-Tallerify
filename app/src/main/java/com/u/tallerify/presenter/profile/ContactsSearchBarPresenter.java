package com.u.tallerify.presenter.profile;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.profile.ContactsSearchBarContract;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.networking.interactor.Interactors;
import com.u.tallerify.networking.interactor.user.UserInteractor;
import com.u.tallerify.presenter.abstracts.BaseInputPresenter;
import java.util.List;
import rx.schedulers.Schedulers;

/**
 * Created by saguilera on 4/27/17.
 */
public class ContactsSearchBarPresenter extends BaseInputPresenter
        implements ContactsSearchBarContract {

    @Override
    protected void onInput(@NonNull final String input) {
        UserInteractor.instance()
            .search(getContext(), input)
            .observeOn(Schedulers.io())
            .subscribeOn(Schedulers.io())
            .compose(this.<List<User>>bindToLifecycle())
            .subscribe(Interactors.ACTION_NEXT, Interactors.ACTION_ERROR);
    }

}
