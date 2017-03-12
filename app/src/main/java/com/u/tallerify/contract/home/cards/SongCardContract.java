package com.u.tallerify.contract.home.cards;

import android.support.annotation.NonNull;
import com.u.tallerify.contract.ContractPresenter;
import com.u.tallerify.contract.ContractView;

/**
 * Created by saguilera on 3/12/17.
 */
public interface SongCardContract {

    interface View extends ContractView {

        void setImage(@NonNull String url);
        void setName(@NonNull String name);

    }

    interface Presenter extends ContractPresenter {

    }

}
