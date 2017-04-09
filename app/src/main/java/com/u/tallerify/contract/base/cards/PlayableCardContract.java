package com.u.tallerify.contract.base.cards;

import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;
import rx.Observable;

/**
 * Created by saguilera on 3/12/17.
 */
public interface PlayableCardContract {

    interface View extends ContractView {

        void setImage(@Nullable String url);
        void setName(@NonNull String name);
        void setAction(@DrawableRes int resId);

        /**
         * By default its false
         * @param enabled is should be shown or not
         */
        void setActionEnabled(boolean enabled);

        @NonNull Observable<Void> observePlaylistClicks();
        @NonNull Observable<Void> observePlayClicks();
        @NonNull Observable<Void> observeActionClicks();

    }

    interface Presenter extends ContractPresenter {

    }

}
