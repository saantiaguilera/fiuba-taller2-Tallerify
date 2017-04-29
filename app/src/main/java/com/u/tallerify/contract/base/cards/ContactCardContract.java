package com.u.tallerify.contract.base.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import com.u.tallerify.view.base.cards.ContactCardView;
import rx.Observable;

/**
 * Created by saguilera on 4/26/17.
 */

public interface ContactCardContract {

    interface View extends ContractView {

        void setImage(@NonNull String url);
        void setAction(@ContactCardView.Action int action);

        @NonNull Observable<Void> observeAddClicks();
        @NonNull Observable<Void> observeRemoveClicks();

    }

    interface Presenter extends ContractPresenter {
    }

}
