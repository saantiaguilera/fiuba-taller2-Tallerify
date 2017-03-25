package com.u.tallerify.supplier.home.card;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.presenter.home.cards.PlayableCardPresenter;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.view.home.cards.PlayableCardView;

/**
 * Created by saguilera on 3/12/17.
 */
public class PlayableCardSupplier extends GenericAdapter.ItemSupplier<PlayableCardView> {

    public static final int SONGS_PER_ROW = 2;

    private @NonNull Playable palayable;

    public PlayableCardSupplier(@NonNull Context context, @NonNull Playable palayable) {
        super(context);
        this.palayable = palayable;
    }

    @NonNull
    @Override
    public PlayableCardView createView() {
        return new PlayableCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter createPresenter() {
        return new PlayableCardPresenter(palayable);
    }

    @Override
    public int spanSize() {
        return SONGS_PER_ROW;
    }

    @Override
    public boolean isSameItem(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return supplier.type() == type() &&
            palayable.id() == ((PlayableCardSupplier) supplier).palayable.id();
    }

    @Override
    public boolean isSameContent(@NonNull final GenericAdapter.ItemSupplier supplier) {
        return isSameItem(supplier) &&
            palayable.equals(((PlayableCardSupplier) supplier).palayable);
    }

}
