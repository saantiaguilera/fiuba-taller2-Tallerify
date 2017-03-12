package com.u.tallerify.list.home.card.supplier;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.list.adapter.GenericAdapter;
import com.u.tallerify.presenter.home.cards.SongCardPresenter;
import com.u.tallerify.view.home.cards.SongCardView;

/**
 * Created by saguilera on 3/12/17.
 */
public class SongCardSupplier extends GenericAdapter.ItemSupplier<SongCardView> {

    public static final int SONGS_PER_ROW = 2;

    public SongCardSupplier(@NonNull Context context) {
        super(context);
    }

    @NonNull
    @Override
    public SongCardView createView() {
        return new SongCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter createPresenter() {
        return new SongCardPresenter();
    }

    @Override
    public int spanSize() {
        return SONGS_PER_ROW;
    }

    @Override
    public boolean isSameItem(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return supplier.type() == type(); // TODO app
    }

    @Override
    public boolean isSameContent(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return isSameItem(supplier); // TODO app
    }
}
