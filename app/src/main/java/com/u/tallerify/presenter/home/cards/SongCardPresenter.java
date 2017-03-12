package com.u.tallerify.presenter.home.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.home.cards.SongCardContract;
import com.u.tallerify.model.entity.Song;
import com.u.tallerify.utils.adapter.GenericAdapter;

/**
 * Created by saguilera on 3/12/17.
 */
public class SongCardPresenter extends GenericAdapter.ItemPresenter<SongCardContract.View>
        implements SongCardContract.Presenter {

    private @NonNull Song song;

    public SongCardPresenter(@NonNull Song song) {
        this.song = song;
    }

    @Override
    protected void onAttach(@NonNull final SongCardContract.View view) {
        view.setImage(song.album().picture().medium());
        view.setName(song.name() + " - " + song.album().artist().name());
    }

}
