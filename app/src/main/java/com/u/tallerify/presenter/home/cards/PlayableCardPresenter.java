package com.u.tallerify.presenter.home.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.home.cards.PlayableCardContract;
import com.u.tallerify.model.entity.Playable;
import com.u.tallerify.utils.adapter.GenericAdapter;

/**
 * Created by saguilera on 3/12/17.
 */
public class PlayableCardPresenter extends GenericAdapter.ItemPresenter<PlayableCardContract.View>
        implements PlayableCardContract.Presenter {

    private @NonNull Playable playable;

    public PlayableCardPresenter(@NonNull Playable playable) {
        this.playable = playable;
    }

    @Override
    protected void onAttach(@NonNull final PlayableCardContract.View view) {
        view.setImage(playable.pictures() == null ? null : playable.pictures().get(0));
        view.setName(playable.fullName());
    }

}
