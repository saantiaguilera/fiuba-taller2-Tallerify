package com.u.tallerify.presenter.home.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.home.cards.SongCardContract;
import com.u.tallerify.list.adapter.GenericAdapter;

/**
 * Created by saguilera on 3/12/17.
 */
public class SongCardPresenter extends GenericAdapter.ItemPresenter<SongCardContract.View>
        implements SongCardContract.Presenter {

    public SongCardPresenter() {

    }

    @Override
    protected void onAttach(@NonNull final SongCardContract.View view) {
        view.setImage("http://www.elrocknomuere.com/blog/img/albums/americana.jpg");
        view.setName("The Offspring - Americana");
    }

}
