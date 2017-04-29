package com.u.tallerify.supplier.card;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.presenter.base.cards.HorizontalCardPresenter;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.view.base.cards.HorizontalCardView;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
public class HorizontalCardSupplier extends GenericAdapter.ItemSupplier<HorizontalCardView> {

    private @NonNull List<GenericAdapter.ItemSupplier> supplier;

    public HorizontalCardSupplier(@NonNull final Context context,
            @NonNull List<GenericAdapter.ItemSupplier> supplier) {
        super(context);
        this.supplier = supplier;
    }

    @NonNull
    @Override
    public HorizontalCardView createView() {
        return new HorizontalCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter<?> createPresenter() {
        return new HorizontalCardPresenter(supplier);
    }

    @Override
    public int spanSize() {
        return 1;
    }

    @Override
    public boolean isSameItem(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return supplier.type() == type();
    }

    @Override
    public boolean isSameContent(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return isSameItem(supplier) &&
            ((HorizontalCardSupplier) supplier).supplier.equals(supplier);
    }

}
