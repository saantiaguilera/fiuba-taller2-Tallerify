package com.u.tallerify.presenter.home.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.home.cards.TrendingSongsCardContract;
import com.u.tallerify.utils.adapter.GenericAdapter;
import java.util.List;

/**
 * Created by saguilera on 3/12/17.
 */
public class TrendingSongsCardPresenter extends GenericAdapter.ItemPresenter<TrendingSongsCardContract.View>
        implements TrendingSongsCardContract.Presenter {

    private @NonNull List<GenericAdapter.ItemSupplier> songs;

    public TrendingSongsCardPresenter(@NonNull List<GenericAdapter.ItemSupplier> songs) {
        this.songs = songs;
    }

    @Override
    protected void onAttach(@NonNull final TrendingSongsCardContract.View view) {
        view.setData(songs);
    }

}
