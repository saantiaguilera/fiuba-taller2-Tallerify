package com.u.tallerify.supplier.home.card;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.presenter.home.cards.TrendingSongsCardPresenter;
import com.u.tallerify.view.home.cards.TrendingSongsCardView;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
public class TrendingSongsCardSupplier extends GenericAdapter.ItemSupplier<TrendingSongsCardView> {

    private @NonNull List<GenericAdapter.ItemSupplier> songsSupplier;

    public TrendingSongsCardSupplier(@NonNull final Context context,
            @NonNull List<GenericAdapter.ItemSupplier> songsSupplier) {
        super(context);
        this.songsSupplier = songsSupplier;
    }

    @NonNull
    @Override
    public TrendingSongsCardView createView() {
        return new TrendingSongsCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter<?> createPresenter() {
        return new TrendingSongsCardPresenter(songsSupplier);
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
        return isSameItem(supplier);
    }

}
