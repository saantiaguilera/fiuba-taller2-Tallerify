package com.u.tallerify.supplier.card;

import android.content.Context;
import android.support.annotation.NonNull;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.presenter.base.cards.PlayableCardPresenter;
import com.u.tallerify.utils.adapter.GenericAdapter;
import com.u.tallerify.view.base.cards.PlayableCardView;

/**
 * Created by saguilera on 3/12/17.
 */
public class PlayableCardSupplier extends GenericAdapter.ItemSupplier<PlayableCardView> {

    public static final int SONGS_PER_ROW = 2;

    private @NonNull Playable palayable;
    private int status;

    public PlayableCardSupplier(@NonNull Context context, @NonNull Playable palayable, int status) {
        super(context);
        this.palayable = palayable;
        this.status = status;
    }

    @NonNull
    @Override
    public PlayableCardView createView() {
        return new PlayableCardView(context());
    }

    @NonNull
    @Override
    public GenericAdapter.ItemPresenter createPresenter() {
        return new PlayableCardPresenter(palayable, status);
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
