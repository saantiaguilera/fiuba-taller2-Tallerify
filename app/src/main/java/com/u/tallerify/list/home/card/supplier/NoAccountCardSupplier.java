package com.u.tallerify.list.home.card.supplier;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.list.adapter.GenericAdapter;
import com.u.tallerify.presenter.home.cards.NoAccountCardPresenter;
import com.u.tallerify.view.home.cards.NoAccountCardView;

/**
 * Created by saguilera on 3/12/17.
 */
public class NoAccountCardSupplier extends GenericAdapter.ItemSupplier<NoAccountCardView> {

    public NoAccountCardSupplier(@NonNull final Context context) {
        super(context);
    }

    @NonNull
    @Override
    public NoAccountCardView createView() {
        return new NoAccountCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter createPresenter() {
        return new NoAccountCardPresenter();
    }

    @Override
    public int spanSize() {
        return 1;
    }

    @Override
    public boolean isSameItem(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return type() == supplier.type();
    }

    @Override
    public boolean isSameContent(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return isSameItem(supplier);
    }

}
