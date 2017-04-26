package com.u.tallerify.supplier.card;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.User;
import com.u.tallerify.presenter.base.cards.ContactCardPresenter;
import com.u.tallerify.presenter.base.cards.PlayableCardPresenter;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.view.base.cards.ContactCardView;
import com.u.tallerify.view.base.cards.PlayableCardView;

/**
 * Created by saguilera on 4/26/17.
 */

public class ContactCardSupplier extends GenericAdapter.ItemSupplier<ContactCardView> {

    private @NonNull User user;
    private @ContactCardView.Action int status;

    public ContactCardSupplier(@NonNull Context context, @NonNull User user, @ContactCardView.Action int status) {
        super(context);
        this.user = user;
        this.status = status;
    }

    @NonNull
    @Override
    public ContactCardView createView() {
        return new ContactCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter createPresenter() {
        return new ContactCardPresenter(user, status);
    }

    @Override
    public int spanSize() {
        return 2;
    }

    @Override
    public boolean isSameItem(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return supplier.type() == type() &&
            user.id() == ((ContactCardSupplier) supplier).user.id();
    }

    @Override
    public boolean isSameContent(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return isSameItem(supplier) &&
            user.equals(((ContactCardSupplier) supplier).user) &&
            status == ((ContactCardSupplier) supplier).status;
    }

}