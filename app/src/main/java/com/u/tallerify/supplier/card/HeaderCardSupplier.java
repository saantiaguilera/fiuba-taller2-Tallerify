package com.u.tallerify.supplier.card;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.presenter.base.cards.HeaderCardPresenter;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.view.base.cards.HeaderCardView;

/**
 * Created by saguilera on 3/12/17.
 */
public class HeaderCardSupplier extends GenericAdapter.ItemSupplier<HeaderCardView> {

    private final @NonNull String title;

    public HeaderCardSupplier(final @NonNull Context context, final @NonNull String title) {
        super(context);
        this.title = title;
    }

    @NonNull
    @Override
    public HeaderCardView createView() {
        return new HeaderCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter createPresenter() {
        return new HeaderCardPresenter(title);
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
        boolean same = isSameItem(supplier);

        if (!same) return false;

        HeaderCardSupplier cardSupplier = (HeaderCardSupplier) supplier;
        return (title.contentEquals(cardSupplier.title));
    }

}
