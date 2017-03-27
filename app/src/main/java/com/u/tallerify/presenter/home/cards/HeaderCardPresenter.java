package com.u.tallerify.presenter.home.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.abstracts.cards.HeaderCardContract;
import com.u.tallerify.utils.adapter.GenericAdapter;

/**
 * Created by saguilera on 3/12/17.
 */
public class HeaderCardPresenter extends GenericAdapter.ItemPresenter<HeaderCardContract.View>
    implements HeaderCardContract.Presenter {

    private @NonNull String title;

    public HeaderCardPresenter(@NonNull String title) {
        this.title = title;
    }

    @Override
    protected void onAttach(@NonNull final HeaderCardContract.View view) {
        view.setTitle(title);
    }

}
