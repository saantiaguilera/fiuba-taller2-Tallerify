package com.u.tallerify.supplier.home.card;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.presenter.home.cards.SongCardPresenter;
import com.u.tallerify.view.home.cards.SongCardView;

/**
 * Created by saguilera on 3/12/17.
 */
public class SongCardSupplier extends GenericAdapter.ItemSupplier<SongCardView> {

    public static final int SONGS_PER_ROW = 2;

    private @NonNull Song song;

    public SongCardSupplier(@NonNull Context context, @NonNull Song song) {
        super(context);
        this.song = song;
    }

    @NonNull
    @Override
    public SongCardView createView() {
        return new SongCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter createPresenter() {
        return new SongCardPresenter(song);
    }

    @Override
    public int spanSize() {
        return SONGS_PER_ROW;
    }

    @Override
    public boolean isSameItem(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return supplier.type() == type() &&
            song.id() == ((SongCardSupplier) supplier).song.id();
    }

    @Override
    public boolean isSameContent(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return isSameItem(supplier) &&
            song.equals(((SongCardSupplier) supplier).song);
    }

}
